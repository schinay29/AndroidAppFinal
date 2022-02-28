package xyz.hannah.hannahapp.ClasesAyuda;

import java.io.Serializable;
import java.util.Date;


public class PartOfCar {
    private String nombre;
    private String modelo;
    private String ultFechaCambio;

    public PartOfCar(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getUltFechaCambio() {
        return ultFechaCambio;
    }

    public void setUltFechaCambio(String ultFechaCambio) {
        this.ultFechaCambio = ultFechaCambio;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
