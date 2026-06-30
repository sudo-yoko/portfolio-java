# ポートフォリオ
Java 実装集

## jaxrs-ddd

jakartaEE の JAX-RS および DDD(ドメイン駆動設計) アプリケーションの実装例。

Embedded GlassFish Server を使った簡易サーバーでのアプリケーション起動、Derbyを使った組み込みデータベース、JPA(EclipseLink)実装、Java標準機能を使ったプロキシ設定済みのHTTPクライアントや、暗号／複合の実装など。

## sso-proto

シングルサインオンの実装例

```mermaid
sequenceDiagram
    autonumber
    actor ユーザー
    box domain1
        participant アプリA
        participant IdP
    end
    box domain2
        participant アプリB
    end
    ユーザー->>アプリA: ログイン
    Note over アプリA: ログイン認証
    アプリA->>アプリB: シングルサインオン
    Note over アプリB: ログイン認証
    Note over アプリB: アプリB ログインチェック
    alt アプリB未ログインの場合
        アプリB-->>IdP: リダイレクト
        Note over IdP: 認可エンドポイント
        Note over IdP: アプリA ログインチェック
        alt アプリA未ログインの場合
            IdP-->>ユーザー: リダイレクト
            ユーザー->>アプリA: ログイン
            Note over アプリA: ログイン認証
            Note over アプリA: ログイン完了
            アプリA->>IdP: リダイレクト
        end
        IdP-->>ユーザー: 同意確認
        ユーザー->>IdP: 同意する
        IdP-->>ユーザー: 認可コード
        ユーザー->>アプリB: リダイレクト
        アプリB-->>IdP: アクセストークンを要求
        Note over IdP: トークンエンドポイント
        IdP->>アプリB: トークンを返す
    end
```

## JEP330

JEP330 (Launch Single-File Source-Code Programs)を使用した便利なツール実装例。

Javaソースを事前コンパイルしないで直接実行するため、スクリプトのように手軽に実行できます。Java標準APIのみを使ったシンプルな利用であれば非常に簡単に利用できます。



