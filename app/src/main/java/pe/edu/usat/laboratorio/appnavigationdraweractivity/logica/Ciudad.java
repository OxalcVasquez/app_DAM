package pe.edu.usat.laboratorio.appnavigationdraweractivity.logica;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pe.edu.usat.laboratorio.appnavigationdraweractivity.datos.Conexion;

public class Ciudad extends Conexion {
    //Definir los atributos
    private int id;
    private String nombre;

    //Definir una lista para almacenar los registros
    public static ArrayList<Ciudad> listaCiudad = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private void cargarDatosCiudad(){
        //Leer los registros de la tabla ciudad
        SQLiteDatabase bd = this.getReadableDatabase();

        //Definir la consulta SQL
        String sql = "select * from ciudad order by nombre";

        //Ejecutar consulta SQL
        Cursor cursorCiudad = bd.rawQuery(sql,null);

        //Limpiar la lista donde se almacenara los registros de la tabla ciudad
        listaCiudad.clear();

        //Agregar los registros a la lista
        while (cursorCiudad.moveToNext()){
            Ciudad ciudad = new Ciudad();
            ciudad.setId(cursorCiudad.getInt(0)); //id
            ciudad.setNombre(cursorCiudad.getString(1)); //String
            listaCiudad.add(ciudad);
        }

    }

    public String[] obtenerNombreCiudades() {
        //Ejecutar el metodo que permite cargar las  ciudades registradas en la tabla ciudad
        this.cargarDatosCiudad();
        //Definir un array de satring para alamacenar los nombre de ciudades
        String nombreCiudad[] = new String[listaCiudad.size()];

        for (int i = 0; i < listaCiudad.size() ; i++) {
            nombreCiudad[i] = listaCiudad.get(i).getNombre();
        }

        return nombreCiudad;
    }
}
