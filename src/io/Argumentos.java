package src.io;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import src.dominio.*;
import src.menus.MenuPrincipal;

public class Argumentos {
    private Designacoes memoria;
    private String[] args;
    private Scanner scanner;

    private final CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

    public Argumentos(Designacoes designacoes, String[] args, Scanner scanner) {
        this.memoria = designacoes;
        this.args = args;
        this.scanner = scanner;
    }

    public void checaArgumentos() {
        int index = 0;

        for (String i : args) {
            index++;

            switch (i) {
                case "-p":
                    argPeriodo(args[index]);
                    break;
                case "-d":
                    argDocentes(args[index]);
                    break;
                case "-o":
                    argDisciplinas(args[index]);
                    break;
                case "-e":
                    argEstudantes(args[index]);
                    break;
                case "-m":
                    argMatriculas(args[index]);
                    break;
                case "-a":
                    argAtividades(args[index]);
                    break;
                case "-n":
                    break;
                case "--read-only":
                    break;
                case "--write-only":
                    break;
                case "-menu":
                    MenuPrincipal menu = new MenuPrincipal(scanner, memoria);
                    try {
                        menu.menu();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

        }

    }

    private void argPeriodo(String filename) {

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

    private void argDocentes(String filename){

        try (FileReader fileReader = new FileReader(filename);
                CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {
                
                linhas = linhas[0].split(Pattern.quote(";"));

                String login = linhas[0];
                String nome = linhas[1];
                String website = "NA";

                if(linhas[2] != null) website = linhas[2];

                if(memoria.getDocentes().get(login) != null){
                    System.out.println("Cadastro repetido: " + login);
                    return;
                }
        
                Docente novoDocente = new Docente(login, nome, website);
                memoria.docentes.put(login, novoDocente);
            
            }

        } catch (IOException | CsvValidationException e) {
            System.out.println("ERRO DE I/O");
        }
    }

    private void argEstudantes(String filename){

        try (FileReader fileReader = new FileReader(filename);
                CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {
            
                linhas = linhas[0].split(Pattern.quote(";"));  
                
                BigInteger matricula = new BigInteger(linhas[0]);
                String nome = linhas[1];      

                if(memoria.estudantes.get(matricula) != null){
                    System.out.println("Cadastro repetido: " + matricula);
                    return;
                }

                Estudante novoEstudante = new Estudante(matricula, nome);
                memoria.estudantes.put(matricula , novoEstudante);
            
            }

        } catch (IOException | CsvValidationException e) {
            System.out.println("ERRO DE I/O");
        } catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
        }

    }

    private void argDisciplinas(String filename){

        try (FileReader fileReader = new FileReader(filename);
                CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {

                linhas = linhas[0].split(Pattern.quote(";"));
                String strPeriodo = linhas[0];
                String codigo = linhas[1];
                String nome = linhas[2];
                String strProfessor = linhas[3];

                String[] split = strPeriodo.split(Pattern.quote("/"));

                String semestre = split[1].toUpperCase();

                if(semestre.length() != 1){
                    System.out.println("Dado Inválido: (semestre) " + semestre);
                    return;
                }

                if(memoria.disciplinas.get(codigo + "-" + strPeriodo) != null){
                    System.out.println("Cadastro repetido: " + codigo + "-" + strPeriodo);
                    return;
                }
        
                if (memoria.periodos.get(strPeriodo) == null){
                    System.out.println("Referência inválida: " + strPeriodo);
                    return;
                }

                Periodo periodo = memoria.periodos.get(strPeriodo);

                if (memoria.docentes.get(strProfessor) == null){
                    System.out.println("Referência inválida: " + strProfessor);
                    return;
                }

                Docente professor = memoria.docentes.get(strProfessor);

                Disciplina disciplina = new Disciplina(codigo, nome, periodo, professor);
                memoria.disciplinas.put(disciplina.toString(), disciplina);
                
                professor.addDisciplina(disciplina);
            
            }

        } catch (IOException | CsvValidationException e) {
            System.out.println("ERRO DE I/O");
        }

    }

    private void argMatriculas(String filename){

        try (FileReader fileReader = new FileReader(filename);
                CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {

                linhas = linhas[0].split(Pattern.quote(";"));
                String codigo = linhas[0];
                String matriculaStr = linhas[1];
                BigInteger matricula = new BigInteger(matriculaStr);
                
                if (memoria.estudantes.get(matricula) == null){
                    System.out.println("Referência inválida: " + matricula);
                    return;
                }

                String periodo = codigo.split(Pattern.quote("-"))[1];
                String semestre = periodo.split(Pattern.quote("/"))[1];
                codigo = codigo.split(Pattern.quote("-"))[0];

                System.out.println(codigo);
                System.out.println(periodo);

                if(semestre.length() != 1){
                    System.out.println("Dado Inválido: (semestre) " + semestre);
                    return;
                }

                if (memoria.disciplinas.get(codigo + "-" + periodo) == null){
                    System.out.println("Referência inválida: " + codigo + "-" + periodo);
                    return;
                }

                if (memoria.disciplinas.get(codigo + "-" + periodo).getAlunos().get(matricula) != null){
                    System.out.println("Matricula repetida: " + matricula + " em " + codigo + "-" + periodo);
                    return;
                }

                memoria.disciplinas.get(codigo + "-" + periodo).getAlunos().put(matricula, memoria.estudantes.get(matricula));
                memoria.estudantes.get(matricula).addDisciplina(memoria.disciplinas.get(codigo + "-" + periodo));
                    
            }

        } catch (IOException | CsvValidationException e) {
            System.out.println("ERRO DE I/O");
        } catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
        }

    }

    private void argAtividades(String filename){

        try (FileReader fileReader = new FileReader(filename);
        CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {
            
                linhas = linhas[0].split(Pattern.quote(";"));

                String codigo = linhas[0].split(Pattern.quote("-"))[0];
                String periodo = linhas[0].split(Pattern.quote("-"))[1];

                String nome = linhas[1];

                String semestre = periodo.split(Pattern.quote("/"))[1].toUpperCase();

                if(semestre.length() != 1){
                    System.out.println("Dado Inválido: (semestre) " + semestre);
                    return;
                }

                if (memoria.disciplinas.get(codigo + "-" + periodo) == null){
                    System.out.println("Referência inválida: " + codigo + "-" + periodo);
                    return;
                }

                //Verifica tipo de atividade
                String tipo = linhas[2].toUpperCase();

                if (tipo.equals("A")){
                    String dataStr = linhas[3] + " " + linhas[4];

                    LocalDateTime data;
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    data = LocalDateTime.parse(dataStr, formatter);
                    
                    Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo);
                    Atividade atividade = new Aula(disciplina.getAtividades().size() + 1, nome, data);
                    disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
                }

                else if (tipo.equals("T")){

                    String dataStr = linhas[3] + " 00:00";

                    LocalDateTime data;
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    data = LocalDateTime.parse(dataStr, formatter);

                    String strNAlunos = linhas[6];
                    int nAlunos = Integer.parseInt(strNAlunos);

                    String strCargaHoraria = linhas[7];
                    double cargaHoraria = Double.parseDouble(strCargaHoraria);

                    Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo);
                    Atividade atividade = new Trabalho(disciplina.getAtividades().size() + 1, nome, data, nAlunos, cargaHoraria);
                    disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
                }

                else if (tipo.equals("P")){
                    String dataStr = linhas[3] + " " + linhas[4];

                    LocalDateTime data;
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    data = LocalDateTime.parse(dataStr, formatter);

                    String conteudo = linhas[5];

                    Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo);
                    Atividade atividade = new Prova(disciplina.getAtividades().size() + 1, nome, data, conteudo);
                    disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
                }

                else if (tipo.equals("E")){
                    Map<String, String> materiais = new HashMap<>();

                    String[] materiaisStr = linhas[5].split(Pattern.quote(")"));
                    int nConteudos = materiaisStr.length / 2 ;

                    for(int j = 0; j < nConteudos ; j++ ){
                        String materialNome = materiaisStr[j].split(Pattern.quote("]"))[0] + "]";
                        String materialUrl = materiaisStr[j].split(Pattern.quote("]"))[1] + ")";

                        materiais.put(materialNome, materialUrl);           
                    }

                    Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo);
                    Atividade atividade = new Estudo(disciplina.getAtividades().size() + 1, nome, materiais);
                    disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
                }

                else {
                    System.out.println("Dado invalido: Tipo nao especificado");
                    return;
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("ERRO DE I/O");
        } catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
        }
    }

}


    /*     ESQUELETO
    
    try (FileReader fileReader = new FileReader(filename);
                CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {
            
                
            
            }

        } catch (IOException | CsvValidationException e) {
            System.out.println("ERRO DE I/O");
        }

    */
