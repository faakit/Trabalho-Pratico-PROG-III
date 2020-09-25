import java.util.Scanner;
import java.util.regex.Pattern;

public class Periodo{
    int ano;
    String semestre;

    public Periodo cadastraPeriodo(Scanner scanner){
        String entrada;

        System.out.println("Digite o periodo [Ano/Semestre]: ");
        scanner.nextLine();
        entrada = scanner.nextLine();

        String[] split = entrada.split(Pattern.quote("/"));

        this.ano = Integer.parseInt(split[0]);
        this.semestre = split[1];

        return this;
    }
}