package org.example.DAO;

import org.example.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private Connection conn;

    public AlunoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertAluno(Aluno aluno) {
        String query = "INSERT INTO alunos (cpf, nome, data_nascimento) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, aluno.getCpf());
            statement.setString(2, aluno.getNome());
            statement.setString(3, aluno.getData_nascimento());
            statement.executeUpdate();
            System.out.println("Aluno inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir aluno: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro geral ao inserir aluno: " + e.getMessage());
        }
    }

    public Aluno pesquisarCpf(String cpf) {
        String query = "SELECT cpf, nome, data_nascimento FROM alunos WHERE cpf = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Aluno aluno = new Aluno();
                aluno.setCpf(resultSet.getString("cpf"));
                aluno.setNome(resultSet.getString("nome"));
                aluno.setData_nascimento(resultSet.getString("data_nascimento"));
                return aluno;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar aluno por CPF: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro geral ao pesquisar aluno por CPF: " + e.getMessage());
        }
        return null;
    }

    public List<Aluno> pesquisarNome(String nome) {
        List<Aluno> alunosEncontrados = new ArrayList<>();
        String query = "SELECT cpf, nome, data_nascimento FROM alunos WHERE nome LIKE ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + nome + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setCpf(resultSet.getString("cpf"));
                    aluno.setNome(resultSet.getString("nome"));
                    aluno.setData_nascimento(resultSet.getString("data_nascimento"));
                    alunosEncontrados.add(aluno);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar aluno por nome: " + e.getMessage());
        }
        return alunosEncontrados;
    }

    public List<Aluno> todosAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        String query = "SELECT cpf, nome, data_nascimento FROM alunos";
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Aluno aluno = new Aluno();
                aluno.setCpf(resultSet.getString("cpf"));
                aluno.setNome(resultSet.getString("nome"));
                aluno.setData_nascimento(resultSet.getString("data_nascimento"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar todos os alunos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro geral ao recuperar todos os alunos: " + e.getMessage());
        }
        return alunos;
    }

    public void updateAluno(Aluno aluno, String cpfantigo) {
        String query = "UPDATE alunos SET cpf = ?, nome = ?, data_nascimento = ? WHERE cpf = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, aluno.getCpf());
            statement.setString(2, aluno.getNome());
            statement.setString(3, aluno.getData_nascimento());
            statement.setString(4, cpfantigo);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro geral ao atualizar aluno: " + e.getMessage());
        }
    }

    public void deleteAluno(String cpf) {
        String query = "DELETE FROM alunos WHERE cpf = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, cpf);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir aluno: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro geral ao excluir aluno: " + e.getMessage());
        }
    }
}