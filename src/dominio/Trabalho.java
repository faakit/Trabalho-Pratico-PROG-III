package src.dominio;

import java.time.LocalDateTime;

public class Trabalho extends Atividade {

    private static final long serialVersionUID = 1L;
    
    private LocalDateTime prazo;
    private int nAlunos;

    public Trabalho(int numero, String nome, LocalDateTime prazo, int nAlunos, double cargaHoraria){
        super(numero, nome, false, cargaHoraria);
        this.prazo = prazo;
        this.nAlunos = nAlunos;
    }

    public int getnAlunos() {
        return this.nAlunos;
    }

    @Override
    public LocalDateTime getData(){
        return this.prazo;
    }
}
