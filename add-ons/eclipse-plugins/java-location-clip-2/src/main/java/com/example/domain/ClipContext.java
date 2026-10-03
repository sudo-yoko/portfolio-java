package main.java.com.example.domain;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.text.ITextSelection;

public class ClipContext {
    private final ITextSelection textSelection;
    private final String selectedText;
    private final IJavaElement element;

    private ClipContext(IJavaElement element, ITextSelection textSelection, String selectedText) {
        this.element = element;
        this.textSelection = textSelection;
        this.selectedText = selectedText;
    }

    public static ClipContext of(IJavaElement element, ITextSelection textSelection) {
        if (textSelection == null) {
            throw new IllegalStateException("textSelectionがnullです。");
        }
        String selectedText = textSelection.getText();
        String trimmedText = selectedText == null ? "" : selectedText.trim();
        return new ClipContext(element, textSelection, trimmedText);
    }

    public ITextSelection getTextSelection() {
        return this.textSelection;
    }

    public IJavaElement getElement() {
        return this.element;
    }

    public String getSelectedText() {
        return this.selectedText;
    }
}