package src.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
        boolean writeOnly = false;
        boolean readOnly = false;

        //Joga os args para uma lista de strings
        List<String> argumentos = new ArrayList<>(Arrays.asList(args));

        if(argumentos.contains("--read-only")) readOnly = true ;
        if(argumentos.contains("--write-only")) writeOnly = true ;

        if( readOnly && writeOnly) {
            System.out.print("Read Only e Write Only encontrados, impossível continuar.");
            return;
        }

        /* Caso algum dos métodos argumentos retornem false o programa retorna ao main e interrompe a continuidade */
        if(!writeOnly){
            if(argumentos.contains("-p") && ! argP(args[argumentos.indexOf("-p")+1])) return;
            if(argumentos.contains("-d") && ! argD(args[argumentos.indexOf("-d")+1])) return;
            if(argumentos.contains("-o") && ! argO(args[argumentos.indexOf("-o")+1])) return;
            if(argumentos.contains("-e") && ! argE(args[argumentos.indexOf("-e")+1])) return;
            if(argumentos.contains("-m") && ! argM(args[argumentos.indexOf("-m")+1])) return;
            if(argumentos.contains("-a") && ! argA(args[argumentos.indexOf("-a")+1])) return;
            if(argumentos.contains("-n") && ! argN(args[argumentos.indexOf("-n")+1])) return;
        } else {
            LeitorEscritor dados = new LeitorEscritor(memoria);
            try{
                dados.lerDoDisco();
            } catch(Exception e){
                System.out.print("Erro de I/O.");
                return;
            }
            
        }

        if(!readOnly){
            if(! visaoGeralPeriodo()) return;
            if(! estatisticaDocentes()) return;
            if(! estatisticaEstudante()) return;
            if(! estatisticaDisciplinasDocente()) return;
        } else {
            LeitorEscritor dados = new LeitorEscritor(memoria);
            try{
                dados.escreverEmDisco();
            } catch(Exception e){
                System.out.print("Erro de I/O.");
                return;
            }
            
        }

        if (argumentos.contains("-menu")) {
            MenuPrincipal menu = new MenuPrincipal(scanner, memoria);
            try {
                menu.menu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private boolean argP(String filename) {

        /* ARMAZENA PERIODOS */
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {

                
                int ano = Integer.parseInt(linhas[0].trim());
                String semestre = linhas[1].toUpperCase().trim();

                if (semestre.length() != 1) {
                    System.out.print("Dado Inválido: " + linhas[1] + ".");
                    return false;
                }

                Periodo periodo = new Periodo(ano, semestre);

                if (memoria.periodos.get(periodo.toString()) != null) {
                    System.out.print("Cadastro repetido: " + periodo + ".");
                    return false;
                }

                memoria.periodos.put(periodo.toString(), periodo);

            }

        } catch (IOException | CsvValidationException e) {
            System.out.print("Erro de I/O.");
            return false;
        } catch (NumberFormatException e) {
            System.out.print(e.getLocalizedMessage().replaceFirst("For input string: \"", "Dado inválido: ").replace("\"", ".") );
            return false;
        }

        return true;
    }

    private boolean argD(String filename){
        /* ARMAZENA DOCENTES */
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {

                String login = linhas[0].trim();
                String nome = linhas[1].trim();
                String website = linhas[2].trim();

                if(memoria.getDocentes().get(login) != null){
                    System.out.print("Cadastro repetido: " + login+ ".");
                    return false;
                }
        
                Docente novoDocente = new Docente(login, nome, website);
                memoria.docentes.put(login, novoDocente);
            
            }

        } catch (IOException | CsvValidationException e) {
            System.out.print("Erro de I/O.");
            return false;
        }

        return true;
    }

    private boolean argE(String filename){
        /* ARMAZENA ESTUDANTES */
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {
                
                BigInteger matricula = new BigInteger(linhas[0].trim());
                String nome = linhas[1].trim();      

                if(memoria.estudantes.get(matricula) != null){
                    System.out.print("Cadastro repetido: " + matricula+ ".");
                    return false;
                }

                Estudante novoEstudante = new Estudante(matricula, nome);
                memoria.estudantes.put(matricula , novoEstudante);
            
            }

        } catch (IOException | CsvValidationException e) {
            System.out.print("Erro de I/O.");
            return false;
        } catch(IllegalArgumentException e){
            System.out.print(e.getLocalizedMessage().replaceFirst("For input string: \"", "Dado inválido: ").replace("\"", ".") );
            return false;
        }

        return true;
    }

    private boolean argO(String filename){
        /* ARMAZENA DISCIPLINAS */
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {

                String strPeriodo = linhas[0].trim();
                String codigo = linhas[1].trim();
                String nome = linhas[2].trim();
                String strProfessor = linhas[3].trim();

                String[] split = strPeriodo.split(Pattern.quote("/"));

                String semestre = split[1].toUpperCase();

                if(semestre.length() != 1){
                    System.out.print("Dado Inválido: " + semestre + ".");
                    return false;
                }

                if(memoria.disciplinas.get(codigo + "-" + strPeriodo) != null){
                    System.out.print("Cadastro repetido: " + codigo + "-" + strPeriodo + ".");
                    return false;
                }
        
                if (memoria.periodos.get(strPeriodo) == null){
                    System.out.print("Referência inválida: " + strPeriodo + ".");
                    return false;
                }

                Periodo periodo = memoria.periodos.get(strPeriodo);

                if (memoria.docentes.get(strProfessor) == null){
                    System.out.print("Referência inválida: " + strProfessor + ".");
                    return false;
                }

                Docente professor = memoria.docentes.get(strProfessor);

                Disciplina disciplina = new Disciplina(codigo, nome, periodo, professor);
                memoria.disciplinas.put(disciplina.toString(), disciplina);
                
                professor.addDisciplina(disciplina);
            
            }

        } catch (IOException | CsvValidationException e) {
            System.out.print("Erro de I/O.");
            return false;
        }

        return true;
    }

    private boolean argM(String filename){
        /* RELACIONA ESTUDANTES A DISCIPLINAS */
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {

                String codigo = linhas[0].trim();
                String matriculaStr = linhas[1].trim();
                BigInteger matricula = new BigInteger(matriculaStr);
                
                if (memoria.estudantes.get(matricula) == null){
                    System.out.print("Referência inválida: " + matricula + ".");
                    return false;
                }

                String periodo = codigo.split(Pattern.quote("-"))[1];
                String semestre = periodo.split(Pattern.quote("/"))[1];
                codigo = codigo.split(Pattern.quote("-"))[0];

                if(semestre.length() != 1){
                    System.out.print("Dado Inválido: " + semestre + ".");
                    return false;
                }

                if (memoria.disciplinas.get(codigo + "-" + periodo) == null){
                    System.out.print("Referência inválida: " + codigo + "-" + periodo + ".");
                    return false;
                }

                if (memoria.disciplinas.get(codigo + "-" + periodo).getAlunos().get(matricula) != null){
                    System.out.print("Matrícula repetida: " + matricula + " em " + codigo + "-" + periodo + ".");
                    return false;
                }

                memoria.disciplinas.get(codigo + "-" + periodo).getAlunos().put(matricula, memoria.estudantes.get(matricula));
                memoria.estudantes.get(matricula).addDisciplina(memoria.disciplinas.get(codigo + "-" + periodo));
                    
            }

        } catch (IOException | CsvValidationException e) {
            System.out.print("Erro de I/O.");
            return false;
        } catch(IllegalArgumentException e){
            System.out.print(e.getLocalizedMessage().replaceFirst("For input string: \"", "Dado inválido: ").replace("\"", ".") );
            return false;
        }

        return true;
    }

    private boolean argA(String filename){
        /* ARMAZENA ATIVIDADES DE DISCIPLINAS */
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {

                String codigo = linhas[0].split(Pattern.quote("-"))[0].trim();
                String periodo = linhas[0].split(Pattern.quote("-"))[1].trim();

                String nome = linhas[1].trim();

                String semestre = periodo.split(Pattern.quote("/"))[1].toUpperCase();

                if(semestre.length() != 1){
                    System.out.print("Dado Inválido: " + semestre + ".");
                    return false;
                }

                if (memoria.disciplinas.get(codigo + "-" + periodo) == null){
                    System.out.print("Referência inválida: " + codigo + "-" + periodo + ".");
                    return false;
                }

                //Verifica tipo de atividade
                String tipo = linhas[2].toUpperCase().trim();

                if (tipo.equals("A")){
                    String dataStr = linhas[3].trim() + " " + linhas[4].trim();

                    LocalDateTime data;
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                    try{
                        data = LocalDateTime.parse(dataStr, formatter);
                    } catch (Exception e){
                        System.out.print("Dado inválido: " + dataStr + ".");
                        return false;
                    }

                    Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo);
                    Atividade atividade = new Aula(disciplina.getAtividades().size() + 1, nome, data);
                    disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
                }

                else if (tipo.equals("T")){

                    String dataStr = linhas[3].trim() + " 00:00";

                    LocalDateTime data;
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    try{
                        data = LocalDateTime.parse(dataStr, formatter);
                    } catch (Exception e){
                        System.out.print("Dado inválido: " + linhas[3] + ".");
                        return false;
                    }

                    String strNAlunos = linhas[6].trim();
                    int nAlunos = Integer.parseInt(strNAlunos);

                    String strCargaHoraria = linhas[7].trim();
                    double cargaHoraria = Double.parseDouble(strCargaHoraria);

                    Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo);
                    Atividade atividade = new Trabalho(disciplina.getAtividades().size() + 1, nome, data, nAlunos, cargaHoraria);
                    disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
                }

                else if (tipo.equals("P")){
                    String dataStr = linhas[3].trim() + " " + linhas[4].trim();

                    LocalDateTime data;
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    try{
                        data = LocalDateTime.parse(dataStr, formatter);
                    } catch (Exception e){
                        System.out.print("Dado inválido: " + dataStr + ".");
                        return false;
                    }

                    String conteudo = linhas[5];

                    Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo);
                    Atividade atividade = new Prova(disciplina.getAtividades().size() + 1, nome, data, conteudo);
                    disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
                }

                else if (tipo.equals("E")){
                    Map<String, String> materiais = new HashMap<>();
                    int nConteudos= 1;

                    List<String> materiaisStr = new ArrayList<>();

                    try{
                        materiaisStr = Arrays.asList(linhas[5].split(Pattern.quote(")"))); 
                        nConteudos = materiaisStr.size() / 2 ; 
                    } catch (Exception e) {
                        System.out.print("Erro de I/O.");
                        return false;
                    }
                    
                    for(int j = 0; j < nConteudos ; j++ ){
                        String materialNome = materiaisStr.get(j).split(Pattern.quote("]"))[0] + "]";
                        String materialUrl = materiaisStr.get(j).split(Pattern.quote("]"))[1] + ")";

                        materiais.put(materialNome, materialUrl);           
                    }

                    Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo);
                    Atividade atividade = new Estudo(disciplina.getAtividades().size() + 1, nome, materiais);
                    disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
                }

                else {
                    System.out.print("Dado invalido: Tipo nao especificado");
                    return false;
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.print("Erro de I/O.");
            return false;
        } catch(IllegalArgumentException e){
            System.out.print(e.getLocalizedMessage().replaceFirst("For input string: \"", "Dado inválido: ").replace("\"", ".") );
            return false;
        }

        return true;
    }

    private boolean argN(String filename){

        /* ATRIBUI AVALIAÇOES DE ATIVIDADES POR ALUNOS */
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();) {

            String[] linhas;
            // Pula a primeira linha
            reader.readNext();

            while ((linhas = reader.readNext()) != null) {
               
                String codigo = linhas[0].split(Pattern.quote("-"))[0].trim();
                String periodo = linhas[0].split(Pattern.quote("-"))[1].toUpperCase();
                String semestre = periodo.split(Pattern.quote("/"))[1];

                String matriculaStr = linhas[1].trim();
                BigInteger matricula = new BigInteger(matriculaStr);

                String numero = linhas[2].trim();
                int nAtividade = Integer.parseInt(numero);

                String notaStr = linhas[3].trim().replace(",", "."); 
                double nota = Double.parseDouble(notaStr);

                if(semestre.length() != 1){
                    System.out.print("Dado Inválido: " + semestre + ".");
                    return false;
                }

                if (memoria.disciplinas.get(codigo + "-" + periodo) == null){
                    System.out.print("Referência inválida: " + codigo + "-" + periodo + ".");
                    return false;
                }

                Disciplina disciplina = memoria.disciplinas.get(codigo + "-" + periodo); 
            
                if (disciplina.getAtividades().get(nAtividade) == null){
                    System.out.print("Referência inválida: " + nAtividade + ".");
                    return false;
                }
                
                if (memoria.estudantes.get(matricula) == null){
                    System.out.print("Referência inválida: " + matricula + ".");
                    return false;
                }

                if (disciplina.getAtividades().get(nAtividade).getNotas().get(matricula) != null ){
                    System.out.print("Avaliação repetida: estudante " + matricula + " para atividade " + nAtividade + " de " + disciplina + ".");
                    return false;
                }

                Nota novaNota = new Nota(memoria.estudantes.get(matricula), nota);
                memoria.estudantes.get(matricula).incrementaAvaliacoes();
                memoria.estudantes.get(matricula).incrementaTotalAvaliacoes(nota);

                disciplina.getAtividades().get(nAtividade).addNotas(novaNota.getMatricula() ,novaNota);     
            }
        } catch (IOException | CsvValidationException e) {
            System.out.print("Erro de I/O.");
            return false;
        } catch(IllegalArgumentException e){
            System.out.print(e.getLocalizedMessage().replaceFirst("For input string: \"", "Dado inválido: ").replace("\"", ".") );
            return false;
        }
        
        return true;
    }

    /* Relatórios */
    public boolean visaoGeralPeriodo(){
        File file = new File("1-visao-geral.csv");

        Collator collator = Collator.getInstance(Locale.getDefault());
        collator.setStrength(Collator.PRIMARY);

        try(FileWriter output = new FileWriter(file);
            CSVWriter writer = new CSVWriter(output, ';',
                                            com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER,
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
                if (c==0) c= d1.getSemestre().compareTo(d2.getSemestre());
                return c;
            });

            for(Periodo entrada: periodosOrdenados){
                List<Disciplina> listaOrdenada = new ArrayList<>();

                /* Pega as disciplinas no período informado e coloca em uma lista */
                
                for(Map.Entry<String, Disciplina> j : memoria.disciplinas.entrySet()){
                    if (j.getValue().getPeriodo().toString().equals(entrada.toString())) listaOrdenada.add(j.getValue());
                }
            
                /* Ordena a lista em ordem crescente de nome da disciplina */
                Collections.sort(listaOrdenada, (d1, d2) -> collator.compare(d1.getNome(), d2.getNome()));

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
            System.out.print("Erro de I/O.");
            return false;
        } 
        
        return true;
    }

    public boolean estatisticaDocentes(){
        
        File file = new File("2-docentes.csv");

        Collator collator = Collator.getInstance(Locale.getDefault());
        collator.setStrength(Collator.PRIMARY);

        try(FileWriter output = new FileWriter(file);
            CSVWriter writer = new CSVWriter(output, ';',
                                            com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_LINE_END);){
            
            /* Cria cabeçalho */ 
            String[] linhas = { "Docente", "Qtd. Disciplinas", "Qtd. Períodos", "Média Atividades/Disciplina", "% Síncronas", "% Assíncronas","Média de Notas"};
            writer.writeNext(linhas);
            
            List<Docente> listaOrdenada = new ArrayList<>();

            /* Cria a lista de docentes */
            for( Map.Entry<String , Docente> i : memoria.docentes.entrySet()){
                listaOrdenada.add(i.getValue());
            }

            /* Ordena a lista em ordem decrescente de nome */
            Collections.sort(listaOrdenada, (d1, d2) ->  collator.compare(d2.getNome(), d1.getNome()));

            for(Docente i : listaOrdenada){
                Set<Periodo> periodos = new HashSet<>();
                int numeroDisciplinas=0;
                int numeroAtividades=0;
                double totalNotas=0;
                int nNotas = 0;
                int atvSincronas=0;

                /* Percorre disciplinas do docente responsável */
                for(Disciplina j : i.getDisciplinas()){

                    periodos.add(j.getPeriodo());
                    numeroDisciplinas++; 
                    numeroAtividades += j.getAtividades().size();
                    atvSincronas += j.getnAtvSincronas();
                    
                    /* Percorre Atividades */
                    for(Map.Entry<Integer, Atividade> k : j.getAtividades().entrySet()){

                        nNotas += k.getValue().getNotas().size();
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
                if (numeroAtividades != 0){
                    percentualSincXAssinc = (double)atvSincronas/numeroAtividades * 100;
                }

                double mediaNotas=0;
                if (nNotas != 0){
                    mediaNotas = totalNotas/nNotas;
                }

                linhas[0] = i.getNome();
                linhas[1] = Integer.toString(numeroDisciplinas);
                linhas[2] = Integer.toString(periodos.size());
                linhas[3] = String.format("%.1f", mediaAtivXDisciplina);
                linhas[4] = String.format("%.0f%%", percentualSincXAssinc);
                if(numeroAtividades == 0){
                    linhas[5] = "0%";
                } else{
                    linhas[5] = String.format("%.0f%%", (100 - percentualSincXAssinc));
                }
                linhas[6] =  String.format("%.1f" , mediaNotas);
                writer.writeNext(linhas);

            }
        
        } catch (IOException e) { 
            System.out.print("Erro de I/O.");
            return false;
        }

        return true;
    }

    public boolean estatisticaEstudante(){

        File file = new File("3-estudantes.csv");

        Collator collator = Collator.getInstance();
        collator.setStrength(Collator.PRIMARY);

        try(FileWriter output = new FileWriter(file);
            CSVWriter writer = new CSVWriter(output, ';',
                                            com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_LINE_END);){
            String[] linhas = { "Matrícula", "Nome", "Média Disciplinas/Período", "Média Avaliações/Disciplina", "Média Notas Avaliações"};
            writer.writeNext(linhas);
        
            List<Estudante> listaOrdenada = new ArrayList<>();

            for(Map.Entry<BigInteger, Estudante> i : memoria.estudantes.entrySet()){

                listaOrdenada.add(i.getValue());

            }

            Collections.sort(listaOrdenada, (d1, d2) -> {
                int c = d2.getNAvaliacoes() - d1.getNAvaliacoes();
                if (c!=0) return c;
                else return collator.compare(d1.getNome(), d2.getNome());
            });

            for(Estudante i : listaOrdenada){

                linhas[0] = i.verMatricula().toString();
                linhas[1] = i.getNome();
                if(i.getNPeriodos() == 0){ linhas[2] = "0,0";}
                else linhas[2] = String.format("%.1f", (double)i.getDisciplinas().size()/i.getNPeriodos());
                if (!i.getDisciplinas().isEmpty()) linhas[3] = String.format("%.1f", (double)i.getNAvaliacoes()/i.getDisciplinas().size());
                else linhas[3] = "0,0";
                if(i.getNAvaliacoes() != 0) linhas[4] = String.format("%.1f", (float)i.getTotalAvaliacoes()/i.getNAvaliacoes() );
                else linhas[4] = "0,0";
                
                writer.writeNext(linhas);
            }
        
        } catch (IOException e) { 
            System.out.print("Erro de I/O.");
            return false;
        }

        return true;

    }

    public boolean estatisticaDisciplinasDocente(){

        File file = new File("4-disciplinas.csv");
        Collator collator = Collator.getInstance(Locale.getDefault());
        collator.setStrength(Collator.PRIMARY);

        try(FileWriter output = new FileWriter(file);
            CSVWriter writer = new CSVWriter(output, ';',
                                            com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                            com.opencsv.ICSVWriter.DEFAULT_LINE_END);){

            String[] linhas = { "Docente", "Período", "Código", "Nome", "Qtd. Atividades", "% Síncronas","% Assíncronas", "CH", "Datas Avaliações" };
            writer.writeNext(linhas);
        

            List<Disciplina> listaOrdenada = new ArrayList<>();
    
            /* Cria e ordena lista de disciplinas */
            for(Map.Entry<String, Disciplina> i : memoria.disciplinas.entrySet()){
                listaOrdenada.add(i.getValue());

            }
    
            Collections.sort(listaOrdenada, (d1, d2) -> {
                int c = collator.compare(d1.getPeriodo().toString(), d2.getPeriodo().toString()); 
                if (c==0) c=  collator.compare(d1.getCodigo(), d2.getCodigo());
                return c;
            });
    
            for (Disciplina i : listaOrdenada){

                List<Atividade> atividadesOrdenada = new ArrayList<>();
                /* Cria e ordena a lista de atividades */
                for(Map.Entry<Integer, Atividade> j : i.getAtividades().entrySet()){
                    atividadesOrdenada.add(j.getValue());
                }
    
                Collections.sort(atividadesOrdenada, (d1, d2) -> {
                    int c = 0;
                    if(d1.getLocalDateTime() != null && d2.getLocalDateTime() != null){
                        c = d1.getLocalDateTime().compareTo(d2.getLocalDateTime()); 
                    }
                    if (c!= 0) return c;
                    return d1.getNome().compareTo(d2.getNome()) ;
                });
                    
                double porcentagemSincXAssinc = 100 * (double)i.getnAtvSincronas()/(i.getAtividades().size());
    
                linhas[0] = i.getProfessor().getLogin();
                linhas[1] = i.getPeriodo().toString();
                linhas[2] = i.getCodigo();
                linhas[3] = i.getNome();
                linhas[4] = Integer.toString(i.getAtividades().size());
                if(i.getAtividades().isEmpty()) linhas[5] = "0%";
                else linhas[5] = String.format("%.0f%%" ,porcentagemSincXAssinc);
                if(i.getAtividades().isEmpty()) linhas[6] = "0%";
                else linhas[6] = String.format("%.0f%%" , (100 - porcentagemSincXAssinc));
                linhas[7] = String.format("%.0f", i.getCargaHoraria());
                linhas[8] = "";
                for(Atividade j : atividadesOrdenada){
                    if(j instanceof Trabalho || j instanceof Prova){
                        linhas[8] = linhas[8] + j.getData() + " ";
                    }
                }
                linhas[8] = linhas[8].trim();
                writer.writeNext(linhas);
            }
        
        } catch (IOException e) { 
            System.out.print("Erro de I/O.");
            return false;
        }

        return true;
    }

}