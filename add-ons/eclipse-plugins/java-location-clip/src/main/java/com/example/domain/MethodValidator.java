package com.example.domain;

import com.example.InvalidSelectionException;

public class MethodValidator {
    public static void validate(MethodSelection context, String methodName) {
        if (!context.getSelectedText().equals(methodName)) {
            throw new InvalidSelectionException("メソッド名を選択してください。");
        }
    }
}
