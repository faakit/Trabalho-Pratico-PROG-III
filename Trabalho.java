import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class Trabalho extends Atividade {
    Map<Integer ,Nota> notas;
    LocalDateTime prazo;
    int nAlunos;
    double cargaHoraria;

    public Trabalho(int numero, String nome, LocalDateTime prazo, int nAlunos, double cargaHoraria){
        super(numero, nome);
        this.notas = new HashMap<>();
        this.prazo = prazo;
        this.nAlunos = nAlunos;
        this.cargaHoraria = cargaHoraria;
    }


}
