import java.util.Scanner;

public class RelatoriosMenu {
    Scanner scanner;
    Designacoes memoria;

    public RelatoriosMenu(Scanner scanner, Designacoes memoria){
        this.scanner = scanner;
        this.memoria = memoria;
    }


    public void menuRelatorios(){
        int escolha;

            do{

                System.out.println("Escolha uma opção");
                System.out.println("-------------------------\n");
                System.out.println("1 - Visão geral do período acadêmico");
                System.out.println("2 - Estatísticas dos docentes");
                System.out.println("3 - Estatísticas dos estudantes");
                System.out.println("4 - Estatísticas das disciplinas de um docente");
                System.out.println("5 - Retornar ao menu anterior");

                
                escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        memoria.visaoGeralPeriodo();
                        break;
                    case 2:
                        memoria.estatisticaDocentes();
                        break;
                    case 3:
                        memoria.estatisticaEstudante();
                        break;
                    case 4:
                        memoria.estatisticaDisciplinasDocente();
                        break;
                    default:
                        
                }
            }while(escolha !=5 );
    }
}
