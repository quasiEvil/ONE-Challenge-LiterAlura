package br.com.alura.literalura.model;

public enum Language {

    PT("pt", "Português"),
    EN("en", "Inglês"),
    ES("es", "Espanhol"),
    IT("it", "Italiano");

    private String language;

    Language(String symbol, String language) {
        this.language = language;
    }
}