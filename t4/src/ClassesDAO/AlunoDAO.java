package ClassesDAO;

import Entidades.Aluno;
import conection.ConexaoDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class AlunoDAO {
    public void inserirAluno(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (cpf, nome, data_nascimento) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getData_nascimento());
            stmt.executeUpdate();
        }
    }
    public Aluno procurarAluno(String chave) throws SQLException {
        String sql = "SELECT * FROM alunos WHERE cpf = ?";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, chave);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setCpf(rs.getString("cpf"));
                    aluno.setNome(rs.getString("nome"));
                    aluno.setData_nascimento(rs.getString("data_nascimento"));
                    return aluno;
                }
            }
        }
        return null;
    }
    public List<Aluno> retornaTodosAlunos() throws SQLException {
        String sql = "SELECT * FROM alunos";
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = ConexaoDataBase.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setCpf(rs.getString("cpf"));
                aluno.setNome(rs.getString("nome"));
                aluno.setData_nascimento(rs.getString("data_nascimento"));
                alunos.add(aluno);
            }
        }
        return alunos;
    }
    public void updateAluno(Aluno aluno) throws SQLException {
        String sql = "UPDATE alunos SET nome = ?, data_nascimento = ? WHERE cpf = ?";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getData_nascimento());
            stmt.setString(3, aluno.getCpf());
            stmt.executeUpdate();
        }
    }
    public void deleteAluno(String chave) throws SQLException {
        String sql = "DELETE FROM alunos WHERE cpf = ?";
        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, chave);
            stmt.executeUpdate();
        }
    }
}
