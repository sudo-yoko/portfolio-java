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

## Eclipse プラグイン開発環境の設定

※ Eclipse プラグインをテストするためには Eclipse 画面を立ち上げる必要があるが、Codespaces は GUI を持たないため、GUI を描画する環境が必要。

### desktop-lite（noVNC）の導入確認

コンテナ作成時に desktop-lite を追加しているので、以下のコマンドで確認する。

```bash
ls -la /usr/local/share/desktop-init.sh
```

`-rwxr-xr-x` とファイル情報が表示されれば、desktop-lite のインストールは成功している。

Eclipse (GTKアプリ) および noVNC 動作用ライブラリのインストール

```bash
sudo apt-get update

sudo apt-get install -y \
  openbox \
  websockify \
  x11-utils \
  dbus-x11 \
  libgtk-3-0 \
  libsecret-1-0 \
  libdbus-glib-1-2 \
  x11-apps
```

Eclipse のダウンロード

```bash
# 保存用ディレクトリ作成
mkdir -p ~/eclipse_202312

# Eclipse(プラグイン開発用(committers))のダウンロードと解凍
curl -L -o eclipse.tar.gz "https://archive.eclipse.org/technology/epp/downloads/release/2023-12/R/eclipse-committers-2023-12-R-linux-gtk-x86_64.tar.gz"
tar --warning=no-unknown-keyword -xzf eclipse.tar.gz -C ~/eclipse_202312/

# 正しく展開されたか確認
# -rwxr-xr-x などの実行権限がついた eclipse ファイルが表示されていれば、正常に解凍できている。
ls -la ~/eclipse_202312/eclipse/eclipse

# アーカイブの削除
rm eclipse.tar.gz
```

GUI サービスの起動と確認

```bash
# デスクトップサービス（noVNC）をバックグラウンドで起動
sudo /usr/local/share/desktop-init.sh &

# 以下のコマンドで 200 OK が返ってくれば、noVNC（ポート 6080）が正常に待機状態になっている
curl -I http://localhost:6080
```

### Eclipse の起動

1. VS Code の「PORTS」タブから ポート 6080 の「Open in Browser (地球アイコン)」をクリックしてブラウザで noVNC 画面を開く。
2. ターミナルで以下を実行して Eclipse を起動する。

```bash
DISPLAY=:1 ~/eclipse_202312/eclipse/eclipse &
```
