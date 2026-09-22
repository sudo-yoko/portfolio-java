import path from "path";
import * as vscode from "vscode";

export function activate(context: vscode.ExtensionContext) {
  // const command = "java-location-copy.copyMetadata";
  const command = context.extension.packageJSON.contributes.commands[0].command;
  const callback = async () => {
    const editor = vscode.window.activeTextEditor;
    if (!editor) return;
    const segments = editor.document.uri.fsPath.split(path.sep);

    // フォルダ構造のチェック。"src/main/java" であること。
    const srcIdx = segments.indexOf("src");
    if (srcIdx <= 0) return structureErr();
    if (segments[srcIdx + 1] !== "main") return structureErr();
    if (segments[srcIdx + 2] !== "java") return structureErr();

    // プロジェクト名
    const project = segments[srcIdx - 1];

    // パッケージ名
    const section = segments.slice(srcIdx + 3);
    const packageName = section.slice(0, -1).join(".");

    // クラス名
    const fileName = section[section.length - 1];
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
