package com.example.application;

import org.eclipse.jdt.core.IMethod;

import com.example.domain.JavaLocationFormatter;
import com.example.domain.MethodLocation;
import com.example.domain.MethodSelection;
import com.example.domain.SelectionValidator;

public class MethodProcessor implements JavaLocationProcessor {
    private final MethodSelection selection;
    private final IMethod method;
    private final String methodName;

    public MethodProcessor(IMethod method, MethodSelection selection) {
        this.method = method;
        this.selection = selection;
        this.methodName = method.getElementName();
    }

    @Override
    public String buildClipText() {
        SelectionValidator.validate(this.selection, this.methodName);
        MethodLocation location = JavaLocationMapper.map(this.method, this.selection);
        return JavaLocationFormatter.formatMethod(location);
    }
}
