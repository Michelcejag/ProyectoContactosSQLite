package tablas;

import java.io.Serializable;

public class Contacto implements Serializable {


    private String nombre;
    private  int numero;
    private String contactoConfianza;

    public Contacto(String nombre, int numero, String contactoConfianza) {
        this.nombre = nombre;
        this.numero = numero;
        this.contactoConfianza=contactoConfianza;
    }

    public Contacto(){

    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getContactoConfianza() {
        return contactoConfianza;
    }

    public void setContactoConfianza(String contactoConfianza) {
        this.contactoConfianza = contactoConfianza;
    }
}
