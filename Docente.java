import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String login;
    private String nome;
    private String website;
    private List<Disciplina> disciplinas;

    public Docente(String login, String nome, String website){
        this.login = login;
        this.nome = nome;
        this.website = website;
        this.disciplinas = new ArrayList<>();
    }
    
    public String getLogin() {
        return this.login;
    }

    public String getWebsite() {
        return this.website;
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
