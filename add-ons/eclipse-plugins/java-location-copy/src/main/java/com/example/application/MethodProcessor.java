package com.example.application;

import org.eclipse.jdt.core.IMethod;

import com.example.domain.JavaLocationFormatter;
import com.example.domain.MethodLocation;
import com.example.domain.SelectionValidator;

public class MethodProcessor implements JavaLocationProcessor {
    private final IMethod method;
    private final String methodName;
    private final String selectedText;
    private final int lineNumber;

    public MethodProcessor(IMethod method, String selectedText, int lineNumber) {
        this.method = method;
        this.methodName = method.getElementName();
        this.selectedText = selectedText;
        this.lineNumber = lineNumber;
    }

    @Override
    public String buildClipText() {
        SelectionValidator.validate(this.selectedText, this.methodName);
        MethodLocation location = JavaLocationMapper.map(this.method, this.lineNumber);
        return JavaLocationFormatter.formatMethod(location);
    }
}
