import java.util.Map;
import java.util.*;

public class Designacoes {

    public static void cadastraAlunoEmDisciplina(Scanner scanner, Map<Integer, Estudante> alunos, List<Disciplina> disciplinas){
        System.out.println("Digite a matricula do aluno: ");
        int matricula = scanner.nextInt();

        System.out.println("Digite o código da disciplina: ");
        scanner.nextLine();
        String codigo = scanner.nextLine();

        for (Disciplina i : disciplinas){
            if(i.codigo.equals(codigo)) {
                i.cadastraEstudante(alunos.get(matricula));
            }
        }
    }



}
