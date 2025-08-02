import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ex4 {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Digite 10 números para checar o intervalo:");
        List<Integer> numeros = IntStream.range(0, 10)
                .mapToObj(i -> entrada.nextInt())
                .collect(Collectors.toList());

        long intervalo0a25 = numeros.stream().filter(n -> n >= 0 && n <= 25).count();
        long intervalo26a50 = numeros.stream().filter(n -> n >= 26 && n <= 50).count();
        long intervalo51a75 = numeros.stream().filter(n -> n >= 51 && n <= 75).count();
        long intervalo76a100 = numeros.stream().filter(n -> n >= 76 && n <= 100).count();

        System.out.println("Quantidade de números no intervalo [0, 25]: " + intervalo0a25);
        System.out.println("Quantidade de números no intervalo [26, 50]: " + intervalo26a50);
        System.out.println("Quantidade de números no intervalo [51, 75]: " + intervalo51a75);
        System.out.println("Quantidade de números no intervalo [76, 100]: " + intervalo76a100);
    }
}