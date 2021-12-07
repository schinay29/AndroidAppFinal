package xyz.hannah.hannahapp.ClasesAyuda;

import java.sql.Date;

public class PartOfCar {
    protected String modelo;
    protected Date ultFechaCambio;

    public Date getUltFechaCambio() {
        return ultFechaCambio;
    }

    public void setUltFechaCambio(Date ultFechaCambio) {
        this.ultFechaCambio = ultFechaCambio;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
