import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Habitante {
    double salario;
    int numFilhos;


    Habitante(double salario, int numFilhos) {
        this.salario = salario;
        this.numFilhos = numFilhos;
    }

    public double getSalario() {
        return salario;
    }

    public int getNumFilhos() {
        return numFilhos;
    }

}


public class ex1 {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        List<Habitante> habitantes = new ArrayList<>();

        while (true) {
            System.out.println("Digite seu salário(negativo para sair): ");
            double salario = read.nextDouble();
            if (salario < 0) {
                break;
            }
            System.out.println("Digite o número de filhos: ");
            int filhos = read.nextInt();
            habitantes.add(new Habitante(salario, filhos));
        }

        double media_salario = habitantes.stream()
                .mapToDouble(Habitante::getSalario)
                .average().orElse(0);

        double media_filhos = habitantes.stream()
                .mapToDouble(Habitante::getNumFilhos)
                .average().orElse(0);

        double salario_max = habitantes.stream()
                .mapToDouble(Habitante::getSalario)
                .max().orElse(0);

        long salario_max_1000 = habitantes.stream()
                .mapToDouble(Habitante::getSalario)
                .filter(salario -> salario <= 1000)
                .count();

        double percentual_1000 = (double) salario_max_1000 / habitantes.size() * 100;


        System.out.println("Média de salário da população: R$ " + media_salario);
        System.out.println("Média de número de filhos: " + media_filhos);
        System.out.println("Maior salário: R$ " + salario_max);
        System.out.println("Percentual de pessoas com salário de até R$ 1000,00: " + percentual_1000 + "%");
    }
}
