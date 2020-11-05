package src.io;

import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import src.dominio.*;
import src.menus.Verificador;

public class Designacoes {
    
    private static final String DIGITAPERIODO = "Digite o periodo [Ano/Semestre]: ";
    private static final String DIGITACODIGO = "Digite o código da disciplina: ";
    private static final String DIGITAMATRICULA = "Digite a matricula: ";
    private static final String DIGITANOME = "Digite o nome completo: ";
    private static final String DIGITALOGIN = "Digite o login institucional: ";

    final Verificador verificador = new Verificador();

    Map<String, Periodo> periodos;
    Map<String, Docente> docentes;
    Map<Integer, Estudante> estudantes;
    Map<String, Disciplina> disciplinas;

    Scanner scanner;

    /* Getters e Setters da memória (Utilizáveis apenas ao I/O) */
    public void setDisciplinas(Map<String, Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public void setPeriodos(Map<String, Periodo> periodos) {
        this.periodos = periodos;
    }

    public void setDocentes(Map<String, Docente> docentes) {
        this.docentes = docentes;
    }

    public void setEstudantes(Map<Integer, Estudante> estudantes) {
        this.estudantes = estudantes;
    }

    public Map<String, Periodo> getPeriodos() {
        return this.periodos;
    }

    public Map<String, Docente> getDocentes() {
        return this.docentes;
    }

    public Map<String, Disciplina> getDisciplinas() {
        return this.disciplinas;
    }
    
    public Map<Integer, Estudante> getEstudantes() {
        return this.estudantes;
    }

    /* Construtor da classe */
    public Designacoes(Scanner scanner){
        this.scanner = scanner;
        periodos = new HashMap<>();
        docentes = new HashMap<>();
        estudantes = new HashMap<>();
        disciplinas = new HashMap<>();
    }

    /* Funções de cadastro */
    public void cadastraEstudante(){
        int matricula;
        String nome;
        
        System.out.println(DIGITAMATRICULA);
        String matriculaStr = scanner.nextLine();

        try{
            matricula = Integer.parseInt(matriculaStr);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
            return;
        }

        if(estudantes.get(matricula) != null){
            System.out.println("Cadastro repetido: " + matricula);
            return;
        }

        System.out.println(DIGITANOME);
        nome = scanner.nextLine();

        Estudante novoEstudante = new Estudante(matricula, nome);
        estudantes.put(matricula , novoEstudante);

    }

    public void cadastraPeriodo(){
        String entrada;

        System.out.println(DIGITAPERIODO);
        entrada = scanner.nextLine();

        String[] split = entrada.split(Pattern.quote("/"));

        int ano = Integer.parseInt(split[0]);
        String semestre = split[1].toUpperCase();

        if(semestre.length() != 1){
            System.out.println("Dado Inválido: (semestre) " + semestre);
            return;
        }

        Periodo periodo = new Periodo(ano, semestre);

        if(periodos.get(periodo.toString( )) != null){
            System.out.println("Cadastro repetido: " + periodo);
            return;
        }

        periodos.put(periodo.toString(), periodo);

    }

    public void cadastraDocente(){
        String login;
        String nome;
        String website;
        char escolha;
        
        System.out.println(DIGITALOGIN);
        login = scanner.nextLine();

        if(docentes.get(login) != null){
            System.out.println("Cadastro repetido: " + login);
            return;
        }

        System.out.println(DIGITANOME);
        nome = scanner.nextLine();

        System.out.println("Possui website? [S/N] : ");
        escolha = scanner.next().charAt(0);

        if(escolha == 's' || escolha == 'S'){
            System.out.println("Digite o website: ");
            scanner.nextLine();
            website = scanner.nextLine();
        }else{
            website = "NA";
        }

        Docente novoDocente = new Docente(login, nome, website);
        docentes.put(login, novoDocente);

    }

    public void cadastraDisciplina(){
        String codigo;
        String nome;

        System.out.println(DIGITACODIGO);
        codigo = scanner.nextLine().toUpperCase();
        
        System.out.println("Digite o nome da disciplina: ");
        nome = scanner.nextLine();

        System.out.println(DIGITAPERIODO);
        String strPeriodo = scanner.nextLine().toUpperCase();

        String[] split = strPeriodo.split(Pattern.quote("/"));

        String semestre = split[1].toUpperCase();

        if(semestre.length() != 1){
            System.out.println("Dado Inválido: (semestre) " + semestre);
            return;
        }

        if(disciplinas.get(codigo + "-" + strPeriodo) != null){
            System.out.println("Cadastro repetido: " + codigo + "-" + strPeriodo);
            return;
        }
 
        if (periodos.get(strPeriodo) == null){
        System.out.println("Referência inválida: " + strPeriodo);
        return;
        }

        Periodo periodo = periodos.get(strPeriodo);

        System.out.println("Digite o login institucional do docente responsável: ");
        String strProfessor = scanner.nextLine();

        if (docentes.get(strProfessor) == null){
            System.out.println("Referência inválida: " + strProfessor);
            return;
        }

        Docente professor = docentes.get(strProfessor);

        Disciplina disciplina = new Disciplina(codigo, nome, periodo, professor);
        disciplinas.put(disciplina.toString(), disciplina);
        
        professor.addDisciplina(disciplina);
    }

    public void cadastraAlunoEmDisciplina(){
        System.out.println(DIGITAMATRICULA);
        String matriculaStr = scanner.nextLine();
        int matricula;
        
        try{
            matricula = Integer.parseInt(matriculaStr);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
            return;
        }

        if (estudantes.get(matricula) == null){
            System.out.println("Referência inválida: " + matricula);
            return;
        }

        System.out.println(DIGITACODIGO);
        scanner.nextLine();
        String codigo = scanner.nextLine().toUpperCase();

        System.out.println(DIGITAPERIODO);
        String periodo = scanner.nextLine().toUpperCase();

        String[] split = periodo.split(Pattern.quote("/"));

        String semestre = split[1].toUpperCase();

        if(semestre.length() != 1){
            System.out.println("Dado Inválido: (semestre) " + semestre);
            return;
        }

        if (disciplinas.get(codigo + "-" + periodo) == null){
            System.out.println("Referência inválida: " + codigo + "-" + periodo);
            return;
        }

        if (disciplinas.get(codigo + "-" + periodo).getAlunos().get(matricula) != null){
            System.out.println("Matricula repetida: " + matricula + " em " + codigo + "-" + periodo);
            return;
        }

        disciplinas.get(codigo + "-" + periodo).getAlunos().put(matricula, estudantes.get(matricula));
        estudantes.get(matricula).addDisciplina(disciplinas.get(codigo + "-" + periodo));
    }

    public void cadastraAtividadeEmDisciplina(){
        System.out.println(DIGITACODIGO);
        String codigo = scanner.nextLine().toUpperCase();

        System.out.println(DIGITAPERIODO);
        String periodo = scanner.nextLine().toUpperCase();

        String[] split = periodo.split(Pattern.quote("/"));

        String semestre = split[1].toUpperCase();

        if(semestre.length() != 1){
            System.out.println("Dado Inválido: (semestre) " + semestre);
            return;
        }

        if (disciplinas.get(codigo + "-" + periodo) == null){
            System.out.println("Referência inválida: " + codigo + "-" + periodo);
            return;
        }

        int i;
        do {
            
            System.out.println("Qual tipo de atividade? ");
            System.out.println("1 - Prova ");
            System.out.println("2 - Trabalho ");
            System.out.println("3 - Estudo ");
            System.out.println("4 - Aula ");

            String escolha = scanner.nextLine();

            i = verificador.verificaInt(escolha);

            switch (i){
                case 1:
                    criaProva(codigo, periodo);
                    break;
                case 2:
                    criaTrabalho(codigo, periodo);
                    break;
                case 3:
                    criaEstudo(codigo, periodo);
                    break;
                case 4:
                    criaAula(codigo, periodo);
                    break;
                default:
            }
        } while (i!= 5);

    }

    public void cadastraNotaEmAtividade(){
        System.out.println(DIGITACODIGO);
        String codigo = scanner.nextLine().toUpperCase();

        System.out.println(DIGITAPERIODO);
        String periodo = scanner.nextLine().toUpperCase();

        String[] split = periodo.split(Pattern.quote("/"));

        String semestre = split[1].toUpperCase();

        if(semestre.length() != 1){
            System.out.println("Dado Inválido: (semestre) " + semestre);
            return;
        }

        if (disciplinas.get(codigo + "-" + periodo) == null){
            System.out.println("Referência inválida: " + codigo + "-" + periodo);
            return;
        }

        Disciplina disciplina = disciplinas.get(codigo + "-" + periodo);

        System.out.println("Digite o número da atividade: ");
        String numero = scanner.nextLine();

        int nAtividade;
        try{
            nAtividade = Integer.parseInt(numero);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
            return;
        }

        if (disciplina.getAtividades().get(nAtividade) == null){
            System.out.println("Referência inválida a atividade de número: " + nAtividade);
            return;
        }

        System.out.println(DIGITAMATRICULA);
        String matriculaStr = scanner.nextLine();

        int matricula;
        try{
            matricula = Integer.parseInt(matriculaStr);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
            return;
        }

        if (estudantes.get(matricula) == null){
            System.out.println("Referência inválida: " + matricula);
            return;
        }

        if ( disciplina.getAtividades().get(nAtividade).getNotas().get(matricula) != null ){
            System.out.println("Avaliação repetida: estudante " + matricula + " para atividade " + nAtividade + " de " + disciplina);
        }

        System.out.println("Digite a nota do aluno: ");
        double nota;
        try{
            nota = scanner.nextDouble();
            scanner.nextLine();
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
            return;
        }

        Nota novaNota = new Nota(estudantes.get(matricula), nota);
        estudantes.get(matricula).incrementaAvaliacoes();
        estudantes.get(matricula).incrementaTotalAvaliacoes(nota);

        disciplina.getAtividades().get(nAtividade).addNotas(novaNota.getMatricula() ,novaNota);
    }

    /* Funções criadoras de subtipos de atividades  */
    private void criaProva(String codigo, String periodo){
        System.out.println("Digite o nome da atividade: ");
        String nome = scanner.nextLine();

        System.out.println("Digite a data e horário da prova [dd-MM-yyyy HH:mm]: ");
        String strData = scanner.nextLine();

        LocalDateTime data;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            data = LocalDateTime.parse(strData, formatter);
        }
        catch(Exception e){
            System.out.println("Dado invalido: " + e);
            return;
        }

        System.out.println("Digite o conteudo da prova: ");
        String conteudo = scanner.nextLine();

        Disciplina disciplina = disciplinas.get(codigo + "-" + periodo);
        Atividade atividade = new Prova(disciplina.getAtividades().size() + 1, nome, data, conteudo);
        disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
    }

    private void criaTrabalho(String codigo, String periodo){
        System.out.println("Digite o nome da atividade: ");
        String nome = scanner.nextLine();

        System.out.println("Digite o prazo final [dd-MM-yyyy HH:mm]: ");
        String strData = scanner.nextLine();

        LocalDateTime data;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            data = LocalDateTime.parse(strData, formatter);
        }
        catch(Exception e){
            System.out.println("Dado invalido: " + e);
            return;
        }

        System.out.println("Numero maximo de alunos por grupo: ");
        int nAlunos = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Carga horária esperada: ");
        double cargaHoraria;
        try{
            cargaHoraria = scanner.nextDouble();
            scanner.nextLine();
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage().replaceFirst("For input string: ", "Dado inválido: "));
            return;
        }

        Disciplina disciplina = disciplinas.get(codigo + "-" + periodo);
        Atividade atividade = new Trabalho(disciplina.getAtividades().size() + 1, nome, data, nAlunos, cargaHoraria);
        disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
    }

    private void criaEstudo(String codigo, String periodo){
        System.out.println("Digite o nome do estudo: ");
        String nome = scanner.nextLine();

        Map<String, String> materiais = new HashMap<>();

        System.out.println("Quantos materiais serão registrados? ");
        int i = scanner.nextInt();
        scanner.nextLine();

        for(int j = 1; j <= i ; j++ ){
            System.out.println("Digite o nome do material: ");
            String materialNome = scanner.nextLine();

            System.out.println("Digite o URL do material: ");
            String materialUrl = scanner.nextLine();

            materiais.put(materialNome, materialUrl);           
        }

        Disciplina disciplina = disciplinas.get(codigo + "-" + periodo);
        Atividade atividade = new Estudo(disciplina.getAtividades().size() + 1, nome, materiais);
        disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
    }

    private void criaAula(String codigo, String periodo){
        System.out.println("Digite o nome da aula: ");
        String nome = scanner.nextLine();

        System.out.println("Digite a data e horário da aula [dd-MM-yyyy HH:mm]: ");
        String strData = scanner.nextLine();

        LocalDateTime data;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            data = LocalDateTime.parse(strData, formatter);
        }
        catch(Exception e){
            System.out.println("Dado invalido: " + e);
            return;
        }

        Disciplina disciplina = disciplinas.get(codigo + "-" + periodo);
        Atividade atividade = new Aula(disciplina.getAtividades().size() + 1, nome, data);
        disciplina.addAtividades(disciplina.getAtividades().size() + 1, atividade);
    }

    /* Relatórios */
    public void visaoGeralPeriodo(){
        System.out.println(DIGITAPERIODO);
        scanner.nextLine();
        String entrada = scanner.nextLine().toUpperCase();

        String[] split = entrada.split(Pattern.quote("/"));

        String semestre = split[1].toUpperCase();

        if(semestre.length() != 1){
            System.out.println("Dado Inválido: (semestre) " + semestre);
            return;
        }

        if (periodos.get(entrada) == null){
            System.out.println("Referência inválida: " + entrada);
            return;
        }

        List<Disciplina> listaOrdenada = new ArrayList<>();

        System.out.println("Disciplinas cadastradas: ");

        /* Pega as disciplinas no período informado e coloca em uma lista */
        
        for(Map.Entry<String, Disciplina> j : disciplinas.entrySet()){

            if (j.getValue().getPeriodo().toString().equals(entrada)) listaOrdenada.add(j.getValue());

        }
    

        /* Ordena a lista em ordem crescente de nome da disciplina */
        Collections.sort(listaOrdenada, (d1, d2) ->   d1.getNome().compareTo(d2.getNome()));

        for(Disciplina j : listaOrdenada){
            System.out.println("Disciplina: " + j.getCodigo() + " : " + j.getNome());
            System.out.println("Docente: " + j.getProfessor().getNome() + " : " + j.getProfessor().getLogin() + "@ufes.br");
            System.out.println("Numero de alunos matriculados: " + (j.getAlunos().size() + 1) );
            System.out.println("Número de atividades propostas: " + (j.getAtividades().size() + 1 ) );
            System.out.println();
        }
    }

    public void estatisticaDocentes(){
        List<Docente> listaOrdenada = new ArrayList<>();

        /* Cria a lista de docentes */
        for( Map.Entry<String , Docente> i : docentes.entrySet()){
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

            /* Percorre disciplinas procurando pelo docente responsável */
            for(Map.Entry<String, Disciplina> j : disciplinas.entrySet()){

                /* Encontrado docente */
                if(j.getValue().getProfessor().getNome().equals(i.getNome())) { 

                    periodos.add(j.getValue().getPeriodo());
                    numeroDisciplinas++; 
                    numeroAtividades += j.getValue().getAtividades().size();

                    for(Map.Entry<Integer, Atividade> k : j.getValue().getAtividades().entrySet()){

                        if (k.getValue().isSincrona()) atvSincronas++;
                        else atvAssincronas++;

                        nNotas = k.getValue().getNotas().size();
                        for(Map.Entry<Integer, Nota> l : k.getValue().getNotas().entrySet()){
                            totalNotas += l.getValue().getNotaDoAluno();
                        }
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

            System.out.println("Nome: " + i.getNome());
            System.out.println("Numero de disciplinas: " + numeroDisciplinas);
            System.out.println("Numero de períodos diferentes: " + periodos.size());
            System.out.println("Media de atividades por disciplina: " + mediaAtivXDisciplina);
            System.out.println("Percentual de atividades Síncronas x Assíncronas: " + percentualSincXAssinc);
            System.out.println("Média de notas em atividades: " + mediaNotas);
            System.out.println();
        }


    }

    public void estatisticaEstudante(){
        List<Estudante> listaOrdenada = new ArrayList<>();

        for(Map.Entry<Integer, Estudante> i : estudantes.entrySet()){

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

            System.out.println("Matricula: " + i.getMatricula());
            System.out.println("Nome: " + i.getNome());
            System.out.println("Media de disciplinas por período: " + nDisciplinas/periodos.size());
            if (nDisciplinas!=0) System.out.println("Media de avaliações por disciplina: " + i.getNAvaliacoes()/nDisciplinas );
            System.out.println("Media de notas: " + i.getTotalAvaliacoes()/i.getNAvaliacoes() );
            System.out.println();
        }

    }

    public void estatisticaDisciplinasDocente(){

        scanner.nextLine();
        System.out.println("Digite o login de um docente: ");
        String login = scanner.nextLine();

        if (docentes.get(login) == null){
            System.out.println("Referência inválida: " + login);
            return;
        }

        Docente professor = docentes.get(login);
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

            System.out.println("Período acadêmico: " + i.getPeriodo());
            System.out.println("Código: " + i.getCodigo());
            System.out.println("Nome: " + i.getNome());
            System.out.println("Número de atividades propostas: " + i.getAtividades().size());
            System.out.println("Porcentagem de atividades síncronas: " + porcentagemSincXAssinc + "%");
            System.out.println("Carga horaria total: " + i.getCargaHoraria());

            for(Atividade j : atividadesOrdenada){
                System.out.println("Nome da atividade: " + j.getNome());
                if (j.getData() == null){System.out.println("Atividade sem data");}
                else System.out.println("Data: "+ j.getData().toString());
            }
            System.out.println();
        }
    }

}
