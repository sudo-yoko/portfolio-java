package com.example.domain;

import com.example.InvalidSelectionException;

public class TypeValidator {
    public static void validate(TypeSelection context, String typeName) {
        if (!context.getSelectedText().equals(typeName)) {
            throw new InvalidSelectionException("クラス名を選択してください。");
        }
    }
}
