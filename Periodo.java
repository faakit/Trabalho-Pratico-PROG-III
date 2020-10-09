public class Periodo{
    int ano;
    String semestre;

    public Periodo(int ano, String semestre){
        this.ano = ano;
        this.semestre = semestre;
    }

    @Override
    public String toString(){
        return this.ano + "/" + this.semestre;
    }

}