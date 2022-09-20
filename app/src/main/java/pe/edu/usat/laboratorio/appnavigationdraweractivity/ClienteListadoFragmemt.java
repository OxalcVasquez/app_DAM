package pe.edu.usat.laboratorio.appnavigationdraweractivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import pe.edu.usat.laboratorio.appnavigationdraweractivity.adaptador.ClienteAdaptador;
import pe.edu.usat.laboratorio.appnavigationdraweractivity.logica.Cliente;
import pe.edu.usat.laboratorio.appnavigationdraweractivity.util.Helper;

public class ClienteListadoFragmemt extends Fragment {

    RecyclerView rvCliente;
    ClienteAdaptador adaptador;

    public ClienteListadoFragmemt() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cliente_listado, container, false);

        // Asignar titulo
        this.getActivity().setTitle("Listado de clientes");

        //Configurar el recycler view
        rvCliente = view.findViewById(R.id.rv_cliente);
        rvCliente.setHasFixedSize(true);
        rvCliente.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Enlazar este fragement con el adpatador
        adaptador = new ClienteAdaptador(this.getContext());

        //Asignarle al RecyclerView el adaptador que administra el listado
        rvCliente.setAdapter(adaptador);

        //Llamar al metodo listar
        listar();
        return view;
    }

    private void listar(){
        //Llamar al metodo cargarDatosCliente -> Clase Cliente
        new Cliente().cargarDatosCliente();

        //Enviar lista de clientes al adaptador
        adaptador.cargarDatosCliente(Cliente.listadoClientes);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Identificar la opcion seleccionada en el menu contextual
        switch (item.getItemId()){
            case 1: //opcion eliminar
                Helper.mensajeConfirmacion(this.getActivity(),"Desea eliminar el registro?"," ELIMINAR","CANCELAR",new EliminarClienteTask());
                break;
            case 2: //opcion ubucar en el mapap
                break;
        }

        return super.onContextItemSelected(item);
    }

    class EliminarClienteTask implements Runnable{

        @Override
        public void run() {
            eliminar();
        }
    }

    private void eliminar(){
        //Obtener la posicion del item seleccionado en el item view
        int pos = adaptador.posicionItemSeleccionadoRecyclerView;
        // Obtener el id del cliente a eliminar
        int id = Cliente.listadoClientes.get(pos).getId();

        //Instanciar la clase cliente
        Cliente cliente = new Cliente();
        cliente.setId(id);
        long r = cliente.eliminar();
        if (r > 0){
            listar();
            Helper.mensajeInformacion(this.getContext(),"Cliente","Se ha eliminado correctamente");
        }
    }

}