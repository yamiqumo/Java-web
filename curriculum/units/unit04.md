# 第4回 DAO（更新系）とトランザクション（2026/04/15・Online）

**前提**: 第3回で **DAO・参照系（SELECT）** と `PreparedStatement` を扱っていること。リポジトリ内 [`Unit03_DAO`](../../Unit03_DAO/) の `EmpDao`（参照メソッド）を土台にする。**例外処理**は第3回前に [例外処理](optional-exception-handling.md)（または `Unit90_Exception`）で押さえておくと、`SQLException` や `rollback` の読みが楽になる。

## 0. 5分間プレゼン（先に宣言）

**お題**: INSERT / UPDATE / DELETE を DAO に閉じ、必要ならトランザクションでまとめる  
**ルール**: 講師のOKが出るまで次に進めない。

---

## 1. 到達目標（最小）

- `PreparedStatement` で **更新系SQL**（INSERT / UPDATE / DELETE）を実行し、**更新件数**を扱える
- **1つの `Connection`** に対して `setAutoCommit(false)` → 複文実行 → `commit` / `rollback` の流れを説明できる
- 途中で失敗したとき **中途半端な更新が残らない** 理由を一言で言える

---

## 2. 本日のアーキテクチャ位置づけ

- 今日は **DAO に書き込み責務**を足し、複数操作を **トランザクション**で束ねる回。
- 教材はリポジトリ内 **[Unit04_DAO_Update](../../Unit04_DAO_Update/)**（テキスト・復習・練習問題・サンプル参照）。

---

## 3. 65秒集中動画（集中視聴セクション）

| セグメント | 動画 | start | end | 手動停止の目安 | 65秒集中メモ（写経するもの） |
| :--- | :--- | ---: | ---: | :--- | :--- |
| 1 | 更新系DAO | 0 | 65 | 01:05 | `executeUpdate` と 更新件数 |

写経（これだけ）

```text
更新系も PreparedStatement（? と setXxx）
複数SQLは1接続＋トランザクションでまとめる
失敗時は rollback
```

---

## 4. 5分図解プレゼン：OKチェック

- 参照系と更新系で、DAO メソッドの戻り値の違い（DTO / List vs 件数）を言える
- `commit` 前後で他セッションから見える結果がどう変わるか、ざっくり言える
- `finally` で `setAutoCommit(true)` を戻す理由を言える

---

## 5. 実戦演習（10分で終わる最小動作確認）

### 5-1. 最小タスク（10分）

- `EmpDao` に `insert` / `update` / `delete` のいずれか1つを実装し、Workbench で結果を確認する

### 5-2. 余力があれば（拡張）

- [03-練習問題-DAO更新とトランザクション.html](../../Unit04_DAO_Update/03-練習問題-DAO更新とトランザクション.html) の **発展**（1トランザクション6操作）に挑戦する

---

## 教材（Unit04_DAO_Update）

- **講義テキスト**: `Unit04_DAO_Update/01-テキスト-DAO更新とトランザクション.html`
- **復習・自習**: `Unit04_DAO_Update/02-復習用-DAO更新とトランザクション.html`
- **練習問題**: `Unit04_DAO_Update/03-練習問題-DAO更新とトランザクション.html`
- **Java サンプル（更新系・トランザクション例）**: `Unit04_DAO_Update/src/`（共通の `DaoBase` / `EmpDto` / 参照系 `EmpDao` は `Unit03_DAO/src` を参照）
