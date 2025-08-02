import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.sql.Timestamp;
import conection.ConexaoDataBase;

public class Main {

    private static final String src = "src/";
    private static final String diretorio_entidade = src + "Entidades";
    private static final String diretorio_dao = src + "ClassesDAO";
    private static final String diretorio_exemplo = src + "Exemplos";
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        try (Connection connection = ConexaoDataBase.getConnection()) {

            new File(diretorio_entidade).mkdirs();
            new File(diretorio_dao).mkdirs();
            new File(diretorio_exemplo).mkdirs();

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String nomeTabela = tables.getString("TABLE_NAME");
                GeraClasseEntidade(metaData, nomeTabela);
                GeraClasseDao(metaData, nomeTabela);
                ResultSet columns = metaData.getColumns(null, null, nomeTabela, "%");
                GeraClasseExemplo(nomeTabela, columns);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void GeraClasseEntidade(DatabaseMetaData metaData, String nomeTabela) throws SQLException, IOException {
        String nomeClasse = deixaMaiusculoPrimeiraLetra(nomeTabela);
        nomeClasse = removeSFinal(nomeClasse); // coloca no singular
        List<String> definicoes = new ArrayList<>();
        List<String> sets = new ArrayList<>();
        List<String> gets = new ArrayList<>();

        try (ResultSet colunas = metaData.getColumns(null, null, nomeTabela, null)) {
            while (colunas.next()) {
                String nomeColuna = colunas.getString("COLUMN_NAME");
                String tipoColuna = colunas.getString("TYPE_NAME");
                String tipoJava = transfereSQLpraJava(tipoColuna);

                definicoes.add(String.format("    private %s %s;", tipoJava, nomeColuna));
                sets.add(String.format("    public void set%s(%s %s) {\n        this.%s = %s;\n    }\n", deixaMaiusculoPrimeiraLetra(nomeColuna), tipoJava, nomeColuna, nomeColuna, nomeColuna));
                gets.add(String.format("    public %s get%s() {\n        return %s;\n    }\n", tipoJava, deixaMaiusculoPrimeiraLetra(nomeColuna), nomeColuna));
            }
        }

        try (FileWriter escritor = new FileWriter(diretorio_entidade + "/" + nomeClasse + ".java")) {
            escritor.write("package Entidades;\n\n");
            escritor.write("import java.sql.Timestamp;\n\n");
            escritor.write("public class " + nomeClasse + " {\n");
            for (String definicao : definicoes) {
                escritor.write(definicao + "\n");
            }
            escritor.write("\n");
            for (String set : sets) {
                escritor.write(set + "\n");
            }
            for (String get : gets) {
                escritor.write(get);
            }
            escritor.write("}\n");
        }
    }

    private static void GeraClasseDao(DatabaseMetaData metaData, String nomeTabela) throws SQLException, IOException {
        String nomeClasse = deixaMaiusculoPrimeiraLetra(nomeTabela);
        nomeClasse = removeSFinal(nomeClasse); // coloca no singular
        String nomeClasseSemDao = nomeClasse;
        String nomeEntidade = removeSFinal(nomeClasse);
        nomeClasse = nomeClasse + "DAO"; // coloca dao

        List<String> colunas = new ArrayList<>();
        List<String> tipos = new ArrayList<>();

        try (ResultSet resultSet = metaData.getColumns(null, null, nomeTabela, null)) {
            while (resultSet.next()) {
                colunas.add(resultSet.getString("COLUMN_NAME"));
                tipos.add(transfereSQLpraJava(resultSet.getString("TYPE_NAME")));
            }
        }

        try (FileWriter escritor = new FileWriter(diretorio_dao + "/" + nomeClasse + ".java")) {
            escritor.write("package ClassesDAO;\n\n");
            escritor.write("import Entidades." + nomeClasseSemDao + ";\n");
            escritor.write("import conection.ConexaoDataBase;\n");
            escritor.write("import java.sql.Connection;\n");
            escritor.write("import java.sql.PreparedStatement;\n");
            escritor.write("import java.sql.ResultSet;\n");
            escritor.write("import java.sql.SQLException;\n");
            escritor.write("import java.sql.Statement;\n");
            escritor.write("import java.util.ArrayList;\n");
            escritor.write("import java.util.List;\n");
            escritor.write("import java.sql.Timestamp;\n\n");
            escritor.write("public class " + nomeClasse + " {\n");

            // metodo inserir
            escritor.write("    public void inserir" + nomeEntidade +"(" + nomeEntidade + " " + nomeEntidade.toLowerCase() + ") throws SQLException {\n");
            escritor.write("        String sql = \"INSERT INTO " + nomeTabela + " (");
            for (int i = 0; i < colunas.size(); i++) {
                escritor.write(colunas.get(i));
                if (i < colunas.size() - 1) {
                    escritor.write(", ");
                }
            }
            escritor.write(") VALUES (");
            for (int i = 0; i < colunas.size(); i++) {
                escritor.write("?");
                if (i < colunas.size() - 1) {
                    escritor.write(", ");
                }
            }
            escritor.write(")\";\n");
            escritor.write("        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {\n");
            for (int i = 0; i < colunas.size(); i++) {
                escritor.write("            stmt.set" + deixaMaiusculoPrimeiraLetra(tipos.get(i)) + "(" + (i + 1) + ", " + nomeEntidade.toLowerCase() + ".get" + deixaMaiusculoPrimeiraLetra(colunas.get(i)) + "());\n");
            }
            escritor.write("            stmt.executeUpdate();\n");
            escritor.write("        }\n");
            escritor.write("    }\n");

            // metodo procurar
            escritor.write("    public " + nomeEntidade + " procurar" + nomeEntidade + "(" + tipos.get(0) + " chave) throws SQLException {\n");
            escritor.write("        String sql = \"SELECT * FROM " + nomeTabela + " WHERE " + colunas.get(0) + " = ?\";\n");
            escritor.write("        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {\n");
            escritor.write("            stmt.set" + deixaMaiusculoPrimeiraLetra(tipos.get(0)) + "(1, chave);\n");
            escritor.write("            try (ResultSet rs = stmt.executeQuery()) {\n");
            escritor.write("                if (rs.next()) {\n");
            escritor.write("                    " + nomeEntidade + " " + nomeEntidade.toLowerCase() + " = new " + nomeEntidade + "();\n");
            for (int i = 0; i < colunas.size(); i++) {
                escritor.write("                    " + nomeEntidade.toLowerCase() + ".set" + deixaMaiusculoPrimeiraLetra(colunas.get(i)) + "(rs.get" + deixaMaiusculoPrimeiraLetra(tipos.get(i)) + "(\"" + colunas.get(i) + "\"));\n");
            }
            escritor.write("                    return " + nomeEntidade.toLowerCase() + ";\n");
            escritor.write("                }\n");
            escritor.write("            }\n");
            escritor.write("        }\n");
            escritor.write("        return null;\n");
            escritor.write("    }\n");

            // metodo retornatodos
            escritor.write("    public List<" + nomeEntidade + "> retornaTodos" + nomeEntidade + "s() throws SQLException {\n");
            escritor.write("        String sql = \"SELECT * FROM " + nomeTabela + "\";\n");
            escritor.write("        List<" + nomeEntidade + "> " + nomeEntidade.toLowerCase() + "s = new ArrayList<>();\n");
            escritor.write("        try (Connection conn = ConexaoDataBase.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {\n");
            escritor.write("            while (rs.next()) {\n");
            escritor.write("                " + nomeEntidade + " " + nomeEntidade.toLowerCase() + " = new " + nomeEntidade + "();\n");
            for (int i = 0; i < colunas.size(); i++) {
                escritor.write("                " + nomeEntidade.toLowerCase() + ".set" + deixaMaiusculoPrimeiraLetra(colunas.get(i)) + "(rs.get" + deixaMaiusculoPrimeiraLetra(tipos.get(i)) + "(\"" + colunas.get(i) + "\"));\n");
            }
            escritor.write("                " + nomeEntidade.toLowerCase() + "s.add(" + nomeEntidade.toLowerCase() + ");\n");
            escritor.write("            }\n");
            escritor.write("        }\n");
            escritor.write("        return " + nomeEntidade.toLowerCase() + "s;\n");
            escritor.write("    }\n");

            // metodo update
            escritor.write("    public void update" + nomeEntidade + "(" + nomeEntidade + " " + nomeEntidade.toLowerCase() + ") throws SQLException {\n");
            escritor.write("        String sql = \"UPDATE " + nomeTabela + " SET ");
            for (int i = 1; i < colunas.size(); i++) {
                escritor.write(colunas.get(i) + " = ?");
                if (i < colunas.size() - 1) {
                    escritor.write(", ");
                }
            }
            escritor.write(" WHERE " + colunas.get(0) + " = ?\";\n");
            escritor.write("        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {\n");
            for (int i = 1; i < colunas.size(); i++) {
                escritor.write("            stmt.set" + deixaMaiusculoPrimeiraLetra(tipos.get(i)) + "(" + i + ", " + nomeEntidade.toLowerCase() + ".get" + deixaMaiusculoPrimeiraLetra(colunas.get(i)) + "());\n");
            }
            escritor.write("            stmt.set" + deixaMaiusculoPrimeiraLetra(tipos.get(0)) + "(" + colunas.size() + ", " + nomeEntidade.toLowerCase() + ".get" + deixaMaiusculoPrimeiraLetra(colunas.get(0)) + "());\n");
            escritor.write("            stmt.executeUpdate();\n");
            escritor.write("        }\n");
            escritor.write("    }\n");

            // metodo delete
            escritor.write("    public void delete" + nomeEntidade + "(" + tipos.get(0) + " chave) throws SQLException {\n");
            escritor.write("        String sql = \"DELETE FROM " + nomeTabela + " WHERE " + colunas.get(0) + " = ?\";\n");
            escritor.write("        try (Connection conn = ConexaoDataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {\n");
            escritor.write("            stmt.set" + deixaMaiusculoPrimeiraLetra(tipos.get(0)) + "(1, chave);\n");
            escritor.write("            stmt.executeUpdate();\n");
            escritor.write("        }\n");
            escritor.write("    }\n");

            escritor.write("}\n");
        }
    }

    private static void GeraClasseExemplo(String nomeTabela, ResultSet columns) throws SQLException, IOException {
        String nomeClasse = deixaMaiusculoPrimeiraLetra(nomeTabela);
        nomeClasse = removeSFinal(nomeClasse);
        String nomeDAO = nomeClasse + "DAO";
        String valorAleatorioChave = "0";
        int primeiro = 1;

        String nomeArquivo = diretorio_exemplo + "/" + nomeClasse + "Exemplo.java";
        try (FileWriter escritor = new FileWriter(nomeArquivo)) {
            escritor.write("package Exemplos;\n\n");
            escritor.write("import Entidades." + nomeClasse + ";\n");
            escritor.write("import ClassesDAO." + nomeDAO + ";\n");
            escritor.write("import java.sql.Timestamp;\n");
            escritor.write("import java.sql.SQLException;\n\n");
            escritor.write("public class " + nomeClasse + "Exemplo {\n");
            escritor.write("    public static void main(String[] args) {\n");
            escritor.write("        " + nomeClasse + "DAO " + nomeDAO.toLowerCase() + " = new " + nomeClasse + "DAO();\n");
            escritor.write("        try {\n");
            escritor.write("            // Exemplo de inserção de dados\n");
            escritor.write("            " + nomeClasse + " novo" + nomeClasse + " = new " + nomeClasse + "();\n");

            while (columns.next()) {
                String nomeColuna = columns.getString("COLUMN_NAME");
                String tipoColuna = columns.getString("TYPE_NAME");
                String valorAleatorio = getValorAleatorio(tipoColuna);
                if(primeiro == 1)
                    valorAleatorioChave = valorAleatorio;
                primeiro = 0;

                escritor.write("            novo" + nomeClasse + ".set" + deixaMaiusculoPrimeiraLetra(nomeColuna) + "(" + valorAleatorio + ");\n");
            }

            escritor.write("            " + nomeDAO.toLowerCase() + ".inserir" + nomeClasse + "(novo" + nomeClasse + ");\n");

            escritor.write("\n            // Exemplo de busca de dados\n");
            escritor.write("            " + nomeClasse + " " + nomeClasse.toLowerCase() + " = " + nomeDAO.toLowerCase() + ".procurar" + nomeClasse + "(" + valorAleatorioChave + ");\n");
            escritor.write("            if (" + nomeClasse.toLowerCase() + " != null) {\n");
            escritor.write("                System.out.println(\"Dados encontrados:\");\n");

            columns.beforeFirst();
            while (columns.next()) {
                String nomeColuna = columns.getString("COLUMN_NAME");
                escritor.write("                System.out.println(\"" + nomeColuna + ": \" + " + nomeClasse.toLowerCase() + ".get" + deixaMaiusculoPrimeiraLetra(nomeColuna) + "());\n");
            }

            escritor.write("            } else {\n");
            escritor.write("                System.out.println(\"Nenhum dado encontrado.\");\n");
            escritor.write("            }\n");

            escritor.write("\n            // Exemplo de atualização de dados\n");
            columns.beforeFirst();
            primeiro = 1;
            while (columns.next()) {
                String nomeColuna = columns.getString("COLUMN_NAME");
                String tipoColuna = columns.getString("TYPE_NAME");
                String valorAleatorio = getValorAleatorio(tipoColuna);
                if(primeiro == 1)
                    escritor.write("            " + nomeClasse.toLowerCase() + ".set" + deixaMaiusculoPrimeiraLetra(nomeColuna) + "(" + valorAleatorioChave + ");\n");
                else
                    escritor.write("            " + nomeClasse.toLowerCase() + ".set" + deixaMaiusculoPrimeiraLetra(nomeColuna) + "(" + valorAleatorio + ");\n");
                primeiro = 0;
            }

            escritor.write("            " + nomeDAO.toLowerCase() + ".update" + nomeClasse + "(" + nomeClasse.toLowerCase() + ");\n");

            escritor.write("\n            // Exemplo de deleção de dados\n");
            escritor.write("            " + nomeDAO.toLowerCase() + ".delete" + nomeClasse + "(" + valorAleatorioChave + ");\n");

            escritor.write("        } catch (SQLException e) {\n");
            escritor.write("            e.printStackTrace();\n");
            escritor.write("        }\n");
            escritor.write("    }\n");
            escritor.write("}\n");
        }
    }

    private static String getValorAleatorio(String tipoColuna) {
        switch (tipoColuna.toUpperCase()) {
            case "VARCHAR":
            case "CHAR":
            case "TEXT":
                return "\"" + geraStringAleatoria(10) + "\"";
            case "INT":
            case "INTEGER":
            case "INT4":
            case "SERIAL":
                return String.valueOf(RANDOM.nextInt(100));
            case "DOUBLE":
            case "FLOAT8":
            case "NUMERIC":
            case "DECIMAL":
                return String.valueOf(RANDOM.nextDouble() * 100);
            case "DATE":
                return "new java.sql.Date(System.currentTimeMillis())";
            case "BOOLEAN":
            case "BOOL":
                return String.valueOf(RANDOM.nextBoolean());
            case "TIMESTAMP":
                return "Timestamp";
            default:
                return "\"\"";
        }
    }

    private static String geraStringAleatoria(int tamanho) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            sb.append(caracteres.charAt(RANDOM.nextInt(caracteres.length())));
        }
        return sb.toString();
    }

    private static String deixaMaiusculoPrimeiraLetra(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static String transfereSQLpraJava(String tipoSQL) {
        switch (tipoSQL.toUpperCase()) {
            case "VARCHAR":
            case "CHAR":
            case "TEXT":
                return "String";
            case "INT":
            case "INTEGER":
            case "INT4":
            case "SERIAL":
                return "int";
            case "DOUBLE":
            case "FLOAT8":
            case "NUMERIC":
            case "DECIMAL":
                return "double";
            case "DATE":
                return "java.sql.Date";
            case "BOOLEAN":
            case "BOOL":
                return "boolean";
            case "TIMESTAMP":
                return "Timestamp";
            default:
                throw new IllegalArgumentException("Tipo SQL não suportado: " + tipoSQL);
        }
    }

    private static String removeSFinal(String str) {
        if (str.endsWith("s")) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }
}
