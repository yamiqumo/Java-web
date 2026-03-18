# 第5回 Web基本 & Tomcat（unit05）

## 1. 前回復習セクション

### 1-1. なぜDAOとして分離すべきか（保守性）

**問い**  
なぜデータベースへの接続処理をDAOとして分離すべきなのか、保守性の観点から説明してください。

**結論（覚える一文）**  
DAOは「DBとのやり取り（変更が起きやすい部分）」を1か所にまとめ、**変更・バグ修正・テストの影響範囲を最小化**するために分離する。

**保守性のポイント（写経して覚える）**

- **変更点を1か所に集約**できる  
	- DB接続URL、ユーザー、パスワード  
	- JDBCドライバ、接続方式  
	- SQLの修正（列追加・条件追加・並び替え等）
- **「データの取り出し方」と「使い方」を分離**できる  
	- DAO: SQL/JDBCの詳細  
	- それ以外: 何をしたいか（例：売上一覧を表示したい）
- **テストがしやすくなる**  
	- DAOだけ差し替えて、DBなしでも上流の動作確認がしやすい

**NG例（この形を避ける）**

```
画面やServletの中に、SQLやJDBCの細かい手順が散らばっている
→ 仕様変更のたびに複数箇所修正になり、壊れやすい
```

---

### 1-2. DTOとEntityの違い（図解：文字で表現）

**問い**  
DTOとEntityの違いを、図解（文字で表現）して説明してください。

**結論（覚える一文）**  
Entityは「DBの行」を表し、DTOは「画面や処理が使いやすい形に運ぶ箱」。

**図解（写経用）**

```
[DB]                         [アプリ]
テーブル1行                 画面/処理で使うデータの形

┌──────────────────┐        ┌──────────────────────┐
│ sales_table        │        │ SalesSummaryDto       │
│ id (PK)            │        │ totalAmount           │
│ sale_date          │  →DTO→ │ displayDate           │
│ amount             │        │ shopName              │
│ shop_id (FK)       │        │ ...（必要な形に編集） │
└──────────────────┘        └──────────────────────┘
        ↑
      Entity
  SalesEntity（列とほぼ1対1）
```

**整理（写経して暗記）**

- **Entity**
	- テーブルの列と**ほぼ1対1**
	- DB設計変更の影響を受けやすい（= DB寄りの層で管理したい）
- **DTO**
	- 画面・処理・APIの都合で**必要な項目だけ集める／名前や型を整える**
	- 1つのDTOに、複数Entityの情報が混ざってもよい

---

## 2. チャプター1: インターネットの仕組み（動画視聴 8分）

**視聴区間**: 00:54 〜 08:52（手動停止の目安: **08:52**）

### 2-1. 動画（埋め込み）

<iframe
	width="560"
	height="315"
	src="https://www.youtube.com/embed/gvcCEFlA-50?start=54&end=532&rel=0"
	title="インターネットの仕組み（抜粋）"
	frameborder="0"
	allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
	allowfullscreen></iframe>

### 2-2. リロード（JSなし版）

- **リセット方法**: ブラウザの更新（Windows: `F5` / `Ctrl+R`、Mac: `Command+R`）  
- **リセット用リンク**: 同じ区間を開き直す → `https://www.youtube.com/embed/gvcCEFlA-50?start=54&end=532&rel=0`
- **手動停止の目安**: **08:52**（シークした場合も、いったんここで止める）

### 2-3. 写経ポイント（ここだけ書けばOK）

次をノートに「そのまま」書いてください。

```
ブラウザ →（HTTPリクエスト）→ サーバ
ブラウザ ←（HTTPレスポンス）← サーバ

URL = 行き先
Method = やりたいこと（取得/送信）
Header = 条件や情報（ブラウザ情報など）
Body = 送りたいデータ（主にPOST）
```

---

## 3. チャプター2: HTTPリクエストの写経

### 3-1. 目的（短く）

ブラウザが送っているHTTPリクエストを**目で見て写経**し、手続きとして覚えます。

### 3-2. 手順（写経に必要な最短手順）

1. 任意のサイトを開く（例：学校のサイト、ニュースサイト等）。
2. デベロッパーツールを開く（Windows: `F12` / `Ctrl+Shift+I`）。
3. **Network** タブを開く。
4. ページを更新（`F5`）。
5. 一覧から、最初に取得している **document（HTML）** をクリックする。
6. **Headers** を開き、下の「写経テンプレ」に埋める。

### 3-3. 写経テンプレ（これに埋めるだけ）

下をコピーして、埋めてください。

```text
【Request】
URL:
Method:
Status:

【Request Headers】
Host:
User-Agent:
Accept:
Accept-Language:
Referer:（あれば）
Cookie:（あれば。長いなら先頭だけでOK）

【Response Headers】
Content-Type:
Set-Cookie:（あれば）
Cache-Control:（あれば）
```

### 3-4. 写経した内容を「流れ」にする（最小の変換）

写経が終わったら、次の1行に変換して書きます。

```text
（ブラウザ）Method + URL + Header + Body →（サーバ）→ Status + ResponseHeader + Body
```

---

## 4. チャプター3: クイズ

※ 解答は折りたたみ（最初は見えません）。

### 4-1. 第1問（3択）

**問題**: GETメソッドについて、最も適切な説明はどれ？

- A. リクエストボディ（Body）に主要データを載せて送るのが基本である  
- B. URLにパラメータが付くことが多く、同じURLなら再実行しやすい  
- C. 必ずデータ更新に使うメソッドである  

<details>
<summary>解答例</summary>

**正解**: B  
**理由（最短）**: GETはURLに情報が乗りやすく、表示・検索などの「取得」によく使う。  

</details>

---

### 4-2. 第2問（3択）

**問題**: POSTメソッドがふさわしいケースはどれ？

- A. 商品一覧を表示するだけの画面を開く  
- B. フォームの入力内容を送信して、登録処理を行う  
- C. 画像やCSSなどの静的ファイルを取得する  

<details>
<summary>解答例</summary>

**正解**: B  
**理由（最短）**: POSTは送信するデータ（Body）を持ちやすく、登録・更新の処理で使う。  

</details>

---

## 5. チャプター4: Tomcatとディレクトリ構造（動画視聴 10分）

**視聴区間**: 35:43 〜 45:22（手動停止の目安: **45:22**）

### 5-1. 動画（埋め込み）

<iframe
	width="560"
	height="315"
	src="https://www.youtube.com/embed/gvcCEFlA-50?start=2143&end=2722&rel=0"
	title="Tomcatとディレクトリ構造（抜粋）"
	frameborder="0"
	allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
	allowfullscreen></iframe>

### 5-2. リロード（JSなし版）

- **リセット方法**: ブラウザの更新（Windows: `F5` / `Ctrl+R`、Mac: `Command+R`）  
- **リセット用リンク**: 同じ区間を開き直す → `https://www.youtube.com/embed/gvcCEFlA-50?start=2143&end=2722&rel=0`
- **手動停止の目安**: **45:22**

### 5-3. 写経ポイント（フォルダを「箱」として覚える）

ノートに次をそのまま書いてください。

```text
Tomcat
 └ webapps
    └ アプリ名（コンテキスト）
       ├ （公開される領域）HTML/CSS/画像など
       └ WEB-INF（外から直接見えない）
          ├ classes（Javaのクラス）
          └ lib（ライブラリjar）
```

さらに、次の2行も追記してください。

```text
URL →（Tomcat）→ アプリに振り分け
リクエスト →（アプリ）→ 処理 → レスポンス
```

---

## 6. マスター認定（5分間プレゼン課題）

### 6-1. お題

**「Webの循環とTomcatの内部構造」**をホワイトボードに描いて説明してください。

### 6-2. チェックポイント（これだけ描けばOK）

- **Webの循環（矢印が必須）**
	- ブラウザ →（HTTPリクエスト）→ Tomcat
	- ブラウザ ←（HTTPレスポンス）← Tomcat
- **Tomcatの箱の中**
	- `webapps` → アプリ名 → `WEB-INF` → `classes` / `lib`
- **言葉で言えること（短く）**
	- 「ブラウザはHTTPで依頼する」
	- 「Tomcatがアプリに渡して処理する」
	- 「処理結果がHTTPレスポンスで戻る」

