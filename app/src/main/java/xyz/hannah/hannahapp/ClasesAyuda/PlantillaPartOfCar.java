package xyz.hannah.hannahapp.ClasesAyuda;

import java.io.Serializable;

public class PlantillaPartOfCar implements Serializable {
    private int id;
    private int idImagen;
    private String nombre;
    private String modelo;
    private String ultFechaCambio;


    public PlantillaPartOfCar(int id, int idImagen, String nombre) {
        this.id = id;
        this.idImagen = idImagen;
        this.nombre = nombre;
    }

    public PlantillaPartOfCar(int id, int idImagen, String nombre, String modelo, String ultFechaCambio) {
        this.id = id;
        this.idImagen = idImagen;
        this.nombre = nombre;
        this.modelo = modelo;
        this.ultFechaCambio = ultFechaCambio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getUltFechaCambio() {
        return ultFechaCambio;
    }

    public void setUltFechaCambio(String ultFechaCambio) {
        this.ultFechaCambio = ultFechaCambio;
    }
}
