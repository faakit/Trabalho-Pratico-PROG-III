import java.util.*;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        List<Periodo> periodos = new ArrayList<>();
        List<Docente> docentes = new ArrayList<>();
        List<Estudante> estudantes = new ArrayList<>();
        List<Disciplina> disciplinas = new ArrayList<>();

        do{
            System.out.println("Escolha uma opção");
            System.out.println("-------------------------\n");
            System.out.println("1 - Cadastrar periodo");
            System.out.println("2 - Cadastrar docente");
            System.out.println("3 - Cadastrar disciplina");
            System.out.println("4 - Cadastrar estudante");
            System.out.println("5 - Sair");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    Periodo novoPeriodo = new Periodo();
                    periodos.add(novoPeriodo.cadastraPeriodo(scanner));
                    break;
                case 2:
                    Docente novoDocente = new Docente();
                    docentes.add(novoDocente.cadastraDocente(scanner));
                    break;
                case 3:
                    Disciplina novaDisciplina = new Disciplina();
                    disciplinas.add(novaDisciplina.cadastraDisciplina(scanner, periodos, docentes));
                    break;
                case 4:
                    Estudante novoEstudante = new Estudante();
                    estudantes.add(novoEstudante.cadastraEstudante(scanner));
                    break;
                case 5:
                    break;
                default:
                    
            }
        }while(escolha !=5 );

        scanner.close();
    }
}
