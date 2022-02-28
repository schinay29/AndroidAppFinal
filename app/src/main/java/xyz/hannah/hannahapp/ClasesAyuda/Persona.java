package xyz.hannah.hannahapp.ClasesAyuda;

import java.io.Serializable;

public class Persona implements Serializable {
    private String nombre;
    private String correo;
    private Coche coche;


    public Persona(String nombre, String correo){
        this.nombre = nombre;
        this.correo = correo;
    }


}
