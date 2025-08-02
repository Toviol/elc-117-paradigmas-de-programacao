package org.example.model;

import java.time.LocalDate;

public class ProgressaoCarga {
    private int id;
    private int idTreinosExercicio;
    private int carga;
    private LocalDate date_atual;

    public ProgressaoCarga(int id, int idTreinosExercicio, int carga, LocalDate date_atual) {
        this.id = id;
        this.idTreinosExercicio = idTreinosExercicio;
        this.carga = carga;
        this.date_atual = date_atual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTreinosExercicio() {
        return idTreinosExercicio;
    }

    public void setIdTreinosExercicio(int idTreinosExercicio) {
        this.idTreinosExercicio = idTreinosExercicio;
    }

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }

    public LocalDate getDate_atual() {
        return date_atual;
    }

    public void setDate_atual(LocalDate date_atual) {
        this.date_atual = date_atual;
    }

    @Override
    public String toString() {
        return
                "Carga: "+carga+
                "kg   Data:"+date_atual+
                " | ";
    }
}
