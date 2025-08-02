package Entidades;

import java.sql.Timestamp;

public class Exercicio {
    private int numero;
    private String nome;
    private String musculos;

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMusculos(String musculos) {
        this.musculos = musculos;
    }

    public int getNumero() {
        return numero;
    }
    public String getNome() {
        return nome;
    }
    public String getMusculos() {
        return musculos;
    }
}
