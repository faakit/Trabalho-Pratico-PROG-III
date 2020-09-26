import java.util.Map;
import java.util.regex.Pattern;
import java.util.*;



public class Designacoes {

    private static final String DIGITAPERIODO = "Digite o periodo [Ano/Semestre]: ";
    private static final String DIGITACODIGO = "Digite o código da disciplina: ";

    public static void cadastraAlunoEmDisciplina(Scanner scanner, Map<Integer, Estudante> alunos, List<Disciplina> disciplinas){
        System.out.println("Digite a matricula do aluno: ");
        int matricula = scanner.nextInt();

        System.out.println(DIGITACODIGO);
        scanner.nextLine();
        String codigo = scanner.nextLine();

        System.out.println(DIGITAPERIODO);
        String periodo = scanner.nextLine();

        String[] split = periodo.split(Pattern.quote("/"));
        int ano = Integer.parseInt(split[0]);
        String semestre = split[1];


        /*      Busca a disciplina com codigo e período e cadastra o aluno     */
        for (Disciplina i : disciplinas){
            if(i.codigo.equals(codigo) && i.periodo.ano == ano && i.periodo.semestre.equals(semestre)) {
                i.cadastraEstudante(alunos.get(matricula));
                break;
            }
        }
    }

    public static void cadastraAtividadeEmDisciplina(Scanner scanner, List<Disciplina> disciplinas){
        System.out.println(DIGITACODIGO);
        scanner.nextLine();
        String codigo = scanner.nextLine();

        System.out.println(DIGITAPERIODO);
        String periodo = scanner.nextLine();

        String[] split = periodo.split(Pattern.quote("/"));
        int ano = Integer.parseInt(split[0]);
        String semestre = split[1];

        /*      Busca a disciplina com codigo e período e cadastra atividade     */
        for(Disciplina i : disciplinas){
            if(i.codigo.equals(codigo) && i.periodo.ano == ano && i.periodo.semestre.equals(semestre)){
                i.novaAtividade(scanner);
                break;
            }
        }
    }

    public static void cadastraNotaEmAtividade(Scanner scanner, List<Disciplina> disciplinas, Map<Integer, Estudante> alunos){

        System.out.println(DIGITACODIGO);
        scanner.nextLine();
        String codigo = scanner.nextLine();

        System.out.println(DIGITAPERIODO);
        String periodo = scanner.nextLine();

        String[] split = periodo.split(Pattern.quote("/"));
        int ano = Integer.parseInt(split[0]);
        String semestre = split[1];

        System.out.println("Digite o índice da atividade: ");
        int numAtividade = scanner.nextInt();

        /*      Busca a disciplina com codigo e período e chama função de designação de nota     */
        for(Disciplina i : disciplinas){
            if(i.codigo.equals(codigo) && i.periodo.ano == ano && i.periodo.semestre.equals(semestre)){

                i.atividades.get(numAtividade).designaNotaAluno(scanner, alunos);

            }
        }
    }
}
