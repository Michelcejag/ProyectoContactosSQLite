package utilidades;

public class Utilidades {

//Constantes de los campos

    public static String TAABLA_CONTACTO="Contactos";
    public static   String CAMPO_NOMBRE="nombre";
    public static String CAMPO_TELEFONO="telefono";
    public static String CAMPO_CONTACTO_CONFIANZA="contacto_confianza";

    public static final String CrearTablaContactos="CREATE TABLE " +
            TAABLA_CONTACTO + "(" + CAMPO_NOMBRE + " INT," + CAMPO_TELEFONO + " TEXT," + CAMPO_CONTACTO_CONFIANZA + " TEXT)";



}
