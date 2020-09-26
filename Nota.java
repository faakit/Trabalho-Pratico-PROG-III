public class Nota {
    Estudante estudante;
    Atividade atividade;
    float notaDoAluno;

    public Nota(Estudante estudante, Atividade atividade, float nota){
        this.atividade = atividade;
        this.estudante = estudante;
        this.notaDoAluno = nota;
    }
}
