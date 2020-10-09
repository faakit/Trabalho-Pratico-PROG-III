public class Nota {
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
