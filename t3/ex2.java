import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ex2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> votos = new ArrayList<>();

        System.out.println("Digite os votos (1-4 para candidatos, 5 para nulo, 6 para branco, 0 para finalizar):");

        while (true) {
            int voto = scanner.nextInt();
            if (voto == 0) break;
            if (voto >= 1 && voto <= 6) {
                votos.add(voto);
            } else {
                System.out.println("Código de voto inválido. Digite novamente.");
            }
        }

        long totalCandidato1 = votos.stream().filter(v -> v == 1).count();
        long totalCandidato2 = votos.stream().filter(v -> v == 2).count();
        long totalCandidato3 = votos.stream().filter(v -> v == 3).count();
        long totalCandidato4 = votos.stream().filter(v -> v == 4).count();
        long totalNulos = votos.stream().filter(v -> v == 5).count();
        long totalBrancos = votos.stream().filter(v -> v == 6).count();

        System.out.println("Total de votos para o candidato 1: " + totalCandidato1);
        System.out.println("Total de votos para o candidato 2: " + totalCandidato2);
        System.out.println("Total de votos para o candidato 3: " + totalCandidato3);
        System.out.println("Total de votos para o candidato 4: " + totalCandidato4);
        System.out.println("Total de votos nulos: " + totalNulos);
        System.out.println("Total de votos em branco: " + totalBrancos);
    }
}
