import java.time.LocalDateTime;

public class Prova extends Atividade {

    private static final long serialVersionUID = 1L;
    
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
