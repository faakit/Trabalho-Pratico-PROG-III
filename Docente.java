import java.util.Scanner;

public class Docente {
    String login;
    String nome;
    String website;

    public Docente cadastraDocente(Scanner scanner){
        String login;
        String nome;
        String website;
        char escolha;
        
        System.out.println("Digite o login institucional: ");
        scanner.nextLine();
        login = scanner.nextLine();

        System.out.println("Digite o nome completo: ");
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


        this.login = login;
        this.nome = nome;
        this.website = website;

        return this;
    }
}
