package org.example.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class DbFunctions {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname, user, pass);
            System.out.println("Conexão estabelecida!");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver não encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Conexão sem sucesso. Problemas com o banco de dados.");
            e.printStackTrace();
        }
        return conn;
    }

    public void createTableAlunos(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS alunos (" +
                "cpf   VARCHAR(15) PRIMARY KEY," +
                "nome VARCHAR(255) NOT NULL," +
                "data_nascimento VARCHAR(11) NOT NULL)";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Tabela alunos criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela alunos: " + e.getMessage());
        }
    }

    public void createTablePlanos(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS planos (" +
                "codigo INT PRIMARY KEY," +
                "nome VARCHAR(255) NOT NULL," +
                "preco DOUBLE PRECISION NOT NULL)";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Tabela planos criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela planos: " + e.getMessage());
        }
    }

    public void createTableExercicios(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS exercicios (" +
                "numero INT PRIMARY KEY," +
                "nome VARCHAR(255) NOT NULL," +
                "musculos VARCHAR(255) NOT NULL)";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Tabela exercícios criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela exercícios: " + e.getMessage());
        }
    }

    public void createTableAlunoPlano(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS alunoplano (" +
                "cpf_aluno VARCHAR(15)," +
                "codigo_plano INT," +
                "data_inicio VARCHAR(11)," +
                "dados_cartao TEXT," +
                "PRIMARY KEY (cpf_aluno, codigo_plano)," +
                "FOREIGN KEY (cpf_aluno) REFERENCES alunos(cpf) ON DELETE RESTRICT," +
                "FOREIGN KEY (codigo_plano) REFERENCES planos(codigo) ON DELETE RESTRICT" +
                ")";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Tabela alunoplano criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela exercícios: " + e.getMessage());
        }
    }

    public void createTableTreinos(Connection conn)
    {
        String sql = "CREATE TABLE IF NOT EXISTS Treinos (" +
                "id SERIAL PRIMARY KEY," +
                "nome VARCHAR(255)," +
                "cpf_aluno VARCHAR(15) REFERENCES Alunos(cpf) ON DELETE RESTRICT," +
                "ativo BOOLEAN DEFAULT FALSE" +
                ")";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Tabela Treinos criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela Treinos: " + e.getMessage());
        }
    }

    public void createTableTreinosExercicios(Connection conn)
    {
        String sql = "CREATE TABLE IF NOT EXISTS TreinosExercicios (" +
                "id SERIAL PRIMARY KEY," +
                "id_treino INTEGER REFERENCES Treinos(id)," +
                "id_exercicio INTEGER REFERENCES exercicios(numero) ON DELETE RESTRICT," +
                "series INT," +
                "repeticoes_min INT," +
                "repeticoes_max INT," +
                "carga INT," +
                "descanso NUMERIC," +
                "ativo BOOLEAN DEFAULT TRUE" +
                ")";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Tabela TreinoExercicios criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela TreinoExercicios: " + e.getMessage());
        }
    }

    public void createTableProgressaoCarga(Connection conn)
    {
        String sql = "CREATE TABLE IF NOT EXISTS ProgressaoCarga (" +
                "id SERIAL PRIMARY KEY," +
                "id_treinosexercicio INTEGER REFERENCES TreinosExercicios(id)," +
                "carga INTEGER," +
                "date_atual TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Tabela ProgressaoCarga criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela ProgressaoCarga: " + e.getMessage());
        }
    }

    public void createTablePresencas(Connection conn)
    {
        String sql = "CREATE TABLE IF NOT EXISTS presencas (" +
                "id SERIAL PRIMARY KEY," +
                "cpf_aluno VARCHAR(15) REFERENCES alunos(cpf),"  +
                "date_atual TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Tabela Presencas criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela Presencas: " + e.getMessage());
        }
    }

}
