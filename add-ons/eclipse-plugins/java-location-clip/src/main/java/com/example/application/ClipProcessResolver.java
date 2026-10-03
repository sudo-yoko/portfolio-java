package com.example.application;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;

import com.example.ClipException;
import com.example.domain.MethodContext;
import com.example.domain.SelectionValidator;

public class ClipProcessResolver {
    public static ClipProcessor resolve(ICompilationUnit cu, ISelection selection) throws JavaModelException {

        // 選択されたテキスト
        ITextSelection textSelection = (ITextSelection) selection;
        String selectedText = textSelection.getText();
        String trimmedText = selectedText == null ? "" : selectedText.trim();
        SelectionValidator.validate(trimmedText);

        // カーソル位置の Java 要素を取得
        IJavaElement selectedElement = cu.getElementAt(textSelection.getOffset());
        if (selectedElement == null) {
            return null;
        }

        // 処理クラスの選択
        if (selectedElement instanceof IType type) {
            return new TypeProcessor(type);
        }
        if (selectedElement instanceof IMethod method) {
            // String selectedText = textSelection.getText();
            // String trimmedText = selectedText == null ? "" : selectedText.trim();
            int lineNumber = textSelection.getStartLine();
            MethodContext context = MethodContext.of(trimmedText, lineNumber);
            return new MethodProcessor(method, context);
        }
        String elementName = (selectedElement == null) ? "なし" : selectedElement.getElementName();
        throw new ClipException(String.format(
                "選択された要素（%s）は対象外です。クラス名またはメソッド名を選択してください。",
                elementName));
    }
}
