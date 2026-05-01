# 第3回 DAO（参照系）と安全性（NotebookLM 用 原稿）

## 0. 5分間プレゼン（先に宣言）

**お題**: DAO分離とプリペアドステートメント  
**ルール**: 講師のOKが出るまで次に進まない

---

## 1. 到達目標（最小）

- DAOに「DBとの会話（参照）」を集約する意味を説明できる
- `PreparedStatement` の「値を埋める位置」を説明できる
- `emps` 単表と `depts`+`emps`（JOIN）の SELECT を DAO に閉じて実行できる

---

## 2. 本日の位置づけ（上流と切り離す）

- 今日の主役は **DAO（DB担当窓口）**
- 上流（Servletではなく、呼び出し側としてのService/画面相当）と切り離して考える
- **INSERT / UPDATE / DELETE とトランザクション**は第4回（`Unit04_DAO_Update`）へ先送り

---

## 3. 65秒集中：DAO/DTO/Entity の3箱

**DAO/DTO/Entity** を3箱で捉える

【図】「Service → DAO → DB」と「DTO（運ぶ箱）」「Entity（DBの行）」の3箱図を描く（関係は矢印で）

写経（これだけ）

```text
Service → DAO → DB
DTO = 運ぶ箱（画面/処理向け）
Entity = DBの行（テーブル向け）
```

---

## 4. 5分図解プレゼン：OKチェック

- DAOの箱が「DBの手前」に置ける
- `PreparedStatement` の `?` と `setXxx` の対応を説明できる
- 「DAOにSQLを書く」「呼び出し側（将来）にSQLを書かない」を言える

---

## 5. 例題の題材：emps / depts（社員・部署）

- 基本は **`emps`** の単表 SELECT、発展は **`depts` と JOIN** して部署名を取る
- DTO は **`EmpDto`**（`deptName` は JOIN 時のみ）

【図】「emps を SELECT し、DAO が EmpDto に詰め替える」「JOIN で dept_name を足す」流れを1枚で描く

---

## 6. PreparedStatement：? と setXxx

- SQL文字列に値をベタ書きしない
- `?` は「値を入れる場所」
- `setXxx` は「その場所に、後から値を渡す操作」

【図】プレースホルダ `?` がSQL内のどこにあり、`setXxx` がどの順番で値を渡すかの対応図

---

## 7. 実戦演習（10分で終わる最小動作確認）

### 7-1. 最小タスク（10分）

- `EmpDao` の `findById` または `findAll` を写経し、結果を表示する
- SQL は `PreparedStatement` にし、文字列連結を消す

### 7-2. 余力があれば（拡張）

- `03-練習問題-DAO参照系（emps-depts）.html` の JOIN 問題に挑戦する

---

## 8. 例外処理（第3回前・強く推奨）

- JDBC では `catch` や `try-with-resources` を先に押さえると `SQLException` が読みやすい
- 教材: `Unit90_Exception`、[optional-exception-handling.md](optional-exception-handling.md)
