package org.example.DAO;

import org.example.model.Plano;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAO {
    private Connection conn;

    public PlanoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertPlano(Plano plano) {
        String query = "INSERT INTO planos (codigo, nome, preco) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, plano.getCodigo());
            statement.setString(2, plano.getNome());
            statement.setDouble(3, plano.getPreco());
            statement.executeUpdate();
            System.out.println("Plano inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir plano: " + e.getMessage());
        }
    }

    public Plano pesquisarCodigo(int codigo) {
        String query = "SELECT codigo, nome, preco FROM planos WHERE codigo = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, codigo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Plano plano = new Plano();
                    plano.setCodigo(resultSet.getInt("codigo"));
                    plano.setNome(resultSet.getString("nome"));
                    plano.setPreco(resultSet.getDouble("preco"));
                    return plano;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar plano por código: " + e.getMessage());
        }
        return null;
    }

    public List<Plano> buscarTodos() {
        List<Plano> planos = new ArrayList<>();
        String query = "SELECT codigo, nome, preco FROM planos";
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Plano plano = new Plano();
                plano.setCodigo(resultSet.getInt("codigo"));
                plano.setNome(resultSet.getString("nome"));
                plano.setPreco(resultSet.getDouble("preco"));
                planos.add(plano);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar todos os planos: " + e.getMessage());
        }
        return planos;
    }

    public void updatePlano(Plano plano) {
        String query = "UPDATE planos SET nome = ?, preco = ? WHERE codigo = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, plano.getNome());
            statement.setDouble(2, plano.getPreco());
            statement.setInt(3, plano.getCodigo());
            statement.executeUpdate();
            System.out.println("Plano atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar plano: " + e.getMessage());
        }
    }

    public void deletePlano(int codigo) {
        String query = "DELETE FROM planos WHERE codigo = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, codigo);
            statement.executeUpdate();
            System.out.println("Plano removido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao remover plano: " + e.getMessage());
        }
    }
}