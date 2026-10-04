package com.example.domain;

import com.example.InvalidSelectionException;

public class SelectionValidator {
    public static void validate(String selectedText) {
        if (selectedText.length() == 0) {
            throw new InvalidSelectionException("要素を範囲選択（ハイライト）してください。");
        }
    }
}
