package com.example.application;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.ITextSelection;

import com.example.InvalidSelectionException;
import com.example.domain.SelectionValidator;

public class JavaLocationProcessorFactory {
    public static JavaLocationProcessor create(
            ICompilationUnit cu, ITextSelection selection) throws JavaModelException {

        // 選択されたテキスト
        String selectedText = selection.getText();
        String trimmedText = selectedText == null ? "" : selectedText.trim();
        SelectionValidator.validate(trimmedText);

        // カーソル位置の Java 要素を取得
        IJavaElement selectedElement = cu.getElementAt(selection.getOffset());
        if (selectedElement == null) {
            throw new InvalidSelectionException("選択位置のJava要素を取得できません。");
        }

        // 処理クラスの選択
        if (selectedElement instanceof IType type) {
            return new TypeProcessor(type, trimmedText);
        }
        if (selectedElement instanceof IMethod method) {
            int lineNumber = selection.getStartLine() + 1;
            return new MethodProcessor(method, trimmedText, lineNumber);
        }
        String elementName = (selectedElement == null) ? "なし" : selectedElement.getElementName();
        throw new InvalidSelectionException(String.format(
                "選択された要素（%s）は対象外です。型名またはメソッド名を選択してください。",
                elementName));
    }
}
