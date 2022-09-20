package pe.edu.usat.laboratorio.appnavigationdraweractivity.adaptador;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import pe.edu.usat.laboratorio.appnavigationdraweractivity.R;
import pe.edu.usat.laboratorio.appnavigationdraweractivity.logica.Cliente;
import pe.edu.usat.laboratorio.appnavigationdraweractivity.util.Helper;

public class ClienteAdaptador extends RecyclerView.Adapter<ClienteAdaptador.ViewHolder> {

    private Context context; //Permite relacionar el Adaptador(ClienteAdaptador) con el fragement donde se mostrara el listado de clientes
    private ArrayList<Cliente> listaClienteAuxiliar; //para el adapter y la imprension de datos, administrar el listado
    public int posicionItemSeleccionadoRecyclerView;

    public ClienteAdaptador(Context context){
        this.context = context;
        listaClienteAuxiliar = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Permite enlazar el Adaptador(ClienteAdaptador) con el archivo XML que contiene el cardview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //permite mostrar los datos dentro de los controles del cardview

        //1. Crear un objeto cliente
        Cliente cliente = listaClienteAuxiliar.get(position);
        //2. Mostrar los datos del cliente
        holder.txtApellidosNombres.setText(cliente.getApellidosNombres());
        holder.txtDireccion.setText("Direccion: " + cliente.getDireccion());
        holder.txtTelefono.setText("Telefono: " + cliente.getTelefono());
        holder.txtCiudad.setText("Ciudad : " + cliente.getNombreCiudad());
        holder.txtClienteVip.setText("Cliente VIP: " + (cliente.getVip().equals("1")?"SI":"NO"));
        if (cliente.getVip().equals("1")){
            holder.txtClienteVip.setTextColor(context.getResources().getColor(android.R.color.holo_orange_light));
        }
        if (cliente.getFoto()==null){
            holder.imgCliente.setImageResource(R.drawable.fondo_triangulo_rosa);
        } else {
            //Mostrar la foto real grabada en la base de datos
            holder.imgCliente.setImageBitmap(Helper.base64ToImage(cliente.getFoto()));
        }
    }
    public void cargarDatosCliente(ArrayList<Cliente> listaDatos){
        //Permite recepcionar la lista de clientes de la base de datos
        listaClienteAuxiliar = listaDatos;

        //indicarle al RecyclerView que hay cambios en el listado por lo tanto debe actualizarse
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        //permite obtener la cantidad de elementos que tiene almancenado el arraylist de cliente
        return listaClienteAuxiliar.size();
    }

    //Clase view holder permite gestionar los controles que se encuentran dentro del cardview
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,View.OnLongClickListener{
        //Declarar los controles del cardview
        CircleImageView imgCliente;
        TextView txtApellidosNombres,txtDireccion,txtTelefono,txtClienteVip,txtCiudad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Enlazar los controles java con los controles xml
            imgCliente = itemView.findViewById(R.id.imgCliente);
            txtApellidosNombres = itemView.findViewById(R.id.txtApellidosNombres);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtClienteVip = itemView.findViewById(R.id.txtClienteVip);
            txtCiudad = itemView.findViewById(R.id.txtCiudad);

            //Configurar a la clase ViewHolder para que reconozca los siguientes eveentos
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            //Implementar el menu contextual
            contextMenu.setHeaderTitle("Opciones");
            contextMenu.add(0,1,0,"Eliminar");
            contextMenu.add(0,2,0,"Ubicar en el mapa");
        }

        @Override
        public boolean onLongClick(View view) {
            //Permite obtener la posicion del item seleccionado en el RecyclerView
            posicionItemSeleccionadoRecyclerView  = this.getAdapterPosition();
            Log.e("CLIENTE ID",String.valueOf( Cliente.listadoClientes.get(posicionItemSeleccionadoRecyclerView).getId()) );
            return false;
        }
    }
}
