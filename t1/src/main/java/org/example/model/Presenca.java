package org.example.model;

import java.time.LocalDate;

public class Presenca {
    private String cpf_aluno;
    private LocalDate dataPresenca;

    public Presenca(String cpf_aluno, LocalDate dataPresenca) {
        this.cpf_aluno = cpf_aluno;
        this.dataPresenca = dataPresenca;
    }

    public String getCpf_aluno() {
        return cpf_aluno;
    }

    public void setCpf_aluno(String cpf_aluno) {
        this.cpf_aluno = cpf_aluno;
    }

    public LocalDate getDataPresenca() {
        return dataPresenca;
    }

    public void setDataPresenca(LocalDate dataPresenca) {
        this.dataPresenca = dataPresenca;
    }

    @Override
    public String toString() {
        return "Presenca{" +
                "alunoId=" + cpf_aluno +
                ", dataPresenca=" + dataPresenca +
                '}';
    }
}