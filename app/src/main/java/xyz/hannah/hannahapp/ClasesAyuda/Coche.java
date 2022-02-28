package xyz.hannah.hannahapp.ClasesAyuda;

import java.io.Serializable;
import java.util.List;

public class Coche implements Serializable {
    private String matricula;
    private String modelo;
    private double kilometros;
    private List<PartOfCar> partesDelCoche;

    public Coche() {
    }

    public Coche(String matricula, String modelo, double kilometros) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.kilometros = kilometros;
    }

    public Coche(String matricula, String modelo, double kilometros, List<PartOfCar> partesDelCoche) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.kilometros = kilometros;
        this.partesDelCoche = partesDelCoche;
    }

    public List<PartOfCar> getPartesDelCoche() {
        return partesDelCoche;
    }

    public void setPartesDelCoche(List<PartOfCar> partesDelCoche) {
        this.partesDelCoche = partesDelCoche;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getKilometros() {
        return kilometros;
    }

    public void setKilometros(double kilometros) {
        this.kilometros = kilometros;
    }
}
