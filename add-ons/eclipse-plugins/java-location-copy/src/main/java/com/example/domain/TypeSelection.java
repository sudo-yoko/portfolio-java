package com.example.domain;

public class TypeSelection {
    private final String selectedText;

    private TypeSelection(String selectedText) {
        this.selectedText = selectedText;
    }

    public static TypeSelection of(String selectedText) {
        return new TypeSelection(selectedText);
    }

    public String getSelectedText() {
        return selectedText;
    }
}