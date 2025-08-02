package org.example.DAO;

import org.example.model.Exercicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExercicioDAO {
    private Connection conn;

    public ExercicioDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertExercicio(Exercicio exercicio) {
        String query = "INSERT INTO exercicios (numero, nome, musculos) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, exercicio.getNumero());
            statement.setString(2, exercicio.getNome());
            statement.setString(3, exercicio.getMusculos());
            statement.executeUpdate();
            System.out.println("Exercício inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir exercício: " + e.getMessage());
        }
    }

    public Exercicio pesquisarNumero(int numero) {
        String query = "SELECT numero, nome, musculos FROM exercicios WHERE numero = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, numero);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Exercicio exercicio = new Exercicio();
                    exercicio.setNumero(resultSet.getInt("numero"));
                    exercicio.setNome(resultSet.getString("nome"));
                    exercicio.setMusculos(resultSet.getString("musculos"));
                    return exercicio;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar exercício por número: " + e.getMessage());
        }
        return null;
    }

    public List<Exercicio> buscarTodos() {
        List<Exercicio> exercicios = new ArrayList<>();
        String query = "SELECT numero, nome, musculos FROM exercicios";
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Exercicio exercicio = new Exercicio();
                exercicio.setNumero(resultSet.getInt("numero"));
                exercicio.setNome(resultSet.getString("nome"));
                exercicio.setMusculos(resultSet.getString("musculos"));
                exercicios.add(exercicio);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar todos os exercícios: " + e.getMessage());
        }
        return exercicios;
    }

    public void updateExercicio(Exercicio exercicio) {
        String query = "UPDATE exercicios SET nome = ?, musculos = ? WHERE numero = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, exercicio.getNome());
            statement.setString(2, exercicio.getMusculos());
            statement.setInt(3, exercicio.getNumero());
            statement.executeUpdate();
            System.out.println("Exercício atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar exercício: " + e.getMessage());
        }
    }

    public void deleteExercicio(int numero) {
        String query = "DELETE FROM exercicios WHERE numero = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, numero);
            statement.executeUpdate();
            System.out.println("Exercício removido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao remover exercício: " + e.getMessage());
        }
    }
}