package xyz.hannah.hannahapp.ClasesAyuda;

import java.io.Serializable;
import java.util.Date;


public class PartOfCar {
    protected String modelo;
    protected String ultFechaCambio;


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
