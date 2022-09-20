package pe.edu.usat.laboratorio.appnavigationdraweractivity.datos;

public class Tablas {
    //Script para generar las tablas de la BD

    //DDL de la tabla ciudad
    public static String tablaCiudad = "create table ciudad (id int primary key, nombre varchar(50) not null);";
    //DML de la tabla ciudad
    public static String tablaCiudadDatos[] =
            {
                    "insert into ciudad(id,nombre) values (1,'Lima');",
                    "insert into ciudad(id,nombre) values (2,'Chiclayo');",
                    "insert into ciudad(id,nombre) values (3,'Trujillo');",
                    "insert into ciudad(id,nombre) values (4,'Piura');",
                    "insert into ciudad(id,nombre) values (5,'Cajamarca');"

            };

    //DDL de la tabla cliente
    public static String tablaCliente = "create table cliente(id INTEGER primary key AUTOINCREMENT, " +
            "apellidos_nombres varchar(80) not null, " +
            "direccion varchar(50) not null, " +
            "telefono varchar(20) not null, " +
            "linea_credito real not null, " +
            "contrasena char(32) not null, " +
            "ciudad_id int not null, " +
            "foto blob, " +
            "vip char(1) not null, " +
            "latitud real not null, " +
            "longitud real not null, " +
            "foreign key (ciudad_id) references ciudad(id) )";

    //DML dela tabla cliente
    public static String tablaClienteDatos[] =
            {
                    "insert into cliente values (1,'MERA MONTENEGRO HUILDER','AV. BALTA 310','074311212',1500,'74c317b55a9cde43fda7a599e3524856',2 " +
                            ",NULL,'1',-6.7604108,-79.861695)",
                    "insert into cliente values (2,'VASQUEZ FERNANDEZ JORDAN','JR. TARAPACA 331','96778812',2000,'74c317b55a9cde43fda7a599e3524856',5 " +
                            ",NULL,'0',-7.158616, -78.522229)"

            };
}
