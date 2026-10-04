package com.example.application;

import org.eclipse.jdt.core.IType;

import com.example.domain.JavaLocationFormatter;
import com.example.domain.SelectionValidator;
import com.example.domain.TypeLocation;
import com.example.domain.TypeSelection;

public class TypeProcessor implements JavaLocationProcessor {
    private final IType type;
    private final TypeSelection selection;
    private final String typeName;

    public TypeProcessor(IType type, TypeSelection selection) {
        this.selection = selection;
        this.type = type;
        this.typeName = type.getElementName();
    }

    @Override
    public String buildClipText() {
        SelectionValidator.validate(this.selection, this.typeName);
        TypeLocation location = JavaLocationMapper.map(this.type);
        return JavaLocationFormatter.formatType(location);
    }
}
