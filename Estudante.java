import java.util.Scanner;

public class Estudante {
        int matricula;
        String nome;

        public Estudante cadastraEstudante(Scanner scanner){
                int matricula;
                String nome;
                
                System.out.println("Digite a matricula: ");
                matricula = scanner.nextInt();
        
                System.out.println("Digite o nome completo: ");
                scanner.nextLine();
                nome = scanner.nextLine();

                this.matricula = matricula;
                this.nome = nome;

                return this;
        }

        public Estudante retornaEstudante(){
                return this;
        }
}
