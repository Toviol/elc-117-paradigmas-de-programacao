package org.example.model;

public class Treino {
    private int id;
    private String nome;
    private String cpf_aluno;

    public Treino(int id, String nome, String cpf_aluno) {
        this.id = id;
        this.nome = nome;
        this.cpf_aluno = cpf_aluno;
    }

    public Treino() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf_aluno() {
        return cpf_aluno;
    }

    public void setCpf_aluno(String cpf_aluno) {
        this.cpf_aluno = cpf_aluno;
    }

    @Override
    public String toString() {
        return "Id: " + id + " / Nome: " + nome;
    }
}

