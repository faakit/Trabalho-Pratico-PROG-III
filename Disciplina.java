import java.util.*;
import java.util.regex.Pattern;

public class Disciplina {
    String codigo;
    String nome;
    Periodo periodo;
    Docente professor;
    List<Estudante> alunos;
    Map<Integer, Atividade> atividades;

    public Disciplina cadastraDisciplina(Scanner scanner, List<Periodo> periodos, List<Docente> professores){
        String codigo;
        String nome;
        String periodo;
        String professor;
        int ano;
        String semestre;
        this.atividades = new HashMap<>();
        this.alunos = new ArrayList<>();

        System.out.println("Digite o codigo da disciplina: ");
        scanner.nextLine();
        codigo = scanner.nextLine();
        
        System.out.println("Digite o nome da disciplina: ");
        nome = scanner.nextLine();

        /*      Organiza e procura pelo período     */
        System.out.println("Digite o periodo [Ano/Semestre]: ");
        periodo = scanner.nextLine();

        String[] split = periodo.split(Pattern.quote("/"));
        ano = Integer.parseInt(split[0]);
        semestre = split[1];

        /*      Percorre a lista de periodos procurando o especificado e referencia     */
        int achou = 0;
        for (Periodo i : periodos){
            if(ano == i.ano && semestre.equals(i.semestre)){
                achou = 1;
                this.periodo = i;
                break;
            }
        }
        if (achou == 0) System.out.println("Não foi possível encontrar o período especificado") ;


        /*      Fazer o mesmo com o docente responsável     */
        System.out.println("Digite o login institucional do docente responsável: ");
        professor = scanner.nextLine();

        achou = 0;
        for (Docente i : professores){
            if(professor.equals(i.login)){
                achou = 1;
                this.professor = i;
                break;
            }
        }
        if(achou == 0) System.out.println("Não foi possível encontrar o docente responsável espericifado");


        this.codigo = codigo;
        this.nome = nome;

        return this;
    }

    public void cadastraEstudante( Estudante estudante ){
        this.alunos.add(estudante);
    }

    public void novaAtividade(Scanner scanner){
        System.out.println("Digite o nome da atividade: ");
        scanner.nextLine();
        String nome = scanner.nextLine();

        Atividade atividade = new Atividade(this.atividades.size() + 1, nome);
        this.atividades.put(this.atividades.size() + 1, atividade);
    }
}