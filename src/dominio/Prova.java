package src.dominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Prova extends Atividade {

    private static final long serialVersionUID = 1L;
    
    private LocalDateTime data;
    private String conteudo;

    public Prova(int numero, String nome, LocalDateTime data, String conteudo){
        super(numero, nome, true, 2);
        this.data = data;
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    @Override
    public LocalDateTime getLocalDateTime() {
        return data;
    }

    @Override
    public String getData(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.data.format(formatter);
    }
}
