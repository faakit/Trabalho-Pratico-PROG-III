import java.util.*;
import java.util.regex.Pattern;

public class Testes {

    private static void pressionaEnter(){
        System.out.println("Pressione ENTER para retornar.");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}
    }
    
    public static void verificaPeriodos(List<Periodo> periodos){

        System.out.println("Períodos cadastrados: ");
        for(Periodo i : periodos){
            System.out.println(i.ano + "/" + i.semestre);
        }
        
        pressionaEnter();

    }

    public static void verificaDocentes(List<Docente> docentes){

        System.out.println("Docentes cadastrados: ");
        for(Docente i : docentes){
            System.out.println("Nome: " + i.nome);
            System.out.println("Login: " + i.login);
            System.out.println("Website: " + i.website);
            System.out.println();
        }

        pressionaEnter();
    }

    public static void verificaDisciplinas(List<Disciplina> disciplinas){

        System.out.println(("Disciplinas cadastradas: "));
        for(Disciplina i : disciplinas){
            System.out.println(i.codigo + "-" + i.periodo.ano + "/" + i.periodo.semestre);
        }
        pressionaEnter();

    }

    public static void verificaEstudantes(Map<Integer, Estudante> estudantes){

        System.out.println("Estudantes cadastrados:");
        for(Map.Entry<Integer, Estudante> i : estudantes.entrySet()){
            Estudante aluno = i.getValue();
            System.out.println(aluno.nome + " :: " + aluno.matricula);
        }
        pressionaEnter();

    }

    public static void verificaAlunosEmDisciplina(List<Disciplina> disciplinas, Scanner scanner){
        
        System.out.println("Digite o codigo da disciplina: ");
        scanner.nextLine();
        String codigo = scanner.nextLine().toUpperCase();

        System.out.println("Digite o periodo [Ano/Semestre]: ");
        String periodo = scanner.nextLine();

        String[] split = periodo.split(Pattern.quote("/"));
        int ano = Integer.parseInt(split[0]);
        String semestre = split[1].toUpperCase();

        for (Disciplina i : disciplinas){

            if(i.codigo.equals(codigo) && i.periodo.ano == ano && i.periodo.semestre.equals(semestre)) {

                System.out.println("Estudantes cadastrados em " + i.codigo + "-" + i.periodo.ano + "/" + i.periodo.semestre);
                for(Estudante j : i.alunos){
                    System.out.println(j.matricula + " :: " + j.nome);
                }

                break;
            }
        }

        pressionaEnter();

    }

    public static void obterAtividadesDaDisciplina(List<Disciplina> disciplinas, Scanner scanner){

        System.out.println("Digite o codigo da disciplina: ");
        scanner.nextLine();
        String codigo = scanner.nextLine().toUpperCase();

        System.out.println("Digite o periodo [Ano/Semestre]: ");
        String periodo = scanner.nextLine();

        String[] split = periodo.split(Pattern.quote("/"));
        int ano = Integer.parseInt(split[0]);
        String semestre = split[1].toUpperCase();

        for (Disciplina i : disciplinas){

            if(i.codigo.equals(codigo) && i.periodo.ano == ano && i.periodo.semestre.equals(semestre)) {

                System.out.println("Atividades cadastradas em " + i.codigo + "-" + i.periodo.ano + "/" + i.periodo.semestre );
                for( Map.Entry<Integer, Atividade> j : i.atividades.entrySet() ){

                    Atividade atividade = j.getValue();
                    System.out.println(atividade.numero + " :: " +atividade.nome);

                }

                break;
            }
        }

        pressionaEnter();
    }

    public static void obterNotasEmAtividade(List<Disciplina> disciplinas, Scanner scanner){

        System.out.println("Digite o codigo da disciplina: ");
        scanner.nextLine();
        String codigo = scanner.nextLine().toUpperCase();

        System.out.println("Digite o periodo [Ano/Semestre]: ");
        String periodo = scanner.nextLine();

        String[] split = periodo.split(Pattern.quote("/"));
        int ano = Integer.parseInt(split[0]);
        String semestre = split[1].toUpperCase();

        System.out.println("Digite o numero da atividade: ");
        int numero = scanner.nextInt();

        for (Disciplina i : disciplinas){

            if(i.codigo.equals(codigo) && i.periodo.ano == ano && i.periodo.semestre.equals(semestre)) {


                Atividade j = i.atividades.get(numero);
                
                for(Nota k : j.notas){
                    System.out.println("Aluno: " +  k.estudante.nome + " :: " + k.estudante.matricula + " nota: " + k.notaDoAluno);
                }

                break;
            }
        }

        pressionaEnter();

    }
}
