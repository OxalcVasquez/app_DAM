package pe.edu.usat.laboratorio.appnavigationdraweractivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import pe.edu.usat.laboratorio.appnavigationdraweractivity.datos.Conexion;
import pe.edu.usat.laboratorio.appnavigationdraweractivity.logica.Ciudad;
import pe.edu.usat.laboratorio.appnavigationdraweractivity.logica.Cliente;
import pe.edu.usat.laboratorio.appnavigationdraweractivity.util.Gallery;
import pe.edu.usat.laboratorio.appnavigationdraweractivity.util.Helper;


public class ClientesAgregarFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    CircleImageView imgCliente;
    TextInputEditText txtClienteApellidosNombres,txtClienteDireccion,txtClienteLineaCredito,txtClienteContrasena,txtClienteTelefono;
    AutoCompleteTextView actvClienteCiudad;
    SwitchMaterial swVip;
    MaterialButton btnRegistrar,btnLimpiar;
    int posicionItemSeleccionadoActvCiudad = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clientes_agregar, container, false);

        //Debe tener un titulo - Configurar el titulo del activity
        this.getActivity().setTitle("Agregar cliente");

        //Referenciar a la conexion de la bd sqlite
        Conexion.contextApp = this.getContext();

        //Referenciar los controles
        imgCliente = view.findViewById(R.id.img_cliente);
        txtClienteApellidosNombres= view.findViewById(R.id.txt_cliente_apellidos_nombres);
        txtClienteDireccion= view.findViewById(R.id.txt_cliente_direccion);
        txtClienteLineaCredito= view.findViewById(R.id.txt_cliente_linea_credito);
        txtClienteContrasena= view.findViewById(R.id.txt_cliente_contrasena);
        txtClienteTelefono = view.findViewById(R.id.txt_cliente_telefono);
        actvClienteCiudad= view.findViewById(R.id.actv_cliente_ciudad);
        swVip= view.findViewById(R.id.sw_cliente_vip);
        btnRegistrar= view.findViewById(R.id.btn_cliente_registrar);
        btnLimpiar= view.findViewById(R.id.btn_cliente_limpiar);

        //Configurar el evento click de los controles
        imgCliente.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        btnLimpiar.setOnClickListener(this);

        //Llamar al metodo que permite cargar las ciudades en el control actv
        this.cargarCiudades();

        //Configurar el tag del imgCliente como foto no real
        imgCliente.setTag("foto_no_real");

        //Configurar el control actvCiudad para que reconozca el evento OnItemClick
        actvClienteCiudad.setOnItemClickListener(this);

        return view;
    }

    private void cargarCiudades(){
        String nombreCiudad[] = new Ciudad().obtenerNombreCiudades();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1,nombreCiudad);
        actvClienteCiudad.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_cliente:
                this.abrirGaleria();
                break;
            case R.id.btn_cliente_registrar:
                //ToDo registrar()
                this.registrar();
                break;
            case R.id.btn_cliente_limpiar:
                //ToDo limpiar

                break;
        }
    }

    public static final int REQUEST_PICK = 1;

    @SuppressWarnings("deprecation")
    private void abrirGaleria() {
        startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"),REQUEST_PICK);

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK){
            //El activity que ha devuelto resultado es la galeria
            if (resultCode == Activity.RESULT_OK){
                //Capturar la foto seleccionada en la galeria
                Uri rutaImage = data.getData();
                try {
                    //Otorga la orientacion correcta a la imagen
                    Bitmap bitmap = Gallery.rotateImage(this.getActivity(),rutaImage,Gallery.getOrientation(this.getActivity(),rutaImage));
                    Bitmap bitmapCompress = Gallery.compress(bitmap);
                    imgCliente.setImageBitmap(bitmapCompress);
                    imgCliente.setTag("foto_real");
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void registrar(){
        //Permite registrar los datos del cliente en la DB

        //Validar los datos

        //Capturar los datos ingresados en los controles
        Cliente cliente = new Cliente();
        cliente.setApellidosNombres(this.txtClienteApellidosNombres.getText().toString().toUpperCase());
        cliente.setDireccion(this.txtClienteDireccion.getText().toString());
        cliente.setTelefono(this.txtClienteTelefono.getText().toString());
        cliente.setLineaCredito(Double.parseDouble(txtClienteLineaCredito.getText().toString()));
        cliente.setContrasena(Helper.convertPassMd5(this.txtClienteContrasena.getText().toString()));
        cliente.setCiudadId(posicionItemSeleccionadoActvCiudad); //Falta definir la pos del item seleccionado
        if (imgCliente.getTag().equals("foto_real")){
            cliente.setFoto(
                    Helper.imageToBase64(
                            ((BitmapDrawable) imgCliente.getDrawable()).getBitmap()
                    )
            );

        } else {
            cliente.setFoto(null);
        }

        cliente.setVip(swVip.isChecked()?"1":" 0");
        cliente.setLongitud(0);
        cliente.setLatitud(0);
        //llamar al metodo registrar de la clase cliente
        long r = cliente.insertar();
        if (r>0){
            Helper.mensajeInformacion(this.getContext(),"Cliente","Se ha registrado correctamente");
            limpiar();
        }
    }

    private void limpiar(){
        txtClienteApellidosNombres.setText("");
        txtClienteTelefono.setText("");
        txtClienteContrasena.setText("");
        txtClienteDireccion.setText("");
        txtClienteLineaCredito.setText("");
        swVip.setChecked(false);
        actvClienteCiudad.setText("");
        imgCliente.setImageResource(R.drawable.profile);
        txtClienteApellidosNombres.requestFocus();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        posicionItemSeleccionadoActvCiudad = i;

    }
}