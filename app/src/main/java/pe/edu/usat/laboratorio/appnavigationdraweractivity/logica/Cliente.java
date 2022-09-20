package pe.edu.usat.laboratorio.appnavigationdraweractivity.logica;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pe.edu.usat.laboratorio.appnavigationdraweractivity.datos.Conexion;

public class Cliente extends Conexion {

    private int id;
    private String apellidosNombres;
    private String direccion;
    private String telefono;
    private double lineaCredito;
    private String contrasena;
    private int ciudadId;
    private String foto;
    private String vip;
    private double latitud;
    private double longitud;

    private String nombreCiudad;

    //Definir un ArrayList para almacenar los datos de clientes
    public static ArrayList<Cliente> listadoClientes = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellidosNombres() {
        return apellidosNombres;
    }

    public void setApellidosNombres(String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getLineaCredito() {
        return lineaCredito;
    }

    public void setLineaCredito(double lineaCredito) {
        this.lineaCredito = lineaCredito;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(int ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public void cargarDatosCliente(){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "select c.*,cc.nombre as ciudad from cliente c inner join ciudad cc on (c.ciudad_id == cc.id) " +
                "order by c.apellidos_nombres";

        Cursor cursor = db.rawQuery(sql,null);

        listadoClientes.clear();

        while(cursor.moveToNext()){
            Cliente cliente = new Cliente();
            cliente.setId(cursor.getInt(0));
            cliente.setApellidosNombres(cursor.getString(1));
            cliente.setDireccion(cursor.getString(2));
            cliente.setTelefono(cursor.getString(3));
            cliente.setLineaCredito(cursor.getDouble(4));
            cliente.setContrasena(cursor.getString(5));
            cliente.setCiudadId(cursor.getInt(6));
            cliente.setFoto(cursor.getString(7));
            cliente.setVip(cursor.getString(8));
            cliente.setLatitud(cursor.getDouble(9));
            cliente.setLongitud(cursor.getDouble(10));
            cliente.setNombreCiudad(cursor.getString(11));

            listadoClientes.add(cliente);

        }
    }

    //id interno del registro que voy agregando
    public long insertar(){
        long r = 0;
        try {
            //Configurar a la variable db, para que funcione en modo escritura
            SQLiteDatabase db = this.getReadableDatabase();
            //Preparar los datos que se registran en la tabla cliente
            ContentValues values = new ContentValues();
            //Configurar los datos para cada columna de la tabla cliente
            values.put("apellidos_nombres",this.getApellidosNombres());
            values.put("direccion",this.getDireccion());
            values.put("telefono",this.getTelefono());
            values.put("linea_credito",this.getLineaCredito());
            values.put("contrasena",this.getContrasena());
            values.put("ciudad_id",this.getCiudadId());
            values.put("foto",this.getFoto());
            values.put("vip",this.getVip());
            values.put("latitud",this.getLatitud());
            values.put("longitud",this.getLongitud());
            //Insertar en la tabla cliente
            r = db.insert(" cliente",null,values);
        } catch (Exception e){
            e.printStackTrace();

        }
        return  r;
    }

    public long eliminar(){
        long r = 0;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String idClienteEliminar = String.valueOf(this.getId());
            r = db.delete("cliente","id = ?",new String[]{idClienteEliminar});
            //and ciudad_id = ? , new String[]{idClienteEliminar,idCiudadEliminar}
        }catch (Exception e){
            e.printStackTrace();
        }
       return r;
    }

}
