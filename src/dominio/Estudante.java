package src.dominio;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Estudante implements Serializable  {
        
        private static final long serialVersionUID = 1L;
        
        private int nAvaliacoes;
        private double totalAvaliacoes;
        private BigInteger matricula;
        private String nome;
        private List<Disciplina> disciplinas;
        private Set<Periodo> periodos;

        public Estudante(BigInteger matricula, String nome){
                this.matricula = matricula;
                this.nome = nome;
                this.disciplinas = new ArrayList<>();
                this.nAvaliacoes = 0;
                this.totalAvaliacoes = 0;
                this.periodos = new HashSet<>();
        }

        public void incrementaTotalAvaliacoes(double nota){
                this.totalAvaliacoes+=nota;
        }

        public int getNPeriodos(){
                return this.periodos.size();
        }

        public double getTotalAvaliacoes(){
                return this.totalAvaliacoes;
        }

        public List<Disciplina> getDisciplinas(){
                return this.disciplinas;
        }

        public void addDisciplina(Disciplina disciplina){
                this.disciplinas.add(disciplina);
                this.periodos.add(disciplina.getPeriodo());
        }

        public void incrementaAvaliacoes(){
                this.nAvaliacoes++;
        }

        public int getNAvaliacoes(){
                return this.nAvaliacoes;
        }

        public BigInteger verMatricula(){
                return this.matricula;
        }

        public String getNome(){
                return this.nome;
        }

        public void designaMateria(Disciplina disciplina){
                this.disciplinas.add(disciplina);
        }
}
