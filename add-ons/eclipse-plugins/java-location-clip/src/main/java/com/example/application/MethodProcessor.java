package com.example.application;

import org.eclipse.jdt.core.IMethod;

import com.example.domain.Location;
import com.example.domain.LocationFormatter;
import com.example.domain.MethodContext;
import com.example.domain.MethodValidator;

public class MethodProcessor implements ClipProcessor {
    private final MethodContext context;
    private final IMethod method;
    private final String methodName;

    public MethodProcessor(IMethod method, MethodContext context) {
        this.method = method;
        this.context = context;
        this.methodName = method.getElementName();
    }

    @Override
    public String[] getJavaLocation() {
        MethodValidator.validate(this.context, this.methodName);
        Location location = LocationResolver.resolve(this.method, this.methodName, this.context);
        return LocationFormatter.asMethod(location);
    }
}
