import java.util.Scanner;

public class Estudante {
        int matricula;
        String nome;

        public void cadastraEstudante(Scanner scanner){
                int matricula;
                String nome;
                
                System.out.println("Digite a matricula: ");
                matricula = scanner.nextInt();
        
                System.out.println("Digite o nome completo: ");
                nome = scanner.nextLine();

                this.matricula = matricula;
                this.nome = nome;
        }
}
