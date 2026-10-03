package main.java.com.example.application;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

import main.java.com.example.domain.ClipContext;
import main.java.com.example.domain.ClipException;
import main.java.com.example.domain.ClipTextBuilder;

public class ClipJavaLocationHandler extends AbstractHandler {
	private static final String DIALOG_TITLE = "Javaの位置情報をクリップボードにコピー";

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
				throw new ClipException("要素を範囲選択（ハイライト）してから実行してください。");
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

			ClipContext context = ClipContext.of(selectedElement, textSelection);
			String clip = ClipTextBuilder.build(context);

			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(clip), null);
			MessageDialog.openInformation(editor.getSite().getShell(), DIALOG_TITLE, "クリップボードにコピーしました\n" + clip);

		} catch (ClipException e) {
			MessageDialog.openError(editor.getSite().getShell(), DIALOG_TITLE, e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(editor.getSite().getShell(), DIALOG_TITLE, "処理中に例外が発生しました: " + e.getMessage());
		}
		return null;
	}
}
