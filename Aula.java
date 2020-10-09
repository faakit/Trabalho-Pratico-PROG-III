import java.time.LocalDateTime;

public class Aula extends Atividade {
    LocalDateTime data;

    public Aula(int numero, String nome, LocalDateTime data){
        super(numero, nome, true, 2);
        this.data = data;
    }

    @Override
    public LocalDateTime getData(){
        return this.data;
    }
}
