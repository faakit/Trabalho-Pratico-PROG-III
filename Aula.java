import java.time.LocalDateTime;

public class Aula extends Atividade {
    LocalDateTime data;

    public Aula(int numero, String nome, LocalDateTime data){
        super(numero, nome);
        this.data = data;
    }
}
