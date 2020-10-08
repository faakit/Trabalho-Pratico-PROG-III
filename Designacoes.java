import java.util.regex.Pattern;
import java.util.*;

public class Designacoes {
    
    private static final String DIGITAPERIODO = "Digite o periodo [Ano/Semestre]: ";
    private static final String DIGITACODIGO = "Digite o código da disciplina: ";
    private static final String DIGITAMATRICULA = "Digite a matricula: ";
    private static final String DIGITANOME = "Digite o nome completo: ";
    private static final String DIGITALOGIN = "Digite o login institucional: ";

    Map<String, Periodo> periodos;
    Map<String, Docente> docentes;
    Map<Integer, Estudante> estudantes;
    Map<String, Disciplina> disciplinas;

    Scanner scanner;

    /* Construtor da classe */
    public Designacoes(Scanner scanner){
        this.scanner = scanner;
        periodos = new HashMap<>();
        docentes = new HashMap<>();
        estudantes = new HashMap<>();
        disciplinas = new HashMap<>();
    }

    /* Funções de cadastro */
    public void cadastraEstudante(){
        int matricula;
        String nome;
        
        System.out.println(DIGITAMATRICULA);
        matricula = scanner.nextInt();

        System.out.println(DIGITANOME);
        scanner.nextLine();
        nome = scanner.nextLine();

        Estudante novoEstudante = new Estudante(matricula, nome);
        estudantes.put(matricula , novoEstudante);

    }

    public void cadastraPeriodo(){
        String entrada;

        System.out.println(DIGITAPERIODO);
        scanner.nextLine();
        entrada = scanner.nextLine();

        String[] split = entrada.split(Pattern.quote("/"));

        int ano = Integer.parseInt(split[0]);
        String semestre = split[1].toUpperCase();

        Periodo periodo = new Periodo(ano, semestre);
        periodos.put(periodo.toString(), periodo);

    }

    public void cadastraDocente(){
        String login;
        String nome;
        String website;
        char escolha;
        
        System.out.println(DIGITALOGIN);
        scanner.nextLine();
        login = scanner.nextLine();

        System.out.println(DIGITANOME);
        nome = scanner.nextLine();

        System.out.println("Possui website? [S/N] : ");
        escolha = scanner.next().charAt(0);

        if(escolha == 's' || escolha == 'S'){
            System.out.println("Digite o website: ");
            scanner.nextLine();
            website = scanner.nextLine();
        }else{
            website = "NA";
        }

        Docente novoDocente = new Docente(login, nome, website);
        docentes.put(login, novoDocente);

    }

    public void cadastraDisciplina(){
        String codigo;
        String nome;

        System.out.println(DIGITACODIGO);
        scanner.nextLine();
        codigo = scanner.nextLine().toUpperCase();
        
        System.out.println("Digite o nome da disciplina: ");
        nome = scanner.nextLine();

        System.out.println(DIGITAPERIODO);
        String strPeriodo = scanner.nextLine();

        Periodo periodo = periodos.get(strPeriodo);

        System.out.println("Digite o login institucional do docente responsável: ");
        String strProfessor = scanner.nextLine();

        Docente professor = docentes.get(strProfessor);

        Disciplina disciplina = new Disciplina(codigo, nome, periodo, professor);
        disciplinas.put(disciplina.toString(), disciplina);

    }

    public void cadastraAlunoEmDisciplina(){
        System.out.println(DIGITAMATRICULA);
        int matricula = scanner.nextInt();

        System.out.println(DIGITACODIGO);
        scanner.nextLine();
        String codigo = scanner.nextLine().toUpperCase();

        System.out.println(DIGITAPERIODO);
        String periodo = scanner.nextLine();

        disciplinas.get(codigo + "-" + periodo).alunos.put(matricula, estudantes.get(matricula));

    }

    public void cadastraAtividadeEmDisciplina(){
        System.out.println(DIGITACODIGO);
        scanner.nextLine();
        String codigo = scanner.nextLine().toUpperCase();

        System.out.println(DIGITAPERIODO);
        String periodo = scanner.nextLine();

        System.out.println("Digite o nome da atividade: ");
        String nome = scanner.nextLine();

        Disciplina disciplina = disciplinas.get(codigo + "-" + periodo);
        Atividade atividade = new Atividade(disciplina.atividades.size() + 1, nome);
        disciplina.atividades.put(disciplina.atividades.size() + 1, atividade);


    }



    /* 
    public void cadastraNotaEmAtividade(){

        System.out.println(DIGITACODIGO);
        scanner.nextLine();
        String codigo = scanner.nextLine().toUpperCase();

        System.out.println(DIGITAPERIODO);
        String periodo = scanner.nextLine();

        System.out.println("Digite o índice da atividade: ");
        int numAtividade = scanner.nextInt();

        disciplinas.get(codigo + "-" + periodo).atividades.get(numAtividade).designaNotaAluno(scanner, estudantes);
    }
    */


}
