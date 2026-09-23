Eclipse プラグイン開発環境の設定

※ Eclipse プラグインをテストするためには Eclipse 画面を立ち上げる必要があるが、Codespaces は GUI を持たないため、GUI を描画する環境が必要。

## desktop-lite（noVNC）の導入確認

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

Eclipse のインストール

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

Eclipse 日本語化

```bash
# Pleiades zip のダウンロード
# 一時ディレクトリに Pleiades（日本語化プラグイン）をダウンロードする。
curl -L -o /tmp/pleiades.zip "https://ftp.jaist.ac.jp/pub/mergedoc/pleiades/build/stable/pleiades.zip"

# ダウンロードが完了したら、ファイルサイズを確認
ls -lh /tmp/pleiades.zip

# Eclipse ディレクトリへ解凍・上書き
unzip -o /tmp/pleiades.zip -d ~/eclipse_202312/eclipse/

# eclipse.ini に日本語化エージェント設定を追加（重複防止付き）
if ! grep -q "pleiades.jar" ~/eclipse_202312/eclipse/eclipse.ini; then
  echo "-javaagent:${HOME}/eclipse_202312/eclipse/plugins/jp.sourceforge.mergedoc.pleiades/pleiades.jar" >> ~/eclipse_202312/eclipse/eclipse.ini
fi
```

## GUI サービスの起動と確認

```bash
# デスクトップサービス（noVNC）をバックグラウンドで起動
sudo /usr/local/share/desktop-init.sh &

# 以下のコマンドで 200 OK が返ってくれば、noVNC（ポート 6080）が正常に待機状態になっている
curl -I http://localhost:6080
```

## Eclipse の起動

1. VS Code の「PORTS」タブから ポート 6080 の「Open in Browser (地球アイコン)」をクリックしてブラウザで noVNC 画面を開く。
2. ターミナルで以下を実行して Eclipse を起動する。

```bash
# キャッシュをクリアして初回起動の場合
DISPLAY=:1 ~/eclipse_202312/eclipse/eclipse -clean &

# 2回目以降の起動の場合
DISPLAY=:1 ~/eclipse_202312/eclipse/eclipse &
```

## プラグインの開発と導入

1. 作成したプラグイン（.jar）は以下へエクスポートする

```
/workspaces/portfolio-java/add-ons/eclipse-plugins/java-location-copy/plugins
```

2. 生成された jar ファイルを dropins フォルダへ配置

```bash
# dropins フォルダを作成（存在しない場合）
mkdir -p ~/eclipse_202312/eclipse/dropins

# エクスポートされた jar を dropins へコピー
cd /workspaces/portfolio-java/add-ons/eclipse-plugins/java-location-copy/plugins
cp *.jar ~/eclipse_202312/eclipse/dropins/
```

dropins フォルダとは:

Eclipse にプラグインを手動追加するための標準フォルダです。ここに入れた .jar は起動時に自動認識されます。

3. 新しいプラグイン（.jar）を認識させるため、-clean オプションを付けて Eclipse を起動する。
