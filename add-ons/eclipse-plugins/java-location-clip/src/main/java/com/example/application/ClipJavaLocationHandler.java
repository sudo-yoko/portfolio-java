package com.example.application;

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

import com.example.ClipException;
import com.example.domain.LocationFormatter;

public class ClipJavaLocationHandler extends AbstractHandler {
	private static final String DIALOG_TITLE = "Javaの位置情報をクリップボードにコピー";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (editor == null) {
			return null;
		}
		try {
			IJavaElement element = JavaUI.getEditorInputJavaElement(editor.getEditorInput());
			if (!(element instanceof ICompilationUnit)) {
				return null;
			}
			ICompilationUnit cu = (ICompilationUnit) element;
			// ASTの同期（連続実行・編集直後の誤作動防止）
			if (cu.isWorkingCopy()) {
				cu.reconcile(ICompilationUnit.NO_AST, false, null, null);
			}
			ISelection selection = HandlerUtil.getCurrentSelection(event);
			if (!(selection instanceof ITextSelection)) {
				return null;
			}

			ClipProcessor processor = ClipProcessResolver.resolve(cu, selection);
			if (processor == null) {
				return null;
			}
			String[] location = processor.getJavaLocation();
			String clip = LocationFormatter.format(location);

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
