
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
	private static final String TITLE = "Javaの位置情報をクリップボードにコピー";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (editor == null) {
			return null;
		}
		try {
			ISelection selection = HandlerUtil.getCurrentSelection(event);
			if (!(selection instanceof ITextSelection)) {
				return null;
			}
			ITextSelection textSelection = (ITextSelection) selection;

			// 範囲選択されていない場合はエラー
			if (textSelection.getLength() == 0) {
				throw new CopyHandlerException("要素を範囲選択（ハイライト）してから実行してください。");
			}

			IJavaElement element = JavaUI.getEditorInputJavaElement(editor.getEditorInput());
			if (!(element instanceof ICompilationUnit)) {
				return null;
			}

			ICompilationUnit cu = (ICompilationUnit) element;

			// ASTの同期（連続実行・編集直後の誤作動防止）
			if (cu.isWorkingCopy()) {
				cu.reconcile(ICompilationUnit.NO_AST, false, null, null);
			}

			// カーソル位置の Java 要素を取得
			IJavaElement selectedElement = cu.getElementAt(textSelection.getOffset());

			CopyContext context = CopyContext.of(selectedElement, textSelection);
			String copy = CopyTextBuilder.build(context);

			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(copy), null);
			MessageDialog.openInformation(editor.getSite().getShell(), TITLE, "クリップボードにコピーしました\n" + copy);

		} catch (CopyHandlerException e) {
			MessageDialog.openError(editor.getSite().getShell(), TITLE, e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(editor.getSite().getShell(), TITLE, "処理中に例外が発生しました: " + e.getMessage());
		}
		return null;
	}

	private static class CopyHandlerException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public CopyHandlerException(String message) {
			super(message);
		}
	}

	private static final class CopyContext {
		private final ITextSelection textSelection;
		private final String selectedText;
		private final IJavaElement element;

		private CopyContext(IJavaElement element, ITextSelection textSelection, String selectedText) {
			this.element = element;
			this.textSelection = textSelection;
			this.selectedText = selectedText;
		}

		public static CopyContext of(IJavaElement element, ITextSelection textSelection) {
			if (textSelection == null) {
				throw new IllegalStateException("textSelectionがnullです。");
			}
			String selectedText = textSelection.getText();
			String trimmedText = selectedText == null ? "" : selectedText.trim();
			return new CopyContext(element, textSelection, trimmedText);
		}

		public ITextSelection getTextSelection() {
			return this.textSelection;
		}

		public IJavaElement getElement() {
			return this.element;
		}

		public String getSelectedText() {
			return this.selectedText;
		}
	}

	private static interface CopyStrategy<T extends IJavaElement> {
		String buildCopyText(T element, CopyContext context);
	}

	private static class TypeCopyStrategy implements CopyStrategy<IType> {
		@Override
		public String buildCopyText(IType type, CopyContext context) {
			if (type == null) {
				return null;
			}
			// プロジェクト名
			String projectName = type.getJavaProject().getElementName();
			// パッケージ名
			String packageName = type.getPackageFragment().getElementName();
			// クラス名。内部クラスの場合は、親クラスを含めて$で区切って取得する
			String className = type.getTypeQualifiedName('$');
			// クリップボードにコピーするテキスト
			return String.format("%s\n%s\n%s", projectName, packageName, className);
		}
	}

	private static class MethodCopyStrategy implements CopyStrategy<IMethod> {
		@Override
		public String buildCopyText(IMethod method, CopyContext context) {

			String methodName = method.getElementName();
			// メソッド名を選択していない場合はエラー
			if (!context.getSelectedText().equals(methodName)) {
				throw new CopyHandlerException("メソッド名を選択してください。");
			}

			IType declaringType = method.getDeclaringType();
			if (declaringType == null) {
				throw new IllegalStateException("クラス情報の取得に失敗しました。");
			}

			// プロジェクト名
			String projectName = method.getJavaProject().getElementName();
			// パッケージ名
			String packageName = declaringType.getPackageFragment().getElementName();
			// クラス名。内部クラスの場合は、親クラスを含めて$で区切って取得する
			String className = declaringType.getTypeQualifiedName('$');
			// 行位置
			int lineNumber = context.getTextSelection().getStartLine() + 1;
			// クリップボードにコピーするテキスト
			return String.format("%s\n%s\n%s\nL%d: %s", projectName, packageName, className, lineNumber, methodName);
		}
	}

	private static class CopyTextBuilder {
		public static String build(CopyContext context) {
			if (context.getElement() instanceof IType type) {
				return new TypeCopyStrategy().buildCopyText(type, context);
			}
			if (context.getElement() instanceof IMethod method) {
				return new MethodCopyStrategy().buildCopyText(method, context);
			}
			String elementName = (context.getElement() == null) ? "なし" : context.getElement().getElementName();
			throw new CopyHandlerException(String.format(
					"選択された要素（%s）は対象外です。クラス名またはメソッド名を選択してください。",
					elementName));
		}
	}
}
