package org.example.DAO;

import org.example.model.Aluno;
import org.example.model.AlunoPlano;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoPlanoDAO {
    private Connection conn;

    public AlunoPlanoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertAlunoPlano(AlunoPlano alunoPlano) {
        String query = "INSERT INTO AlunoPlano (cpf_aluno, codigo_plano, data_inicio, dados_cartao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, alunoPlano.getCpfAluno());
            statement.setInt(2, alunoPlano.getCodigoPlano());
            statement.setString(3, alunoPlano.getDataInicio());
            statement.setString(4, alunoPlano.getDadosCartao());
            statement.executeUpdate();
            System.out.println("Associação aluno-plano criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar associação aluno-plano: " + e.getMessage());
        }
    }



    public List<AlunoPlano> getPlanosAluno(Aluno aluno) {
        List<AlunoPlano> alunoPlanos = new ArrayList<>();
        String query = "SELECT cpf_aluno, codigo_plano, data_inicio, dados_cartao " +
                "FROM AlunoPlano " +
                "WHERE cpf_aluno = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, aluno.getCpf());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                AlunoPlano alunoPlano = new AlunoPlano();
                alunoPlano.setCpfAluno(resultSet.getString("cpf_aluno"));
                alunoPlano.setCodigoPlano(resultSet.getInt("codigo_plano"));
                alunoPlano.setDataInicio(resultSet.getString("data_inicio"));
                alunoPlano.setDadosCartao(resultSet.getString("dados_cartao"));
                alunoPlanos.add(alunoPlano);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter planos do aluno: " + e.getMessage());
        }
        return alunoPlanos;
    }


}
