package org.example.DAO;

import org.example.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class TreinoDAO {
    private Connection conn;

    public TreinoDAO(Connection conn) {
        this.conn = conn;
    }


    public int insertTreinos(Aluno aluno, String nome) {
        String query = "INSERT INTO Treinos (nome, cpf_aluno) VALUES (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, nome);
            statement.setString(2, aluno.getCpf());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;

            }
        } catch (SQLException e) {
            System.out.println("Erro ao criar treino: " + e.getMessage());
        }
        return 0;
    }

    public void associarExercicio(int idTreino, int idExercicio) {
        String query = "INSERT INTO TreinosExercicios (id_treino, id_exercicio) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idTreino);
            stmt.setInt(2, idExercicio);
            stmt.executeUpdate();

            System.out.println("Exercicio adicionado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao associar exercício ao plano: " + ex.getMessage());
        }
    }

    public List<Treino> buscarTreinosCpf(String cpf) {
        List<Treino> treinos = new ArrayList<>();
        String query = "SELECT id, nome, cpf_aluno FROM treinos WHERE cpf_aluno = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cpf);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Treino treino = new Treino();
                treino.setId(resultSet.getInt("id"));
                treino.setNome(resultSet.getString("nome"));
                treino.setCpf_aluno(resultSet.getString("cpf_aluno"));
                treinos.add(treino);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar treinos por CPF: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro geral ao pesquisar treinos por CPF: " + e.getMessage());
        }
        return treinos.isEmpty() ? null : treinos;
    }

    public Treino buscarTreinoId(int id) {
        String query = "SELECT id, nome, cpf_aluno FROM treinos WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Treino treino = new Treino();
                treino.setId(resultSet.getInt("id"));
                treino.setNome(resultSet.getString("nome"));
                treino.setCpf_aluno(resultSet.getString("cpf_aluno"));
                return treino;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar Treino por ID: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro geral ao pesquisar Treino por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Exercicio> buscarExerciciosTreino(Treino treino) {
        List<Exercicio> exercicios = new ArrayList<>();
        String query = "SELECT numero, nome, musculos FROM exercicios JOIN treinosexercicios ON exercicios.numero = treinosexercicios.id_exercicio WHERE treinosexercicios.id_treino = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)){
                statement.setInt(1, treino.getId());
             ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercicio exercicio = new Exercicio();
                exercicio.setNumero(resultSet.getInt("numero"));
                exercicio.setNome(resultSet.getString("nome"));
                exercicio.setMusculos(resultSet.getString("musculos"));
                exercicios.add(exercicio);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar exercicios do treino: " + e.getMessage());
        }
        return exercicios;
    }

    public void alterarNomeTreino(int idTreino, String novoNome) {
        String query = "UPDATE treinos SET nome = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, novoNome);
            statement.setInt(2, idTreino);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Nome do treino alterado com sucesso!");
            } else {
                System.out.println("Treino não encontrado para o ID: " + idTreino);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao alterar nome do treino: " + e.getMessage());
        }
    }

    public void desvincularExercicio(int idTreino, int idExercicio) {
        String query = "DELETE FROM treinosexercicios WHERE id_treino = ? AND id_exercicio = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idTreino);
            statement.setInt(2, idExercicio);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Exercício desvinculado do treino com sucesso!");
            } else {
                System.out.println("Exercício não encontrado no treino.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao desvincular exercício do treino: " + e.getMessage());
        }
    }

    public void deletarTreino(int idTreino) {
        String deleteTreinosExerciciosQuery = "DELETE FROM TreinosExercicios WHERE id_treino = ?";
        String deleteTreinoQuery = "DELETE FROM Treinos WHERE id = ?";
        try {
            try (PreparedStatement deleteTreinosExerciciosStatement = conn.prepareStatement(deleteTreinosExerciciosQuery)) {
                deleteTreinosExerciciosStatement.setInt(1, idTreino);
                deleteTreinosExerciciosStatement.executeUpdate();
            }

            try (PreparedStatement deleteTreinoStatement = conn.prepareStatement(deleteTreinoQuery)) {
                deleteTreinoStatement.setInt(1, idTreino);
                int rowsDeleted = deleteTreinoStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Treino deletado com sucesso!");
                } else {
                    System.out.println("Treino não encontrado para o ID: " + idTreino);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar treino: " + e.getMessage());
        }
    }


    public List<TreinoExercicio> getExerciciosAtivosPorTreino(int idTreino) {
        String sql = "SELECT te.*, e.nome AS nome_exercicio " +
                "FROM TreinosExercicios te " +
                "JOIN exercicios e ON te.id_exercicio = e.numero " +
                "WHERE te.id_treino = ? AND te.ativo = TRUE";
        List<TreinoExercicio> exercicios = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTreino);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TreinoExercicio exercicio = new TreinoExercicio(
                            rs.getInt("id"),
                            rs.getInt("id_treino"),
                            rs.getInt("id_exercicio"),
                            rs.getString("nome_exercicio"),
                            rs.getInt("series"),
                            rs.getInt("repeticoes_min"),
                            rs.getInt("repeticoes_max"),
                            rs.getInt("carga"),
                            rs.getDouble("descanso"),
                            rs.getBoolean("ativo")
                    );
                    exercicios.add(exercicio);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar exercícios: " + e.getMessage());
        }

        return exercicios;
    }

    public List<TreinoExercicio> getTodosExerciciosTreino(int idTreino) {
        String sql = "SELECT te.*, e.nome AS nome_exercicio " +
                "FROM TreinosExercicios te " +
                "JOIN exercicios e ON te.id_exercicio = e.numero " +
                "WHERE te.id_treino = ?";
        List<TreinoExercicio> exercicios = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTreino);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TreinoExercicio exercicio = new TreinoExercicio(
                            rs.getInt("id"),
                            rs.getInt("id_treino"),
                            rs.getInt("id_exercicio"),
                            rs.getString("nome_exercicio"),
                            rs.getInt("series"),
                            rs.getInt("repeticoes_min"),
                            rs.getInt("repeticoes_max"),
                            rs.getInt("carga"),
                            rs.getDouble("descanso"),
                            rs.getBoolean("ativo")
                    );
                    exercicios.add(exercicio);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar exercícios: " + e.getMessage());
        }

        return exercicios;
    }


    public TreinoExercicio concluirExercicio(int idTreino, int idExercicio) {
        String sql = "UPDATE TreinosExercicios SET ativo = FALSE WHERE id_treino = ? AND id_exercicio = ? RETURNING id, carga";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTreino);
            stmt.setInt(2, idExercicio);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TreinoExercicio exercicio = new TreinoExercicio();
                    exercicio.setId(rs.getInt("id"));
                    exercicio.setCarga(rs.getInt("carga"));
                    System.out.println("Exercício concluído com sucesso.");
                    return exercicio;
                } else {
                    System.out.println("Nenhum exercício encontrado para o ID de treino e exercício fornecidos.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao concluir exercício: " + e.getMessage());
            return null;
        }
    }


    public TreinoExercicio buscarIdTreinoExercicio(int idTreino, int idExercicio) {
        String sql = "SELECT id FROM TreinosExercicios WHERE id_treino = ? AND id_exercicio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTreino);
            stmt.setInt(2, idExercicio);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TreinoExercicio exercicio = new TreinoExercicio();
                    exercicio.setId(rs.getInt("id"));
                    return exercicio;
                } else {
                    System.out.println("Nenhum ID encontrado para o treino e exercício fornecidos.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar id TreinoExercicio: " + e.getMessage());
            return null;
        }
    }

    public int getCargaMaiorId(int idTreinoExercicio) {
        String sql = "SELECT carga FROM ProgressaoCarga WHERE id_treinosexercicio = ? ORDER BY id DESC LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTreinoExercicio);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("carga");
                } else {
                    System.out.println("Nenhuma carga encontrada para o id_treinosexercicio fornecido.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar a carga: " + e.getMessage());
            return -1;
        }
    }

    public void adicionarProgressaoCarga(int idTreinosExercicio, int carga) {
        String sql = "INSERT INTO ProgressaoCarga (id_treinosexercicio, carga) VALUES (?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idTreinosExercicio);
            statement.setInt(2, carga);
            statement.executeUpdate();
            System.out.println("Progressão de carga adicionada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar progressão de carga: " + e.getMessage());
        }
    }

    public List<ProgressaoCarga> getProgressoesCargaPorTreinoExercicioId(int idTreinosExercicio) {
        List<ProgressaoCarga> progressoes = new ArrayList<>();
        String sql = "SELECT * FROM ProgressaoCarga WHERE id_treinosexercicio = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idTreinosExercicio);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int carga = resultSet.getInt("carga");
                LocalDate date_atual = resultSet.getDate("date_atual").toLocalDate();

                ProgressaoCarga progressao = new ProgressaoCarga(id, idTreinosExercicio, carga, date_atual);
                progressoes.add(progressao);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter as progressões de carga: " + e.getMessage());
        }

        return progressoes;
    }



    public boolean modificarCargaExercicio(int idTreino, int idExercicio, int novaCarga) {
        String sql = "UPDATE TreinosExercicios SET carga = ? WHERE id_treino = ? AND id_exercicio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novaCarga);
            stmt.setInt(2, idTreino);
            stmt.setInt(3, idExercicio);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Carga do exercício atualizada com sucesso.");
                return true;
            } else {
                System.out.println("Nenhum exercício encontrado para o ID de treino e exercício fornecidos.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao modificar a carga do exercício: " + e.getMessage());
            return false;
        }
    }

    public boolean concluirTreino(int idTreino) {
        String updateExerciciosSql = "UPDATE TreinosExercicios SET ativo = TRUE WHERE id_treino = ?";
        String updateTreinoSql = "UPDATE Treinos SET ativo = FALSE WHERE id = ?";

        try (PreparedStatement updateExerciciosStmt = conn.prepareStatement(updateExerciciosSql);
             PreparedStatement updateTreinoStmt = conn.prepareStatement(updateTreinoSql)) {

            conn.setAutoCommit(false); // iniciar a transação

            // Atualizar exercícios para ativo = FALSE
            updateExerciciosStmt.setInt(1, idTreino);
            updateExerciciosStmt.executeUpdate();

            // Atualizar treino para ativo = FALSE
            updateTreinoStmt.setInt(1, idTreino);
            updateTreinoStmt.executeUpdate();

            conn.commit(); // commit da transação
            System.out.println("Treino concluído com sucesso.");
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback(); // rollback da transação em caso de erro
            } catch (SQLException rollbackEx) {
                System.out.println("Erro ao realizar rollback: " + rollbackEx.getMessage());
            }
            System.out.println("Erro ao concluir treino: " + e.getMessage());
            return false;
        } finally {
            try {
                conn.setAutoCommit(true); // restaurar o auto-commit padrão
            } catch (SQLException ex) {
                System.out.println("Erro ao restaurar auto-commit: " + ex.getMessage());
            }
        }
    }

    public void inserirDadosExercicio(int idTreino, int idExercicio, int series, int repeticoesMin, int repeticoesMax, int carga, double descanso) {
        String sql = "UPDATE TreinosExercicios SET series = ?, repeticoes_min = ?, repeticoes_max = ?, carga = ?, descanso = ? " +
                "WHERE id_treino = ? AND id_exercicio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, series);
            stmt.setInt(2, repeticoesMin);
            stmt.setInt(3, repeticoesMax);
            stmt.setInt(4, carga);
            stmt.setDouble(5, descanso);
            stmt.setInt(6, idTreino);
            stmt.setInt(7, idExercicio);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Dados do exercício atualizados com sucesso.");
            } else {
                System.out.println("Exercicio não cadastrado no treino.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados do exercício: " + e.getMessage());
        }
    }


}
