# Java 例外処理（第3回 DAO 直前・強く推奨）

- **想定時間**: 30分〜1時間
- **位置づけ**: [第3回 DAO（参照系）](unit03.md) および第4回の **トランザクション（`rollback`）** で `SQLException`・try-with-resources を扱うための土台。**正課の流れでは第3回の直前に先に実施する想定**とする。未実施でも演習は進められるが、`catch` / `finally` の読みが難しくなる。
- **推奨タイミング**: [第3回 DAO（参照系）](unit03.md) の直前（JDBC で `try` / `catch` に触れた直後）
- **教材**: リポジトリ内 `Unit90_Exception/`

---

## 到達目標（最小）

- コンパイルエラーと実行時エラーを区別できる
- チェック例外と非チェック例外をざっくり説明できる
- try / catch / finally の流れを追える
- 複数 catch で `Exception` を最後に置く理由を言える
- multi-catch（`|`）による複数型の捕捉の役割を言える
- try-with-resources が何のためか一言で言える

---

## 講義項目（目次）

1. 例外とは（コンパイルエラーと実行時エラー）
2. 例外の種類（チェック例外と非チェック例外）
3. try-catch（try / catch / finally）
4. 複数の捕捉と順序（`Exception` は最後、`|` による複数型）
5. （コラム）Try-with-resources

---

## 注記

- スライド原稿・本文・図は別途作成する（Gemini 等）。図版のファイル名・カリキュラム割り付けは [diagrams/90-exception/README.md](../../diagrams/90-exception/README.md) を参照。
- 将来、HTML 教材用フォルダを切る場合は [.cursorrules](../../.cursorrules) の命名規則（例: `02-復習用-…`、単元フォルダ `UnitXX_…`）に合わせる。
