package br.com.alura.literalura.model;

import lombok.Getter;

@Getter
public enum Language {
    PT("pt", "Português"),
    EN("en", "Inglês"),
    ES("es", "Espanhol"),
    UNKNOWN("unknown", "Desconhecido");

    private final String code;
    private final String displayName;

    Language(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Language fromCode(String code) {
        for (Language language : Language.values()) {
            if (language.getCode().equalsIgnoreCase(code)) {
                return language;
            }
        }
        return UNKNOWN;
    }
}
