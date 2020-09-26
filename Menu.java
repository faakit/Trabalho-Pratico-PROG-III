import java.util.*;

public class Menu {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("pt", "BR"));

        Scanner scanner = new Scanner(System.in);
        int escolha;

        List<Periodo> periodos = new ArrayList<>();
        List<Docente> docentes = new ArrayList<>();
        Map<Integer, Estudante> estudantes = new HashMap<>();
        List<Disciplina> disciplinas = new ArrayList<>(); 

        do{

            /*     
            Para listar qualquer um dos cadastros (e verificar) basta repetir o número      
            Exemplo: "11" para verificar os períodos cadastrados , "22" verificar docentes       
            */
            System.out.println("Escolha uma opção");
            System.out.println("-------------------------\n");
            System.out.println("1 - Cadastrar periodo");
            System.out.println("2 - Cadastrar docente");
            System.out.println("3 - Cadastrar disciplina");
            System.out.println("4 - Cadastrar estudante");
            System.out.println("5 - Matricular estudante em disciplina");
            System.out.println("6 - Cadastrar atividade de disciplina");
            System.out.println("7 - Cadastrar nota de aluno em atividade");
            System.out.println("8 - Sair");

            
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
                    novoEstudante.cadastraEstudante(scanner);
                    estudantes.put(novoEstudante.matricula , novoEstudante.retornaEstudante());
                    break;
                case 5:
                    Designacoes.cadastraAlunoEmDisciplina(scanner, estudantes, disciplinas);
                    break;
                case 6:
                    Designacoes.cadastraAtividadeEmDisciplina(scanner, disciplinas);
                    break;
                case 7:
                    Designacoes.cadastraNotaEmAtividade(scanner, disciplinas, estudantes);
                    break;
                case 8:
                    scanner.close();
                    break;
                case 11:
                    Testes.verificaPeriodos(periodos);
                    break;
                case 22:
                    Testes.verificaDocentes(docentes);
                    break;
                case 33:
                    Testes.verificaDisciplinas(disciplinas);
                    break;
                case 44:
                    Testes.verificaEstudantes(estudantes);
                    break;
                case 55:
                    Testes.verificaAlunosEmDisciplina(disciplinas, scanner);
                    break;
                case 66:
                    Testes.obterAtividadesDaDisciplina(disciplinas, scanner);
                    break;
                case 77:
                    Testes.obterNotasEmAtividade(disciplinas, scanner);
                    break;
                default:
                    
            }
        }while(escolha !=8 );

        scanner.close();
    }
}