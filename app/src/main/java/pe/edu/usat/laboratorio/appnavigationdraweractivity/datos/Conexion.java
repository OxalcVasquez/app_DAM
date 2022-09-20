package pe.edu.usat.laboratorio.appnavigationdraweractivity.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {

    public static Context contextApp; //Aplicacion donde se instalara la base de datos
    public static String nombreBD="comercial";
    public static int versionBD = 1;

    public Conexion(){
        //Crear la base de BD
        //Factory : Cursor para poder acceder a la BD, cargar un select
        super(contextApp,nombreBD,null,versionBD);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Instrucciones DDL y DML, solo se ejecuta una vez al instalar la app y abrirlo por primera vez

        //Ejecutar el script DDL de la tabla ciudad
        sqLiteDatabase.execSQL(Tablas.tablaCiudad);

        ///Ejectuar el script DDL de la tabla cliente
        sqLiteDatabase.execSQL(Tablas.tablaCliente);

        //Ejecutar el Script DML de la tabla ciudad
        for (int i = 0; i < Tablas.tablaCiudadDatos.length; i++) {
            sqLiteDatabase.execSQL(Tablas.tablaCiudadDatos[i]);
        }

        //Ejecutar el Script DML de la tabla cliente
        for (int i = 0; i < Tablas.tablaClienteDatos.length; i++) {
            sqLiteDatabase.execSQL(Tablas.tablaClienteDatos[i]);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Se ejecuta al abrir la aplicacion, se podria utilizar para actualizar la BD
        //sqLiteDatabase.execSQL("alter table");
    }
}
