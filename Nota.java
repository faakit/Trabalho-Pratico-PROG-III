public class Nota {
    Estudante estudante;
    Atividade atividade;
    double notaDoAluno;

    public Nota(Estudante estudante, Atividade atividade, double nota){
        this.atividade = atividade;
        this.estudante = estudante;
        this.notaDoAluno = nota;
    }
}
