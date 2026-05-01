# 第3回 DAO（参照系）と安全性（2026/04/08・Online）

**前提（強く推奨）**: [例外処理](optional-exception-handling.md)（教材: `Unit90_Exception`）を **第3回の直前**に実施しておく。`SQLException` と try-with-resources の意味がつかみやすくなる。

**第4回との分担**: 今日は **SELECT（参照系）** のみ。INSERT / UPDATE / DELETE と **トランザクション**は [第4回 DAO（更新系）](unit04.md)（`Unit04_DAO_Update`）で扱う。

## 0. 5分間プレゼン（先に宣言）

**お題**: DAO分離とプリペアドステートメント  
**ルール**: 講師のOKが出るまで次に進めない。

---

## 1. 到達目標（最小）

- DAOに「DBとの会話（参照）」を集約する意味を説明できる
- `PreparedStatement` の「値を埋める位置」を説明できる
- `emps`（単表）と `depts`+`emps`（関連）の SELECT を DAO に閉じて実行できる

---

## 2. 本日のアーキテクチャ位置づけ

- 今日は **DAO（DB担当窓口）** を固定し、上流（Servlet/Service）と切り離す回。題材は **部署・社員（`depts` / `emps`）**。

---

## 3. 65秒集中動画（集中視聴セクション）

| セグメント | 動画 | start | end | 手動停止の目安 | 65秒集中メモ（写経するもの） |
| :--- | :--- | ---: | ---: | :--- | :--- |
| 1 | DAO/DTO | 0 | 65 | 01:05 | 「DAO/DTO/Entity」の3箱図 |

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
- 「DAOにSQLを書く」「ServletにSQLを書かない」を言える

---

## 5. 実戦演習（10分で終わる最小動作確認）

### 5-1. 最小タスク（10分）

- `EmpDao` の `findById` または `findAll` を写経し、コンソールに1件または全件を表示する
- SQL は `PreparedStatement` とし、文字列連結で値を埋め込まない

### 5-2. 余力があれば（拡張）

- `03-練習問題-DAO参照系（emps-depts）.html` の **発展（JOIN）** に挑戦する

---

## 教材（Unit03_DAO）

- **講義テキスト**: `Unit03_DAO/01-テキスト-DAOパターンと安全性.html`（穴埋め写経つき）
- **練習問題**: `Unit03_DAO/03-練習問題-DAO参照系（emps-depts）.html`
- **Java サンプル**: `Unit03_DAO/src/`（`DaoBase.java` / `EmpDto.java` / `EmpDao.java`）。更新系メソッドは第4回で使うためクラス内に含まれるが、第3回の講義では **参照メソッドを中心**とする。
