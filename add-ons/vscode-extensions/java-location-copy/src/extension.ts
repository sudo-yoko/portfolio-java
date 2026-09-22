import path from "path";
import * as vscode from "vscode";

export function activate(context: vscode.ExtensionContext) {
  const command = "java-location-copy.copyMetadata";
  const callback = async () => {
    const editor = vscode.window.activeTextEditor;
    if (!editor) return;
    const segments = editor.document.uri.fsPath.split(path.sep);

    // プロジェクト名
    const srcIdx = segments.indexOf("src");
    if (srcIdx <= 0) return structureErr();
    const project = segments[srcIdx - 1];

    // パッケージ名
    const mainIdx = segments.indexOf("java");
    if (mainIdx === -1 || mainIdx >= segments.length - 1) return structureErr();
    const mainSegments = segments.slice(mainIdx + 1);
    const packageName = mainSegments.slice(0, -1).join(".");

    // クラス名
    const fileName = mainSegments[mainSegments.length - 1];
    const className = path.parse(fileName).name;

    // メソッド名
    const selection = editor.selection;
    const methodName = editor.document.getText(selection).trim();
    if (!methodName) return noWordErr();
    const lineNumber = selection.active.line + 1;

    // クリップボードにコピー
    const clipboard = `${project}\n${packageName}\n${className}\nL${lineNumber}: ${methodName}`;
    await vscode.env.clipboard.writeText(clipboard);
    vscode.window.showInformationMessage("クリップボードにコピーしました。");
  };

  const disposable = vscode.commands.registerCommand(command, callback);
  context.subscriptions.push(disposable);
}
function structureErr() {
  vscode.window.showErrorMessage("Java のパッケージ構造が不正です。");
}
function noWordErr() {
  vscode.window.showErrorMessage("メソッド名をハイライト選択してください。");
}
export function deactivate() {}
