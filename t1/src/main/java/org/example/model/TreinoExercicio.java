package org.example.model;

public class TreinoExercicio {
    private int id;
    private int idTreino;
    private int idExercicio;
    private String nomeExercicio;
    private int series;
    private int repeticoesMin;
    private int repeticoesMax;
    private int carga;
    private double descanso;
    private boolean ativo;


    public TreinoExercicio(int id, int idTreino, int idExercicio, String nomeExercicio, int series, int repeticoesMin, int repeticoesMax, int carga, double descanso, boolean ativo) {
        this.id = id;
        this.idTreino = idTreino;
        this.idExercicio = idExercicio;
        this.nomeExercicio = nomeExercicio;
        this.series = series;
        this.repeticoesMin = repeticoesMin;
        this.repeticoesMax = repeticoesMax;
        this.carga = carga;
        this.descanso = descanso;
        this.ativo = ativo;
    }

    public TreinoExercicio() {

    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTreino() {
        return idTreino;
    }

    public void setIdTreino(int idTreino) {
        this.idTreino = idTreino;
    }

    public int getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(int idExercicio) {
        this.idExercicio = idExercicio;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticoesMin() {
        return repeticoesMin;
    }

    public void setRepeticoesMin(int repeticoesMin) {
        this.repeticoesMin = repeticoesMin;
    }

    public int getRepeticoesMax() {
        return repeticoesMax;
    }

    public void setRepeticoesMax(int repeticoesMax) {
        this.repeticoesMax = repeticoesMax;
    }

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }

    public double getDescanso() {
        return descanso;
    }

    public void setDescanso(double descanso) {
        this.descanso = descanso;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
