import java.util.*;

public class Menu {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("pt", "BR"));

        Scanner scanner = new Scanner(System.in);
        Designacoes designacoes = new Designacoes(scanner);

        int escolha;

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
                    designacoes.cadastraPeriodo();
                    break;
                case 2:
                    designacoes.cadastraDocente();
                    break;
                case 3:
                    designacoes.cadastraDisciplina();
                    break;
                case 4:
                    designacoes.cadastraEstudante();
                    break;
                case 5:
                    designacoes.cadastraAlunoEmDisciplina();
                    break;
                case 6:
                    designacoes.cadastraAtividadeEmDisciplina();
                    break;
                case 7:
                    // designacoes.cadastraNotaEmAtividade();
                    break;
                case 8:
                    scanner.close();
                    break;
                case 11:
                    Testes.verificaPeriodos(designacoes);
                    break;
                case 22:
                    // Testes.verificaDocentes(docentes);
                    break;
                case 33:
                    // Testes.verificaDisciplinas(disciplinas);
                    break;
                case 44:
                    // Testes.verificaEstudantes(estudantes);
                    break;
                case 55:
                    // Testes.verificaAlunosEmDisciplina(disciplinas, scanner);
                    break;
                case 66:
                    // Testes.obterAtividadesDaDisciplina(disciplinas, scanner);
                    break;
                case 77:
                    // Testes.obterNotasEmAtividade(disciplinas, scanner);
                    break;
                default:
                    
            }
        }while(escolha !=8 );

        scanner.close();
    }
}