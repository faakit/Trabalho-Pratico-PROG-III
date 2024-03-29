package src.io;

import java.io.*;
import java.math.BigInteger;
import java.util.Map;

import src.dominio.*;

@SuppressWarnings("unchecked")   //Para ignorar os avisos sobre o cast de objeto para os mapas

public class LeitorEscritor {
    Designacoes memoria;

    public LeitorEscritor(Designacoes memoria) {
        this.memoria = memoria;
    }

    public void escreverEmDisco() throws Exception {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dados.dat"));) {
            out.writeObject(memoria.getDisciplinas());
            out.writeObject(memoria.getDocentes());
            out.writeObject(memoria.getEstudantes());
            out.writeObject(memoria.getPeriodos());
        } catch (Exception e) {
            System.out.println("Erro de i/o.");
        }

    }

    public void lerDoDisco() throws Exception {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("dados.dat"));) {
            this.memoria.setDisciplinas((Map<String, Disciplina>) in.readObject());
            this.memoria.setDocentes((Map<String, Docente>) in.readObject());
            this.memoria.setEstudantes((Map<BigInteger, Estudante>) in.readObject());
            this.memoria.setPeriodos((Map<String, Periodo>) in.readObject());
        } catch (Exception e){
            System.out.println("Erro de i/o.");
        }

    }
}
