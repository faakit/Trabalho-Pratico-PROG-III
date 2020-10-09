import java.time.LocalDateTime;

public class Prova extends Atividade {
    LocalDateTime data;
    String conteudo;

    public Prova(int numero, String nome, LocalDateTime data, String conteudo){
        super(numero, nome, true, 2);
        this.data = data;
        this.conteudo = conteudo;
    }

    @Override
    public LocalDateTime getData(){
        return this.data;
    }
}
