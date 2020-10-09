import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Atividade {
    String nome;
    int numero;
    Map<Integer, Nota> notas;
    boolean sincrona;
    double cargaHoraria;

    public Atividade(int numero, String nome, boolean sincrona, double cargaHoraria){
        this.nome = nome;
        this.numero = numero;
        this.notas = new HashMap<>();
        this.sincrona = sincrona;
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome(){
        return this.nome;
    }

    public boolean isSincrona() {
        return this.sincrona;
    }

    public LocalDateTime getData(){
        return null;
    }

    public double getCargaHoraria() {
        return this.cargaHoraria;
    }

}
