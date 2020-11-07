package src.dominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Aula extends Atividade {

    private static final long serialVersionUID = 1L;
    
    private LocalDateTime data;

    public Aula(int numero, String nome, LocalDateTime data){
        super(numero, nome, true, 2);
        this.data = data;
    }

    @Override
    public String getData(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return this.data.format(formatter);
    }
}
