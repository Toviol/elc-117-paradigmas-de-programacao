package Exemplos;

import Entidades.Exercicio;
import ClassesDAO.ExercicioDAO;
import java.sql.Timestamp;
import java.sql.SQLException;

public class ExercicioExemplo {
    public static void main(String[] args) {
        ExercicioDAO exerciciodao = new ExercicioDAO();
        try {
            // Exemplo de inserção de dados
            Exercicio novoExercicio = new Exercicio();
            novoExercicio.setNumero(12);
            novoExercicio.setNome("G1tHuem6it");
            novoExercicio.setMusculos("xfhMdj2nzc");
            exerciciodao.inserirExercicio(novoExercicio);

            // Exemplo de busca de dados
            Exercicio exercicio = exerciciodao.procurarExercicio(12);
            if (exercicio != null) {
                System.out.println("Dados encontrados:");
                System.out.println("numero: " + exercicio.getNumero());
                System.out.println("nome: " + exercicio.getNome());
                System.out.println("musculos: " + exercicio.getMusculos());
            } else {
                System.out.println("Nenhum dado encontrado.");
            }

            // Exemplo de atualização de dados
            exercicio.setNumero(12);
            exercicio.setNome("oFvfvZKou6");
            exercicio.setMusculos("pqcD7Iqdw8");
            exerciciodao.updateExercicio(exercicio);

            // Exemplo de deleção de dados
            exerciciodao.deleteExercicio(12);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
