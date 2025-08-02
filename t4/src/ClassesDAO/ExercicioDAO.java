package ClassesDAO;

import Entidades.Exercicio;
import conection.ConexaoDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class ExercicioDAO {
    public void inserirExercicio(Exercicio exercicio) throws SQLException {
        String sql = "INSERT INTO exercicios (numero, nome, musculos) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exercicio.getNumero());
            stmt.setString(2, exercicio.getNome());
            stmt.setString(3, exercicio.getMusculos());
            stmt.executeUpdate();
        }
    }
    public Exercicio procurarExercicio(int chave) throws SQLException {
        String sql = "SELECT * FROM exercicios WHERE numero = ?";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chave);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Exercicio exercicio = new Exercicio();
                    exercicio.setNumero(rs.getInt("numero"));
                    exercicio.setNome(rs.getString("nome"));
                    exercicio.setMusculos(rs.getString("musculos"));
                    return exercicio;
                }
            }
        }
        return null;
    }
    public List<Exercicio> retornaTodosExercicios() throws SQLException {
        String sql = "SELECT * FROM exercicios";
        List<Exercicio> exercicios = new ArrayList<>();
        try (Connection conn = ConexaoDataBase.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Exercicio exercicio = new Exercicio();
                exercicio.setNumero(rs.getInt("numero"));
                exercicio.setNome(rs.getString("nome"));
                exercicio.setMusculos(rs.getString("musculos"));
                exercicios.add(exercicio);
            }
        }
        return exercicios;
    }
    public void updateExercicio(Exercicio exercicio) throws SQLException {
        String sql = "UPDATE exercicios SET nome = ?, musculos = ? WHERE numero = ?";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, exercicio.getNome());
            stmt.setString(2, exercicio.getMusculos());
            stmt.setInt(3, exercicio.getNumero());
            stmt.executeUpdate();
        }
    }
    public void deleteExercicio(int chave) throws SQLException {
        String sql = "DELETE FROM exercicios WHERE numero = ?";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chave);
            stmt.executeUpdate();
        }
    }
}
