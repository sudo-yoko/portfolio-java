DevContainer 構築手順

## コンテナをビルドする

```jsonc
// devcontainer.json
{
  "name": "Debian",
  "image": "mcr.microsoft.com/devcontainers/base:trixie",
  // Eclipse プラグイン開発用：
  // GUI 描画および noVNC 転送環境を一括セットアップ
  "features": {
    "ghcr.io/devcontainers/features/desktop-lite:1": {
      "version": "latest",
    },
  },
  // Eclipse プラグイン開発用：
  // noVNC 用のポートフォワード設定
  "forwardPorts": [6080],
}
```

## Java 環境のインストール

※ Java は 11 や 17 の古いバージョンを使用するため、新しい Debian のリポジトリには無いので apt ではなく SDKMAN! を使用する。

SDKMAN! のインストール

```bash
# SDKMAN! のインストールスクリプトを実行
curl -s "https://get.sdkman.io" | bash

# 設定を現在のシェルに反映
source "$HOME/.sdkman/bin/sdkman-init.sh"

# インストールが成功したか確認
sdk version
```

maven のインストール

```bash
sdk install maven
```

JDK のインストール

```bash
# Eclipse Temurin（旧 AdoptOpenJDK）の安定版 JDK をインストールする。

# JDK 11 のインストール
sdk install java 11.0.22-tem

# JDK 17 のインストール（インストール中にデフォルトにするか聞かれたら 'Y' またはそのまま Enter）
sdk install java 17.0.10-tem
```

インストール後の動作確認

```bash
java -version
mvn -version
```

Java のバージョン切り替え

```bash
# 一時的に Java 11 に切り替える（現在のターミナルセッションのみ）
sdk use java 11.0.22-tem

# デフォルトの Java を Java 17 に固定・変更する
sdk default java 17.0.10-tem
```

## その他コマンドメモ

ローカルリポジトリの削除

```bash
# /home/vscode/.m2/repository
rm -rf ~/.m2/repository
```
