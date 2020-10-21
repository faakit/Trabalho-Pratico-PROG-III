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
    
    public static void verificaPeriodos(Designacoes designacoes){

        System.out.println("Períodos cadastrados: ");
        for(Map.Entry<String, Periodo> i : designacoes.periodos.entrySet()){

            System.out.println(i.getValue());

        }
        
        pressionaEnter();

    }

    public static void verificaDocentes(Map<String , Docente> docentes){

        System.out.println("Docentes cadastrados: ");
        for(Map.Entry<String, Docente> i : docentes.entrySet()){
            System.out.println("Nome: " + i.getValue().getNome());
            System.out.println("Login: " + i.getValue().getLogin());
            System.out.println("Website: " + i.getValue().getWebsite());
            System.out.println();
        }

        pressionaEnter();
    }

    public static void verificaDisciplinas(Map<String, Disciplina> disciplinas){

        System.out.println(("Disciplinas cadastradas: "));
        for( Map.Entry<String, Disciplina> i : disciplinas.entrySet() ){
            System.out.println(i.getValue());
        }
        pressionaEnter();

    }

    public static void verificaEstudantes(Map<Integer, Estudante> estudantes){

        System.out.println("Estudantes cadastrados:");
        for(Map.Entry<Integer, Estudante> i : estudantes.entrySet()){
            Estudante aluno = i.getValue();
            System.out.println(aluno.getNome() + " :: " + aluno.getMatricula());
        }
        pressionaEnter();

    }

    public static void verificaAlunosEmDisciplina(Map<String, Disciplina> disciplinas, Scanner scanner){
        
        System.out.println("Digite o codigo da disciplina: ");
        scanner.nextLine();
        String codigo = scanner.nextLine().toUpperCase();

        System.out.println("Digite o periodo [Ano/Semestre]: ");
        String periodo = scanner.nextLine();

        String[] split = periodo.split(Pattern.quote("/"));
        int ano = Integer.parseInt(split[0]);
        String semestre = split[1].toUpperCase();

        for (Map.Entry<String, Disciplina> i : disciplinas.entrySet()){

            if(i.getValue().getCodigo().equals(codigo) && i.getValue().getPeriodo().getAno() == ano && i.getValue().getPeriodo().getSemestre().equals(semestre)) {

                System.out.println("Estudantes cadastrados em " + i.getValue().getCodigo() + "-" + i.getValue().getPeriodo().getAno() + "/" + i.getValue().getPeriodo().getSemestre());
                for(Map.Entry<Integer, Estudante> j : i.getValue().getAlunos().entrySet()){
                    System.out.println(j.getValue().getMatricula() + " :: " + j.getValue().getNome());
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

            if(i.getCodigo().equals(codigo) && i.getPeriodo().getAno() == ano && i.getPeriodo().getSemestre().equals(semestre)) {

                System.out.println("Atividades cadastradas em " + i.getCodigo() + "-" + i.getPeriodo().getAno() + "/" + i.getPeriodo().getSemestre() );
                for( Map.Entry<Integer, Atividade> j : i.getAtividades().entrySet() ){

                    Atividade atividade = j.getValue();
                    System.out.println(atividade.getNumero() + " :: " +atividade.getNome());

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

            if(i.getCodigo().equals(codigo) && i.getPeriodo().getAno() == ano && i.getPeriodo().getSemestre().equals(semestre)) {


                Atividade j = i.getAtividades().get(numero);
                
                for(Map.Entry<Integer, Nota> k : j.getNotas().entrySet()){
                    System.out.println("Aluno: " +  k.getValue().getEstudante().getNome() + " :: " + k.getValue().getEstudante().getMatricula() + " nota: " + k.getValue().getNotaDoAluno());
                }

                break;
            }
        }

        pressionaEnter();

    }
}
