package src.dominio;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.Collator;
import java.util.*;

public class Disciplina implements Serializable, Comparable<Disciplina> {

    private static final long serialVersionUID = 1L;
    
    private String codigo;
    private String nome;
    private Periodo periodo;
    private Docente professor;
    private Map<BigInteger, Estudante> alunos;
    private Map<Integer, Atividade> atividades;
    private int nAtvSincronas;
    private double cargaHoraria;

    public Disciplina(String codigo, String nome, Periodo periodo, Docente professor){
        this.codigo = codigo;
        this.nome = nome;
        this.periodo = periodo;
        this.professor = professor;
        this.nAtvSincronas = 0;
        this.cargaHoraria = 0;
        alunos = new HashMap<>();
        atividades = new HashMap<>();
    }

    public double getCargaHoraria() {
        return cargaHoraria;
    }

    public int getnAtvSincronas() {
        return nAtvSincronas;
    }

    public Docente getProfessor() {
        return this.professor;
    }

    public Map<BigInteger, Estudante> getAlunos(){
        return this.alunos;
    }

    public void addAlunos(BigInteger matricula, Estudante estudante){
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
        if(atividade.isSincrona()) this.nAtvSincronas++;
        this.cargaHoraria += atividade.getCargaHoraria();
    }

    @Override
    public String toString(){
        return this.codigo + "-" + this.periodo.getAno() + "/" + this.periodo.getSemestre();
    }

    @Override
    public int compareTo(Disciplina o) {
        //Inicia comparando período
        int c = this.periodo.compareTo(o.getPeriodo());

        //Caso seja o mesmo período compara o código da disciplina
        if (c == 0) {
            Collator collator = Collator.getInstance(Locale.getDefault());
            collator.setStrength(Collator.PRIMARY);
            c = collator.compare(this.codigo, o.codigo);
        }
        return c;
    }

    
}