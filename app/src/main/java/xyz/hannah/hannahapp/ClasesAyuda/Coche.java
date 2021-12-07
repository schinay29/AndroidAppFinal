package xyz.hannah.hannahapp.ClasesAyuda;

public class Coche {
    private String modelo;
    private double kilometros;
    private Filtro filtro;
    private Luces luces;
    private Frenos frenos;
    private Bateria bateria;

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

    public Filtro getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

    public Luces getLuces() {
        return luces;
    }

    public void setLuces(Luces luces) {
        this.luces = luces;
    }

    public Frenos getFrenos() {
        return frenos;
    }

    public void setFrenos(Frenos frenos) {
        this.frenos = frenos;
    }

    public Bateria getBateria() {
        return bateria;
    }

    public void setBateria(Bateria bateria) {
        this.bateria = bateria;
    }

    public Neumatico getNeumatico() {
        return neumatico;
    }

    public void setNeumatico(Neumatico neumatico) {
        this.neumatico = neumatico;
    }

    public Amortiguador getAmortiguador() {
        return amortiguador;
    }

    public void setAmortiguador(Amortiguador amortiguador) {
        this.amortiguador = amortiguador;
    }

    public SistemaDeEscape getSistemaDeEscape() {
        return sistemaDeEscape;
    }

    public void setSistemaDeEscape(SistemaDeEscape sistemaDeEscape) {
        this.sistemaDeEscape = sistemaDeEscape;
    }

    public AceiteLubricante getAceiteLubricante() {
        return aceiteLubricante;
    }

    public void setAceiteLubricante(AceiteLubricante aceiteLubricante) {
        this.aceiteLubricante = aceiteLubricante;
    }

    public CorreaDeDistribucion getCorreaDistribucion() {
        return correaDistribucion;
    }

    public void setCorreaDistribucion(CorreaDeDistribucion correaDistribucion) {
        this.correaDistribucion = correaDistribucion;
    }

    private Neumatico neumatico;
    private Amortiguador amortiguador;
    private SistemaDeEscape sistemaDeEscape;
    private AceiteLubricante aceiteLubricante;
    private CorreaDeDistribucion correaDistribucion;

}
