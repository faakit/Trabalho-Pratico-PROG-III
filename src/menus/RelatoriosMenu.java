package src.menus;

import java.util.Scanner;

public class RelatoriosMenu {
    final Scanner scanner;
    final Designacoes memoria;

    final Verificador verificador = new Verificador();

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

                
                String i = scanner.nextLine();

                escolha = verificador.verificaInt(i);

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
