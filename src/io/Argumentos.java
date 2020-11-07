package src.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
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
        int writeOnly;
        int readOnly;

        for (String i : args) {
            index++;

            switch (i) {
                case "-p":
                    argP(args[index]);
                    break;
                case "-d":
                    argD(args[index]);
                    break;
                case "-o":
                    argO(args[index]);
                    break;
                case "-e":
                    argE(args[index]);
                    break;
                case "-m":
                    argM(args[index]);
                    break;
                case "-a":
                    argA(args[index]);
                    break;
                case "-n":
                    argN(args[index]);
                    break;
                case "--read-only":
                    break;
                case "--write-only":
                    break;
                case "-teste":
                    visaoGeralPeriodo();
                    estatisticaDocentes();
                    estatisticaEstudante();
                    estatisticaDisciplinasDocente();
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

    private void argP(String filename) {
        /* ARMAZENA PERIODOS */
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

    private void argD(String filename){
        /* ARMAZENA DOCENTES */
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

    private void argE(String filename){
        /* ARMAZENA ESTUDANTES */
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

    private void argO(String filename){
        /* ARMAZENA DISCIPLINAS */
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

    private void argM(String filename){
        /* RELACIONA ESTUDANTES A DISCIPLINAS */
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

    private void argA(String filename){
        /* ARMAZENA ATIVIDADES DE DISCIPLINAS */
        try (FileReader fileReader = new FileReader(filename);
        CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {

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
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    data = LocalDateTime.parse(dataStr, formatter);
                    
                    Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo);
                    Atividade atividade = new Aula(disciplina.getAtividades().size() + 1, nome, data);
                    disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
                }

                else if (tipo.equals("T")){

                    String dataStr = linhas[3] + " 00:00";

                    LocalDateTime data;
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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

    private void argN(String filename){

        /* ATRIBUI AVALIAÇOES DE ATIVIDADES POR ALUNOS */
        try (FileReader fileReader = new FileReader(filename);
                CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {
               
                String codigo = linhas[0].split(Pattern.quote("-"))[0];
                String periodo = linhas[0].split(Pattern.quote("-"))[1].toUpperCase();
                String semestre = periodo.split(Pattern.quote("/"))[1];

                String matriculaStr = linhas[1];
                BigInteger matricula = new BigInteger(matriculaStr);

                String numero = linhas[2];
                int nAtividade = Integer.parseInt(numero);


                String notaStr = linhas[3].replace(",", "."); 
                double nota = Double.parseDouble(notaStr);

                if(semestre.length() != 1){
                    System.out.println("Dado Inválido: (semestre) " + semestre);
                    return;
                }

                if (memoria.disciplinas.get(codigo + "-" + periodo) == null){
                    System.out.println("Referência inválida: " + codigo + "-" + periodo);
                    return;
                }

                Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo); 
            
                if (disciplina.getAtividades().get(nAtividade) == null){
                    System.out.println("Referência inválida a atividade de número: " + nAtividade);
                    return;
                }
                
                if (memoria.estudantes.get(matricula) == null){
                    System.out.println("Referência inválida: " + matricula);
                    return;
                }

                if (disciplina.getAtividades().get(nAtividade).getNotas().get(matricula) != null ){
                    System.out.println("Avaliação repetida: estudante " + matricula + " para atividade " + nAtividade + " de " + disciplina);
                }

                Nota novaNota = new Nota(memoria.estudantes.get(matricula), nota);
                memoria.estudantes.get(matricula).incrementaAvaliacoes();
                memoria.estudantes.get(matricula).incrementaTotalAvaliacoes(nota);

                disciplina.getAtividades().get(nAtividade).addNotas(novaNota.getMatricula() ,novaNota);     
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("ERRO DE I/O");
        } catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
        }
        
    }

    /* Relatórios */
    public void visaoGeralPeriodo(){
        File file = new File("relatorios/1-visao-geral.csv");

        try(FileWriter output = new FileWriter(file);
            CSVWriter writer = new CSVWriter(output, ';',
                                            com.opencsv.ICSVWriter.DEFAULT_QUOTE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_LINE_END);){
            
            /* Adiciona cabeçalho */
            String[] linhas = { "Período", "Código Disciplina", "Disciplina", "Docente Responsável", "E-mail Docente","Qtd. Estudantes", "Qtd. Atividades" };
            writer.writeNext(linhas);
            
            /* Cria lista de periodos para ordenar */
            List<Periodo> periodosOrdenados = new ArrayList<>();
            for(Map.Entry<String, Periodo> entrada : memoria.periodos.entrySet()){
                periodosOrdenados.add(entrada.getValue());
            }

            /* Ordena por ano e semestre crescente */
            Collections.sort(periodosOrdenados, (d1, d2) -> {
                int c = d1.getAno() - d2.getAno();
                if (c!=0) return c;
                else return d1.getSemestre().compareTo(d2.getSemestre());
            });

            for(Periodo entrada : periodosOrdenados){
                List<Disciplina> listaOrdenada = new ArrayList<>();

                /* Pega as disciplinas no período informado e coloca em uma lista */
                
                for(Map.Entry<String, Disciplina> j : memoria.disciplinas.entrySet()){
                    if (j.getValue().getPeriodo().toString().equals(entrada.toString())) listaOrdenada.add(j.getValue());
                }
            
                /* Ordena a lista em ordem crescente de nome da disciplina */
                Collections.sort(listaOrdenada, (d1, d2) ->   d1.getNome().compareTo(d2.getNome()));

                for(Disciplina j : listaOrdenada){
                    /* Escreve o array e depois o escreve no csv */
                    linhas[0] = entrada.toString();
                    linhas[1] = j.getCodigo();
                    linhas[2] = j.getNome();
                    linhas[3] = j.getProfessor().getNome();
                    linhas[4] = j.getProfessor().getLogin() + "@ufes.br";
                    linhas[5] = Integer.toString(j.getAlunos().size());
                    linhas[6] = Integer.toString(j.getAtividades().size());

                    writer.writeNext(linhas);
                }
            }
        } catch (IOException e) { 
            System.out.println("ERRO DE I/O");
        } 
        
        
    }

    public void estatisticaDocentes(){
        
        File file = new File("relatorios/2-docentes.csv");

        try(FileWriter output = new FileWriter(file);
            CSVWriter writer = new CSVWriter(output, ';',
                                            com.opencsv.ICSVWriter.DEFAULT_QUOTE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_LINE_END);){
            
            /* Cria cabeçalho */ 
            String[] linhas = { "Docente", "Qtd. Disciplinas", "Qtd. Períodos", "Média Atividades/Disciplina", "% síncronas", "% Assíncronas","Média de Notas"};
            writer.writeNext(linhas);
            
            List<Docente> listaOrdenada = new ArrayList<>();

            /* Cria a lista de docentes */
            for( Map.Entry<String , Docente> i : memoria.docentes.entrySet()){
                listaOrdenada.add(i.getValue());
            }

            /* Ordena a lista em ordem decrescente de nome */
            Collections.sort(listaOrdenada, (d1, d2) ->   d2.getNome().compareTo(d1.getNome()));

            for(Docente i : listaOrdenada){
                Set<Periodo> periodos = new HashSet<>();
                int numeroDisciplinas=0;
                int numeroAtividades=0;
                double totalNotas=0;
                int nNotas = 0;
                int atvSincronas=0;
                int atvAssincronas=0;

                /* Percorre disciplinas do docente responsável */
                for(Disciplina j : i.getDisciplinas()){

                    periodos.add(j.getPeriodo());
                    numeroDisciplinas++; 
                    numeroAtividades += j.getAtividades().size();
                    atvSincronas = j.getnAtvSincronas();
                    
                    /* Percorre Atividades */
                    for(Map.Entry<Integer, Atividade> k : j.getAtividades().entrySet()){

                        nNotas = k.getValue().getNotas().size();
                            for(Map.Entry<BigInteger, Nota> l : k.getValue().getNotas().entrySet()){
                                totalNotas += l.getValue().getNotaDoAluno();
                            }
                        }
                    
                }

                double mediaAtivXDisciplina=0;
                if(numeroDisciplinas != 0){
                    mediaAtivXDisciplina = (double)numeroAtividades / numeroDisciplinas;
                }

                double percentualSincXAssinc=0;
                if (atvAssincronas != 0){
                    percentualSincXAssinc = (double)atvSincronas/atvAssincronas * 100;
                }

                double mediaNotas=0;
                if (nNotas != 0){
                    mediaNotas = totalNotas/nNotas;
                }

                linhas[0] = i.getNome();
                linhas[1] = Integer.toString(numeroDisciplinas);
                linhas[2] = Integer.toString(periodos.size());
                linhas[3] = String.format("%.1f", mediaAtivXDisciplina);
                linhas[4] = String.format("%.0f", percentualSincXAssinc);
                if(numeroAtividades == 0){
                    linhas[5] = String.valueOf(0);
                } else{
                    linhas[5] = String.format("%.0f", (100 - percentualSincXAssinc));
                }
                linhas[6] =  String.format("%.1f" , mediaNotas);
                writer.writeNext(linhas);

            }
        
        } catch (IOException e) { 
            System.out.println("ERRO DE I/O");
        }

    }

    public void estatisticaEstudante(){

        File file = new File("relatorios/3-estudantes.csv");

        try(FileWriter output = new FileWriter(file);
            CSVWriter writer = new CSVWriter(output, ';',
                                            com.opencsv.ICSVWriter.DEFAULT_QUOTE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_LINE_END);){

            String[] linhas = { "Matricula", "Nome", "Media Disciplinas/Periodo", "Media avaliacoes/Disciplina", "Media Notas Avaliacoes"};
            writer.writeNext(linhas);
        
            List<Estudante> listaOrdenada = new ArrayList<>();

            for(Map.Entry<BigInteger, Estudante> i : memoria.estudantes.entrySet()){

                listaOrdenada.add(i.getValue());

            }

            Collections.sort(listaOrdenada, (d1, d2) -> {
                int c = d2.getNAvaliacoes() - d1.getNAvaliacoes();
                if (c!=0) return c;
                else return d1.getNome().compareTo(d2.getNome());
            });

            for(Estudante i : listaOrdenada){

                Set<Periodo> periodos = new HashSet<>();
                int nDisciplinas = 0;


                for(Disciplina j : i.getDisciplinas()){

                    periodos.add(j.getPeriodo());
                    nDisciplinas++;

                }

                linhas[0] = i.verMatricula().toString();
                linhas[1] = i.getNome();
                if(periodos.isEmpty()){ linhas[2] = "0";}
                else linhas[2] = String.format("%.1f", (double)nDisciplinas/periodos.size()) ;
                if (nDisciplinas!=0) linhas[3] = String.format("%.1f", (double)i.getNAvaliacoes()/nDisciplinas);
                else linhas[3] = "0";
                if(i.getNAvaliacoes() != 0) linhas[4] = String.format("%.1f", i.getTotalAvaliacoes()/i.getNAvaliacoes());
                else linhas[4] = "0";
                
                writer.writeNext(linhas);
            }
        
        } catch (IOException e) { 
            System.out.println("ERRO DE I/O");
        }

        

    }

    public void estatisticaDisciplinasDocente(){

        File file = new File("relatorios/4-disciplinas.csv");

        try(FileWriter output = new FileWriter(file);
            CSVWriter writer = new CSVWriter(output, ';',
                                            com.opencsv.ICSVWriter.DEFAULT_QUOTE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_LINE_END);){
       
            String[] linhas = { "Docente", "Periodo", "Codigo", "Nome", "Qtd. Atividades", "% sincronas","% Assincronas", "CH", "Datas Avaliacoes" };
            writer.writeNext(linhas);
        
            for(Map.Entry<String, Docente> docente: memoria.docentes.entrySet() ){

                Docente professor = docente.getValue();
                List<Disciplina> listaOrdenada = new ArrayList<>();
    
                /* Cria e ordena lista de disciplinas */
                for(Disciplina i : professor.getDisciplinas()){
                    listaOrdenada.add(i);
                }
    
                Collections.sort(listaOrdenada, (d1, d2) -> {
                    int c = d1.getPeriodo().toString().compareTo(d2.getPeriodo().toString());
                    if (c!=0) return c;
                    else return d1.getCodigo().compareTo(d2.getCodigo());
                });
    
                for (Disciplina i : listaOrdenada){
                    List<Atividade> atividadesOrdenada = new ArrayList<>();
    
                    /* Cria e ordena a lista de atividades */
                    for(Map.Entry<Integer, Atividade> j : i.getAtividades().entrySet()){
                        atividadesOrdenada.add(j.getValue());
                    }
    
                    Collections.sort(atividadesOrdenada, (d1, d2) -> {
                        int c;
                        if (d1.getData() != null && d2.getData() != null) {
                            c = d1.getData().compareTo(d2.getData()); 
                            if (c!= 0) return c;
                        }
                        return d1.getNome().compareTo(d2.getNome()) ;
                    });
                    
                    double porcentagemSincXAssinc = 100 * (double)i.getnAtvSincronas()/(i.getAtividades().size());
    
                    linhas[0] = professor.getLogin();
                    linhas[1] = i.getPeriodo().toString();
                    linhas[2] = i.getCodigo();
                    linhas[3] = i.getNome();
                    linhas[4] = Integer.toString(i.getAtividades().size());
                    if(i.getAtividades().isEmpty()) linhas[5] = "0";
                    else linhas[5] = String.format("%.0f" ,porcentagemSincXAssinc);
                    if(i.getAtividades().isEmpty()) linhas[6] = "0";
                    else linhas[6] = String.format("%.0f" , (100 - porcentagemSincXAssinc));
                    linhas[7] = Double.toString(i.getCargaHoraria());
                    linhas[8] = "";
                    for(Atividade j : atividadesOrdenada){
                        if(j instanceof Trabalho || j instanceof Prova){
                            linhas[8] = linhas[8] + "   " + j.getData();
                        }
                    }

                    writer.writeNext(linhas);

                }
            }
        
        } catch (IOException e) { 
            System.out.println("ERRO DE I/O");
        }

    }

}