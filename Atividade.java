import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Atividade {
    String nome;
    int numero;
    List<Nota> notas;

    public Atividade(int numero, String nome){
        this.nome = nome;
        this.numero = numero;
        this.notas = new ArrayList<>();
    }

    public void designaNotaAluno(Scanner scanner, Map<Integer, Estudante> alunos){

        /* Inicializa o DecimalFormat para arredondar para 1 casa decimal */
        DecimalFormat df = new DecimalFormat("##.#");
        df.setRoundingMode(RoundingMode.UP);

        System.out.println("Digite a matricula do aluno: ");
        int matricula = scanner.nextInt();

        System.out.println("Digite a nota do aluno: ");
        double nota = scanner.nextDouble();
        nota = Double.parseDouble(df.format(nota));

        Estudante estudante = alunos.get(matricula);

        Nota pontuacao = new Nota(estudante, this, nota);
        notas.add(pontuacao);
    }

}
