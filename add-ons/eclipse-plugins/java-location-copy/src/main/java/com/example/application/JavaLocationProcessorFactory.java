package com.example.application;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;

import com.example.InvalidSelectionException;
import com.example.domain.MethodSelection;
import com.example.domain.SelectionValidator;
import com.example.domain.TypeSelection;

public class JavaLocationProcessorFactory {
    public static JavaLocationProcessor create(
            ICompilationUnit cu, ISelection selection) throws JavaModelException {

        // 選択されたテキスト
        ITextSelection textSelection = (ITextSelection) selection;
        String selectedText = textSelection.getText();
        String trimmedText = selectedText == null ? "" : selectedText.trim();
        SelectionValidator.validate(trimmedText);

        // カーソル位置の Java 要素を取得
        IJavaElement selectedElement = cu.getElementAt(textSelection.getOffset());
        if (selectedElement == null) {
            throw new InvalidSelectionException("選択位置のJava要素を取得できません。");
        }

        // 処理クラスの選択
        if (selectedElement instanceof IType type) {
            TypeSelection typeSelection = TypeSelection.of(trimmedText);
            return new TypeProcessor(type, typeSelection);
        }
        if (selectedElement instanceof IMethod method) {
            int lineNumber = textSelection.getStartLine() + 1;
            MethodSelection methodSelection = MethodSelection.of(trimmedText, lineNumber);
            return new MethodProcessor(method, methodSelection);
        }
        String elementName = (selectedElement == null) ? "なし" : selectedElement.getElementName();
        throw new InvalidSelectionException(String.format(
                "選択された要素（%s）は対象外です。型名またはメソッド名を選択してください。",
                elementName));
    }
}
