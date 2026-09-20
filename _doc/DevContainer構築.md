# DevContainer 構築手順

### devcontainer.jsonを用いてコンテナをビルドする

```json
// devcontainer.json
{
  "name": "Debian",
  "image": "mcr.microsoft.com/devcontainers/base:bullseye"
}
```

Java 11 と 17 を使用するため、それが標準パッケージとして含まれるDebian 11 (bullseye) を使用する

### インストール

※ Debian 11 はサポート切れでアーカイブされているため、apt は使用しない。

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

ローカルリポジトリの削除

```bash
# /home/vscode/.m2/repository
rm -rf ~/.m2/repository
```
