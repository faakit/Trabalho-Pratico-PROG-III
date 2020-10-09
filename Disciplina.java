import java.util.*;

public class Disciplina {
    String codigo;
    String nome;
    Periodo periodo;
    Docente professor;
    Map<Integer, Estudante> alunos;
    Map<Integer, Atividade> atividades;

    public Disciplina(String codigo, String nome, Periodo periodo, Docente professor){
        this.codigo = codigo;
        this.nome = nome;
        this.periodo = periodo;
        this.professor = professor;
        alunos = new HashMap<>();
        atividades = new HashMap<>();
    }

    public String getNome() {
        return this.nome;
    }

    public Periodo getPeriodo(){
        return this.periodo;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Map<Integer, Atividade> getAtividades() {
        return this.atividades;
    }

    @Override
    public String toString(){
        return this.codigo + "-" + this.periodo.ano + "/" + this.periodo.semestre;
    }
}