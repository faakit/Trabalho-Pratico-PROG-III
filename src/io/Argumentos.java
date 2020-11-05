package src.io;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import src.dominio.*;

public class Argumentos {
    private Designacoes memoria;
    private String[] args;

    private final CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

    public Argumentos(Designacoes designacoes, String[] args) {
        this.memoria = designacoes;
        this.args = args;
    }

    public void checaArgumentos() {
        int index = 0;

        for (String i : args) {
            System.out.println(i);
            index++;

            switch (i) {
                case "-p":
                    argPeriodo(args[index]);
                    break;
                case "-d":
                    break;
                case "-o":
                    break;
                case "-e":
                    break;
                case "-m":
                    break;
                case "-a":
                    break;
                case "-n":
                    break;
                case "--read-only":
                    break;
                case "--write-only":
                    break;
                default:
                    break;
            }

        }

    }

    void argPeriodo(String filename) {

        try (FileReader fileReader = new FileReader(filename);
                CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {

                
                int ano = Integer.parseInt(linhas[0]);
                String semestre = linhas[1].toUpperCase();

                if (semestre.length() != 1) {
                    System.out.println("Dado Inválido: (semestre) " + semestre);
                    return;
                }

                Periodo periodo = new Periodo(ano, semestre);

                if (memoria.periodos.get(periodo.toString()) != null) {
                    System.out.println("Cadastro repetido: " + periodo);
                    return;
                }

                memoria.periodos.put(periodo.toString(), periodo);

            }

        } catch (IOException | CsvValidationException e) {
            System.out.println("ERRO DE I/O");
        } catch (NumberFormatException e) {
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
        }

    }

}
