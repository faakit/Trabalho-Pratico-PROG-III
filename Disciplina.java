import java.io.Serializable;
import java.util.*;

public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;
    
    String codigo;
    String nome;
    Periodo periodo;
    Docente professor;
    private Map<Integer, Estudante> alunos;
    private Map<Integer, Atividade> atividades;

    public Disciplina(String codigo, String nome, Periodo periodo, Docente professor){
        this.codigo = codigo;
        this.nome = nome;
        this.periodo = periodo;
        this.professor = professor;
        alunos = new HashMap<>();
        atividades = new HashMap<>();
    }

    public Map<Integer, Estudante> getAlunos(){
        return this.alunos;
    }

    public void addAlunos(int matricula, Estudante estudante){
        this.alunos.put(matricula, estudante);
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

    public void addAtividades(int numero, Atividade atividade){
        this.atividades.put(numero, atividade);
    }

    @Override
    public String toString(){
        return this.codigo + "-" + this.periodo.ano + "/" + this.periodo.semestre;
    }
}