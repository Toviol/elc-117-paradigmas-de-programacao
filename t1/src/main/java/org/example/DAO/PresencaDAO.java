package org.example.DAO;

import org.example.model.Presenca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PresencaDAO {
    private Connection conn;

    public PresencaDAO(Connection conn) {
        this.conn = conn;
    }


    public void inserirPresenca(String cpf) {

        String sql = "INSERT INTO Presencas (cpf_aluno) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            pstmt.executeUpdate();
            System.out.println("Presença inserida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir presença: " + e.getMessage());
        }
    }

    public List<Presenca> buscarPresencasNoIntervalo(String cpf, LocalDate dataInicio, LocalDate dataFim) {
        List<Presenca> presencas = new ArrayList<>();
        java.sql.Date sqlDateInicio = java.sql.Date.valueOf(dataInicio);
        java.sql.Date sqlDateFim = java.sql.Date.valueOf(dataFim);

        String sql = "SELECT cpf_aluno, date_atual FROM presencas WHERE cpf_aluno = ? AND date_atual BETWEEN ? AND ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setDate(2, sqlDateInicio);
            stmt.setDate(3, sqlDateFim);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String cpf_aluno = rs.getString("cpf_aluno");
                    LocalDate dataPresenca = rs.getDate("date_atual").toLocalDate();
                    presencas.add(new Presenca(cpf_aluno, dataPresenca));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return presencas;
    }


}
