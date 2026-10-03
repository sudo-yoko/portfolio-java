package com.example.domain;

public class MethodContext {
    private final String selectedText;
    private final int lineNumber;

    private MethodContext(String selectedText, int lineNumber) {
        this.selectedText = selectedText;
        this.lineNumber = lineNumber;
    }

    public static MethodContext of(String selectedText, int lineNumber) {
        return new MethodContext(selectedText, lineNumber);
    }

    public String getSelectedText() {
        return selectedText;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}