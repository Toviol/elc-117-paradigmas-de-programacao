package ClassesDAO;

import Entidades.Plano;
import conection.ConexaoDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class PlanoDAO {
    public void inserirPlano(Plano plano) throws SQLException {
        String sql = "INSERT INTO planos (codigo, nome, preco) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, plano.getCodigo());
            stmt.setString(2, plano.getNome());
            stmt.setDouble(3, plano.getPreco());
            stmt.executeUpdate();
        }
    }
    public Plano procurarPlano(int chave) throws SQLException {
        String sql = "SELECT * FROM planos WHERE codigo = ?";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chave);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Plano plano = new Plano();
                    plano.setCodigo(rs.getInt("codigo"));
                    plano.setNome(rs.getString("nome"));
                    plano.setPreco(rs.getDouble("preco"));
                    return plano;
                }
            }
        }
        return null;
    }
    public List<Plano> retornaTodosPlanos() throws SQLException {
        String sql = "SELECT * FROM planos";
        List<Plano> planos = new ArrayList<>();
        try (Connection conn = ConexaoDataBase.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Plano plano = new Plano();
                plano.setCodigo(rs.getInt("codigo"));
                plano.setNome(rs.getString("nome"));
                plano.setPreco(rs.getDouble("preco"));
                planos.add(plano);
            }
        }
        return planos;
    }
    public void updatePlano(Plano plano) throws SQLException {
        String sql = "UPDATE planos SET nome = ?, preco = ? WHERE codigo = ?";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plano.getNome());
            stmt.setDouble(2, plano.getPreco());
            stmt.setInt(3, plano.getCodigo());
            stmt.executeUpdate();
        }
    }
    public void deletePlano(int chave) throws SQLException {
        String sql = "DELETE FROM planos WHERE codigo = ?";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chave);
            stmt.executeUpdate();
        }
    }
}
