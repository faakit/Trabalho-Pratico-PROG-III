package src.dominio;

import java.io.Serializable;

public class Periodo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int ano;
    private String semestre;

    public Periodo(int ano, String semestre){
        this.ano = ano;
        this.semestre = semestre;
    }

    public int getAno() {
        return this.ano;
    }

    public String getSemestre() {
        return this.semestre;
    }

    @Override
    public String toString(){
        return this.ano + "/" + this.semestre;
    }

}