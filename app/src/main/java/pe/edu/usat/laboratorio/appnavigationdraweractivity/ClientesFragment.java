package pe.edu.usat.laboratorio.appnavigationdraweractivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientesFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomMenuCliente;

    public ClientesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);
        //Linked button navigation view
        bottomMenuCliente = view.findViewById(R.id.bottom_menu_clientes);
        bottomMenuCliente.setOnNavigationItemSelectedListener(this);

        //Show ClienteAgregar by default
        this.onNavigationItemSelected(bottomMenuCliente.getMenu().getItem(0));
        return view;
    }
//SQLITE
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = new Fragment();
        int id = item.getItemId();
        if (id == R.id.opcionAgregar) {
            fragment = new ClientesAgregarFragment();
        } else if (id == R.id.opcionListado){
            fragment = new ClienteListadoFragmemt();

        }
        FragmentTransaction fragmentTransaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_clientes,fragment).commit();
        return true;
    }
}