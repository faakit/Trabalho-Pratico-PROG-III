package src.dominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public LocalDateTime getLocalDateTime() {
        return prazo;
    }

    @Override
    public String getData(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.prazo.format(formatter);
    }
}
