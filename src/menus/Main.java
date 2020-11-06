package src.menus;

import java.util.*;

import src.io.*;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("pt", "BR"));

        final Scanner scanner = new Scanner(System.in);
        final Designacoes designacoes = new Designacoes(scanner);

        final Argumentos argumentos = new Argumentos(designacoes, args, scanner);
        argumentos.checaArgumentos();

        scanner.close();
    }
}