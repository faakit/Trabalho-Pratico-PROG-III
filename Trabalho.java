import java.time.LocalDateTime;

public class Trabalho extends Atividade {
    LocalDateTime prazo;
    int nAlunos;

    public Trabalho(int numero, String nome, LocalDateTime prazo, int nAlunos, double cargaHoraria){
        super(numero, nome, false, cargaHoraria);
        this.prazo = prazo;
        this.nAlunos = nAlunos;
    }

    @Override
    public LocalDateTime getData(){
        return this.prazo;
    }
}
