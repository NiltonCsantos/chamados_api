package com.api.chamados.utils;

public class Regex {
    public static String apenasNumeros(String texto) {
        if (texto == null) {
            return null;
        }
        return texto.replaceAll("[^0-9]", "");
    }
}
