package com.example.domain;

import com.example.InvalidSelectionException;

/**
 * 選択内容のバリデーション
 */
public class SelectionValidator {
    public static void validate(String selectedText) {
        if (selectedText.length() == 0) {
            throw new InvalidSelectionException("要素を範囲選択（ハイライト）してください。");
        }
    }

    public static void validate(String selectedText, String javaElement) {
        if (!selectedText.equals(javaElement)) {
            throw new InvalidSelectionException("要素を範囲選択（ハイライト）してください。");
        }
    }

    // public static void validate(TypeSelection selection, String typeName) {
    // if (!isValid(selection.getSelectedText(), typeName)) {
    // throw new InvalidSelectionException("型名を範囲選択（ハイライト）してください。");
    // }
    // }

    // public static void validate(MethodSelection selection, String methodName) {
    // if (!isValid(selection.getSelectedText(), methodName)) {
    // throw new InvalidSelectionException("メソッド名を範囲選択（ハイライト）してください。");
    // }
    // }

    // private static boolean isValid(String actual, String expected) {
    // return actual.equals(expected);
    // }
}
