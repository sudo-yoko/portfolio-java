package com.example.application;

import org.eclipse.jdt.core.IMethod;

import com.example.domain.JavaLocationFormatter;
import com.example.domain.MethodLocation;
import com.example.domain.MethodSelection;
import com.example.domain.MethodValidator;

public class MethodProcessor implements JavaLocationProcessor {
    private final MethodSelection context;
    private final IMethod method;
    private final String methodName;

    public MethodProcessor(IMethod method, MethodSelection context) {
        this.method = method;
        this.context = context;
        this.methodName = method.getElementName();
    }

    @Override
    public String buildClipText() {
        MethodValidator.validate(this.context, this.methodName);
        MethodLocation location = JavaLocationMapper.map(this.method, this.context);
        return JavaLocationFormatter.formatMethod(location);
    }
}
