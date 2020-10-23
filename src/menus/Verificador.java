package src.menus;

public class Verificador {

    public int verificaInt(String numero){
        int escolha;
        try{        
            escolha = Integer.parseInt(numero);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
            escolha = 20;
        }
        return escolha;
    }


}