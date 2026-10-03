package com.example.domain;

import com.example.ClipException;

public class TypeValidator {
    public static void validate(String selectedText, String typeName) {
        if (!selectedText.equals(typeName)) {
            throw new ClipException("クラス名を選択してください。");
        }
    }
}
