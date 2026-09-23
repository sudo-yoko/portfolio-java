
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class JavaLocationCopyHandler extends AbstractHandler {
	private static final String TITLE = "メソッド情報をクリップボードにコピー";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (editor == null || !(selection instanceof ITextSelection)) {
			return null;
		}
		ITextSelection textSelection = (ITextSelection) selection;

		// 範囲選択されていない場合はエラー
		if (textSelection.getLength() == 0) {
			MessageDialog.openError(editor.getSite().getShell(), TITLE, "メソッド名を範囲選択（ハイライト）してから実行してください。");
			return null;
		}

		IJavaElement element = JavaUI.getEditorInputJavaElement(editor.getEditorInput());
		if (!(element instanceof ICompilationUnit)) {
			return null;
		}

		ICompilationUnit cu = (ICompilationUnit) element;
		try {
			// ASTの同期（連続実行・編集直後の誤作動防止）
			if (cu.isWorkingCopy()) {
				cu.reconcile(ICompilationUnit.NO_AST, false, null, null);
			}

			// カーソル位置の Java 要素を取得
			IJavaElement selectedElement = cu.getElementAt(textSelection.getOffset());
			if (!(selectedElement instanceof IMethod)) {
				MessageDialog.openError(editor.getSite().getShell(), TITLE, "メソッド名を選択してください。");
				return null;
			}

			// メソッド名
			IMethod method = (IMethod) selectedElement;
			String methodName = method.getElementName();
			// メソッド名を選択していない場合はエラー
			String selectedText = textSelection.getText().trim();
			if (!selectedText.equals(methodName)) {
				MessageDialog.openError(editor.getSite().getShell(), TITLE, "メソッド名を選択してください。");
				return null;
			}

			IType declaringType = method.getDeclaringType();
			if (declaringType == null) {
				MessageDialog.openError(editor.getSite().getShell(), TITLE, "クラス情報の取得に失敗しました。");
				return null;
			}

			// プロジェクト名
			String projectName = method.getJavaProject().getElementName();
			// パッケージ名
			String packageName = declaringType.getPackageFragment().getElementName();
			// クラス名。内部クラスの場合は、親クラスを含めて$で区切って取得する
			String className = declaringType.getTypeQualifiedName('$');
			// 行位置
			int lineNumber = textSelection.getStartLine() + 1;

			// クリップボードにコピー
			String clip = String.format("%s\n%s\n%s\nL%d: %s",
					projectName, packageName, className, lineNumber, methodName);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(clip), null);
			MessageDialog.openInformation(editor.getSite().getShell(), TITLE, "クリップボードにコピーしました\n" + clip);

		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(editor.getSite().getShell(), TITLE, "処理中に例外が発生しました: " + e.getMessage());
		}
		return null;
	}
}
