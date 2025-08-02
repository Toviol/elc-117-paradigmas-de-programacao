package Entidades;

import java.sql.Timestamp;

public class Aluno {
    private String cpf;
    private String nome;
    private String data_nascimento;

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getCpf() {
        return cpf;
    }
    public String getNome() {
        return nome;
    }
    public String getData_nascimento() {
        return data_nascimento;
    }
}
