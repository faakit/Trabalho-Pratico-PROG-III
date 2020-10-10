import java.io.*;
import java.util.Map;

@SuppressWarnings("unchecked")   //Para ignorar os avisos sobre o cast de objeto para os mapas

public class LeitorEscritor {
    Designacoes memoria;

    public LeitorEscritor(Designacoes memoria) {
        this.memoria = memoria;
    }

    public void escreverEmDisco() throws Exception {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("objs.dat"));) {
            out.writeObject(memoria.disciplinas);
            out.writeObject(memoria.docentes);
            out.writeObject(memoria.estudantes);
            out.writeObject(memoria.periodos);
        } catch (Exception e) {
        }

    }

    public void lerDoDisco() throws Exception {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("objs.dat"));) {
            this.memoria.disciplinas = (Map<String, Disciplina>) in.readObject();
            this.memoria.docentes = (Map<String, Docente>) in.readObject();
            this.memoria.estudantes = (Map<Integer, Estudante>) in.readObject();
            this.memoria.periodos = (Map<String, Periodo>) in.readObject();
        } catch (Exception e){

        }

    }
}
