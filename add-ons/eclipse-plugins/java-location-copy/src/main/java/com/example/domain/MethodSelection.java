package com.example.domain;

public class MethodSelection {
    private final String selectedText;
    private final int lineNumber;

    private MethodSelection(String selectedText, int lineNumber) {
        this.selectedText = selectedText;
        this.lineNumber = lineNumber;
    }

    public static MethodSelection of(String selectedText, int lineNumber) {
        return new MethodSelection(selectedText, lineNumber);
    }

    public String getSelectedText() {
        return selectedText;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}