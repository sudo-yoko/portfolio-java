package com.example.domain;

import com.example.ClipException;

public class MethodValidator {
    public static void validate(MethodContext context, String methodName) {
        if (!context.getSelectedText().equals(methodName)) {
            throw new ClipException("メソッド名を選択してください。");
        }
    }
}
