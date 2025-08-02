package Exemplos;

import Entidades.Plano;
import ClassesDAO.PlanoDAO;
import java.sql.Timestamp;
import java.sql.SQLException;

public class PlanoExemplo {
    public static void main(String[] args) {
        PlanoDAO planodao = new PlanoDAO();
        try {
            // Exemplo de inserção de dados
            Plano novoPlano = new Plano();
            novoPlano.setCodigo(61);
            novoPlano.setNome("bNbc2Hh4Bl");
            novoPlano.setPreco(28.13333110341417);
            planodao.inserirPlano(novoPlano);

            // Exemplo de busca de dados
            Plano plano = planodao.procurarPlano(61);
            if (plano != null) {
                System.out.println("Dados encontrados:");
                System.out.println("codigo: " + plano.getCodigo());
                System.out.println("nome: " + plano.getNome());
                System.out.println("preco: " + plano.getPreco());
            } else {
                System.out.println("Nenhum dado encontrado.");
            }

            // Exemplo de atualização de dados
            plano.setCodigo(61);
            plano.setNome("I2qNcl9VnF");
            plano.setPreco(65.98823379161117);
            planodao.updatePlano(plano);

            // Exemplo de deleção de dados
            planodao.deletePlano(61);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
