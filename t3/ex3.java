import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Aluno {
    int codigo;
    double nota1;
    double nota2;
    double nota3;

    public Aluno(int codigo, double nota1, double nota2, double nota3) {
        this.codigo = codigo;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
    }

    public int getCodigo() {
        return codigo;
    }

    public List<Double> getNotas() {
        List<Double> notas = new ArrayList<>();
        notas.add(nota1);
        notas.add(nota2);
        notas.add(nota3);
        return notas;
    }
}


public class ex3 {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);

        List<Aluno> alunos = new ArrayList<>();

        int i;
        for (i = 0; i < 5; i++) {
            System.out.println("Digite o código do aluno:");
            int codigo = read.nextInt();
            System.out.println("Digite a nota1:");
            double nota1 = read.nextDouble();
            System.out.println("Digite a nota2:");
            double nota2 = read.nextDouble();
            System.out.println("Digite a nota3:");
            double nota3 = read.nextDouble();

            alunos.add(new Aluno(codigo, nota1, nota2, nota3));
        }

        alunos.forEach(aluno -> {
            List<Double> notas = aluno.getNotas();
            List<Double> copia = new ArrayList<>(notas);

            double maiorNota = copia.stream().max(Comparator.naturalOrder()).orElse(0.0);
            copia.remove(maiorNota);

            double mediaPonderada = (maiorNota * 4 + copia.stream().mapToDouble(n -> n * 3).sum()) / 10;

            String resultado = (mediaPonderada >= 5) ? "APROVADO" : "REPROVADO";

            System.out.println("Código do aluno: " + aluno.getCodigo());
            System.out.println("Notas: " + notas);
            System.out.println("Média ponderada: " + mediaPonderada);
            System.out.println("Resultado: " + resultado);
            System.out.println();
        });
    }

}
