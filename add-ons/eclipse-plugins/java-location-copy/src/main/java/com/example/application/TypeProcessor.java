package com.example.application;

import org.eclipse.jdt.core.IType;

import com.example.domain.JavaLocationFormatter;
import com.example.domain.SelectionValidator;
import com.example.domain.TypeLocation;

public class TypeProcessor implements JavaLocationProcessor {
    private final IType type;
    private final String typeName;
    private final String selectedText;

    public TypeProcessor(IType type, String selectedText) {
        this.type = type;
        this.typeName = type.getElementName();
        this.selectedText = selectedText;
    }

    @Override
    public String buildClipText() {
        SelectionValidator.validate(this.selectedText, this.typeName);
        TypeLocation location = JavaLocationMapper.map(this.type);
        return JavaLocationFormatter.formatType(location);
    }
}
