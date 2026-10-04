package com.example.application;

import org.eclipse.jdt.core.IType;

import com.example.domain.JavaLocationFormatter;
import com.example.domain.TypeLocation;
import com.example.domain.TypeSelection;
import com.example.domain.TypeValidator;

public class TypeProcessor implements JavaLocationProcessor {
    private final IType type;
    private final TypeSelection context;
    private final String typeName;

    public TypeProcessor(IType type, TypeSelection context) {
        this.context = context;
        this.type = type;
        this.typeName = type.getElementName();
    }

    @Override
    public String buildClipText() {
        TypeValidator.validate(this.context, this.typeName);
        TypeLocation location = JavaLocationMapper.map(this.type);
        return JavaLocationFormatter.formatType(location);
    }
}
