VSCode 拡張機能の開発環境構築

Node.js と NPM のインストール

```bash
# NPM のインストール
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash

# 設定を反映させる
source ~/.bashrc

# 最新 LTS の Node.js と npm をインストール
nvm install --lts

# 有効化
nvm use --lts
```

インストール後の確認

```bash
node -v
npm -v
```

拡張機能ジェネレーターとビルドツールの導入

```bash
npm install -g yo generator-code @vscode/vsce
```

プロジェクトの自動生成

```bash
yo code
```

```bash
`list` prompt is deprecated. Use `select` prompt instead.
✔ What type of extension do you want to create? New Extension (TypeScript)
✔ What's the name of your extension? java-location-copy
✔ What's the identifier of your extension? java-location-copy
✔ What's the description of your extension? Java Location Copy
✔ Initialize a git repository? No
`list` prompt is deprecated. Use `select` prompt instead.
✔ Which bundler to use? unbundled
`list` prompt is deprecated. Use `select` prompt instead.
✔ Which package manager to use? npm
```

拡張機能パッケージ化ツール

```bash
npm install -g @vscode/vsce
```

プロジェクトルートで以下のコマンドを実行して .vsix ファイルを作成する
```bash
vsce package
```

拡張機能をインストールする
```bash
code --install-extension xxx.vsix
```
