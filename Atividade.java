import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Atividade implements Serializable {

    private static final long serialVersionUID = 1L;
    
    String nome;
    int numero;
    private Map<Integer, Nota> notas;
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

    public Map<Integer, Nota> getNotas(){
        return this.notas;
    }

    public void addNotas(int matricula, Nota nota){
        this.notas.put(matricula, nota);
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
