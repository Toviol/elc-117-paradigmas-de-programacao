package Exemplos;

import Entidades.Aluno;
import ClassesDAO.AlunoDAO;
import java.sql.Timestamp;
import java.sql.SQLException;

public class AlunoExemplo {
    public static void main(String[] args) {
        AlunoDAO alunodao = new AlunoDAO();
        try {
            // Exemplo de inserção de dados
            Aluno novoAluno = new Aluno();
            novoAluno.setCpf("IZMvKD19W2");
            novoAluno.setNome("m33wYypbr3");
            novoAluno.setData_nascimento("SGCXEhj1JM");
            alunodao.inserirAluno(novoAluno);

            // Exemplo de busca de dados
            Aluno aluno = alunodao.procurarAluno("IZMvKD19W2");
            if (aluno != null) {
                System.out.println("Dados encontrados:");
                System.out.println("cpf: " + aluno.getCpf());
                System.out.println("nome: " + aluno.getNome());
                System.out.println("data_nascimento: " + aluno.getData_nascimento());
            } else {
                System.out.println("Nenhum dado encontrado.");
            }

            // Exemplo de atualização de dados
            aluno.setCpf("IZMvKD19W2");
            aluno.setNome("OKhA0N1j21");
            aluno.setData_nascimento("5HAeT3GBUl");
            alunodao.updateAluno(aluno);

            // Exemplo de deleção de dados
            alunodao.deleteAluno("IZMvKD19W2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
