import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Produto {
    int codigo;
    double novoPreco;

    public Produto(int codigo, double novoPreco) {
        this.codigo = codigo;
        this.novoPreco = novoPreco;
    }

    public int getCodigo() {
        return codigo;
    }

    public double getNovoPreco() {
        return novoPreco;
    }
}

public class ex5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Produto> produtos = new ArrayList<>();

        while (true) {
            System.out.print("Digite o código do produto (negativo para encerrar): ");
            int codigo = scanner.nextInt();
            if (codigo < 0) {
                break;
            }

            System.out.print("Digite o preço de custo do produto: ");
            double precoCusto = scanner.nextDouble();

            double novoPreco = precoCusto * 1.20;
            produtos.add(new Produto(codigo, novoPreco));
        }

        produtos.forEach(produto ->
                System.out.println("Código do produto: " + produto.getCodigo() + ", Novo preço: " + produto.getNovoPreco())
        );

        double mediaPrecos = produtos.stream()
                .collect(Collectors.averagingDouble(Produto::getNovoPreco));
        System.out.println("Média dos novos preços: " + mediaPrecos);
    }
}
