package com.example.domain;

public class TypeContext {
    private final String selectedText;

    private TypeContext(String selectedText) {
        this.selectedText = selectedText;
    }

    public static TypeContext of(String selectedText) {
        return new TypeContext(selectedText);
    }

    public String getSelectedText() {
        return selectedText;
    }
}