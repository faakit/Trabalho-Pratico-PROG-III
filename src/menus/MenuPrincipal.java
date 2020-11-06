package src.menus;

import java.util.*;

import src.io.Designacoes;
import src.io.LeitorEscritor;

public class MenuPrincipal {
    private Scanner scanner;

    final Designacoes designacoes;
    final Verificador verificador = new Verificador();
    final LeitorEscritor leitorEscritor;

    public MenuPrincipal (Scanner scanner, Designacoes designacoes){
        this.designacoes = designacoes;
        this.scanner = scanner;
        this.leitorEscritor = new LeitorEscritor(designacoes);
    }
    
    public void menu() throws Exception {

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
            System.out.println("8 - Relatorios");
            System.out.println("9 - Salvar em disco");
            System.out.println("10 - Carregar do disco");
    
            String escolhaString = scanner.nextLine();
            
            escolha = verificador.verificaInt(escolhaString);
    
            switch (escolha) {
                case 1:
                    try{
                        designacoes.cadastraPeriodo();
                    }
                    catch(IllegalArgumentException e){
                        System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
                    }
                    catch(ArrayIndexOutOfBoundsException e){            //Acontece ao digitar "0" no período
                        System.out.println("Não foi possível registrar o período.");
                    }
                    break;
                case 2:
                    designacoes.cadastraDocente();
                    break;
                case 3:
                    designacoes.cadastraDisciplina();
                    break;
                case 4:
                    try{
                        designacoes.cadastraEstudante();
                    }
                    catch(InputMismatchException e){
                        System.out.println("Dado inválido, dado digitado não é um número");
                    }
                    break;
                case 5:
                    designacoes.cadastraAlunoEmDisciplina();
                    break;
                case 6:
                    designacoes.cadastraAtividadeEmDisciplina();
                    break;
                case 7:
                    designacoes.cadastraNotaEmAtividade();
                    break;
                case 8:
                    RelatoriosMenu relatorios = new RelatoriosMenu(scanner, designacoes);
                    relatorios.menuRelatorios();
                    break;
                case 9:
                    leitorEscritor.escreverEmDisco();
                    break;
                case 10:
                    leitorEscritor.lerDoDisco();
                    break;
                case 11:
                    Testes.verificaPeriodos(designacoes);
                    break;
                case 22:
                    Testes.verificaDocentes(designacoes.getDocentes());
                    break;
                case 33:
                    Testes.verificaDisciplinas(designacoes.getDisciplinas());
                    break;
                case 44:
                    Testes.verificaEstudantes(designacoes.getEstudantes());
                    break;
                case 55:
                    Testes.verificaAlunosEmDisciplina(designacoes.getDisciplinas(), scanner);
                    break;
                default:
            
            }
        }while(escolha !=0 ); 

    }    
}