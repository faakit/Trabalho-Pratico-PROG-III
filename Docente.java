import java.util.ArrayList;
import java.util.List;

public class Docente {
    String login;
    String nome;
    String website;
    private List<Disciplina> disciplinas;

    public Docente(String login, String nome, String website){
        this.login = login;
        this.nome = nome;
        this.website = website;
        this.disciplinas = new ArrayList<>();
    }
    
    public List<Disciplina> getDisciplinas(){
        return this.disciplinas;
    }

    public void addDisciplina(Disciplina disciplina){
        this.disciplinas.add(disciplina);
    }

    public String getNome(){
        return this.nome;
    }
}
