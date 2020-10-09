import java.util.ArrayList;
import java.util.List;

public class Estudante {
        private int nAvaliacoes;
        private double totalAvaliacoes;
        int matricula;
        String nome;
        List<Disciplina> disciplinas;

        public Estudante(int matricula, String nome){
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

        public void incrementaAvaliacoes(){
                this.nAvaliacoes++;
        }

        public int getNAvaliacoes(){
                return this.nAvaliacoes;
        }

        public int getMatricula(){
                return this.matricula;
        }

        public String getNome(){
                return this.nome;
        }

        public void designaMateria(Disciplina disciplina){
                this.disciplinas.add(disciplina);
        }
}
