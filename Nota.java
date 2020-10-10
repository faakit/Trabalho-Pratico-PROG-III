import java.io.Serializable;

public class Nota implements Serializable {

    private static final long serialVersionUID = 1L;
    
    Estudante estudante;
    double notaDoAluno;

    public Nota(Estudante estudante, double nota){
        this.estudante = estudante;
        this.notaDoAluno = nota;
    }

    public int getMatricula(){
        return this.estudante.matricula;
    }
}
