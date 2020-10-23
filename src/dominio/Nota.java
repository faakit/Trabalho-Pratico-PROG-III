package src.dominio;

import java.io.Serializable;

public class Nota implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Estudante estudante;
    private double notaDoAluno;

    public Nota(Estudante estudante, double nota){
        this.estudante = estudante;
        this.notaDoAluno = nota;
    }

    public Estudante getEstudante() {
        return this.estudante;
    }

    public double getNotaDoAluno() {
        return this.notaDoAluno;
    }

    public int getMatricula(){
        return this.estudante.getMatricula();
    }
}
