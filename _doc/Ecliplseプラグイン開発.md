# GitHub Codespaces で Eclipse プライグインを開発するメモ

Eclipse プラグインをテストするためには Eclipse 画面を立ち上げる必要があるが、Codespaces は GUI を持たないため、GUI を描画する環境構築が必要

## 前提
ローカルのクライアントPC側は以下の構成とする

- Debian デスクトップ環境は X11 を使用
- IDE は VSCode を使用

## ローカルPC(Debian)側の準備

x11-xserver-utils（xhost コマンドで使用する） がインストールされているか確認
```bash
dpkg -l x11-xserver-utils
```
入っている場合、以下のように行の先頭が「ii」と表示される
```bash
||/ 名前              バージョン   アーキテクチ 説明
+++-=================-============-============-=================================
ii  x11-xserver-utils 7.7+11       amd64        X server utilities
```
入っていなければインストールする
```bash
sudo apt install -y x11-xserver-utils
```

Codespaces（コンテナ）からの画面描画（X11）を受け入れられる状態にする
```bash
xhost +local:
```

ディスプレイ番号を確認しておく
```bash
echo $DISPLAY
```

## Codespace 側の準備  
Eclipse (GTKアプリ) の動作用ライブラリをインストール
```bash
# 1. sources.list の内容で、APTリポジトリの設定が古い場合は以下の最新の記述で上書き
cat << 'EOF' | sudo tee /etc/apt/sources.list
deb http://deb.debian.org/debian bullseye main
deb http://security.debian.org/debian-security bullseye-security/updates main
deb http://deb.debian.org/debian bullseye-updates main
EOF

# 2. 古いキャッシュをクリア
sudo rm -rf /var/lib/apt/lists/*

# 3. パッケージ情報の更新（これで 404 が出なくなります）
sudo apt-get update

# 4. 目的のライブラリ群をインストール
sudo apt-get install -y libgtk-3-0 libsecret-1-0 libdbus-glib-1-2 x11-apps
```

Eclipse のダウンロード
```bash
# 保存用ディレクトリ作成
mkdir -p ~/eclipse_202403

# Eclipse(プラグイン開発用(committers))のダウンロードと解凍
curl -L -o eclipse.tar.gz "https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2024-03/R/eclipse-committers-2024-03-R-linux-gtk-x86_64.tar.gz&r=1"
tar -xzf eclipse.tar.gz -C ~/eclipse_202403/
```

## Eclipse の起動

Codespace 側で以下実行する
```bash
# ローカル (Debian) の X11 サーバーへ画面出力を設定
# ディスプレイ番号はローカルに合わせる
export DISPLAY=host.docker.internal:0

# Eclipse をバックグラウンドで起動
~/eclipse_202403/eclipse &
```




