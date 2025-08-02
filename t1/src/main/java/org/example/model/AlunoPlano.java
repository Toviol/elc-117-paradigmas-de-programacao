package org.example.model;

public class AlunoPlano {
    private String cpfAluno;
    private int codigoPlano;
    private String dataInicio;
    private String dadosCartao;

    public AlunoPlano(String cpfAluno, int codigoPlano, String dataInicio, String dadosCartao) {
        this.cpfAluno = cpfAluno;
        this.codigoPlano = codigoPlano;
        this.dataInicio = dataInicio;
        this.dadosCartao = dadosCartao;
    }

    public AlunoPlano() {

    }

    public String getCpfAluno() {
        return cpfAluno;
    }

    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }

    public int getCodigoPlano() {
        return codigoPlano;
    }

    public void setCodigoPlano(int codigoPlano) {
        this.codigoPlano = codigoPlano;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDadosCartao() {
        return dadosCartao;
    }

    public void setDadosCartao(String dadosCartao) {
        this.dadosCartao = dadosCartao;
    }

    @Override
    public String toString() {
        return "AlunoPlano{" +
                "cpfAluno=" + cpfAluno +
                ", codigoPlano=" + codigoPlano +
                ", dataInicio='" + dataInicio + '\'' +
                ", dadosCartao='" + dadosCartao + '\'' +
                '}';
    }
}
