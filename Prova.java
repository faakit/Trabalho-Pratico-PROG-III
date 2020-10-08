import java.util.HashMap;
import java.util.Map;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Prova extends Atividade {
    Map<Integer ,Nota> notas;
    LocalDateTime data;
    String conteudo;

    public Prova(int numero, String nome, LocalDateTime data, String conteudo){
        super(numero, nome);
        this.notas = new HashMap<>();
        this.data = data;
        this.conteudo = conteudo;
    }

    public void designaNotaAluno(Scanner scanner, Map<Integer, Estudante> alunos){

        /* Inicializa o DecimalFormat para arredondar para 1 casa decimal */
        DecimalFormat df = new DecimalFormat("##.#");
        df.setRoundingMode(RoundingMode.UP);

        System.out.println("Digite a matricula do aluno: ");
        int matricula = scanner.nextInt();

        System.out.println("Digite a nota do aluno: ");
        double nota = scanner.nextDouble();

        Estudante estudante = alunos.get(matricula);

        Nota pontuacao = new Nota(estudante, this, nota);
        notas.put(matricula ,pontuacao);
    }


}
