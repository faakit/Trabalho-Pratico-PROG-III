import java.io.Serializable;

public class Periodo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    int ano;
    String semestre;

    public Periodo(int ano, String semestre){
        this.ano = ano;
        this.semestre = semestre;
    }

    @Override
    public String toString(){
        return this.ano + "/" + this.semestre;
    }

}