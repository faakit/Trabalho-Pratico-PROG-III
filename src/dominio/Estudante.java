package src.dominio;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Estudante implements Serializable  {
        
        private static final long serialVersionUID = 1L;
        
        private int nAvaliacoes;
        private double totalAvaliacoes;
        private BigInteger matricula;
        private String nome;
        private List<Disciplina> disciplinas;

        public Estudante(BigInteger matricula, String nome){
                this.matricula = matricula;
                this.nome = nome;
                this.disciplinas = new ArrayList<>();
                this.nAvaliacoes = 0;
                this.totalAvaliacoes = 0;
        }

        public void incrementaTotalAvaliacoes(double nota){
                this.totalAvaliacoes+=nota;
        }

        public double getTotalAvaliacoes(){
                return this.totalAvaliacoes;
        }

        public List<Disciplina> getDisciplinas(){
                return this.disciplinas;
        }

        public void addDisciplina(Disciplina disciplina){
                this.disciplinas.add(disciplina);
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
