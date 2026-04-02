# オプション講座：Java 例外処理

- **想定時間**: 30分〜1時間（正課外・任意）
- **位置づけ**: 第3回（DAO）および JDBC の `SQLException`・try-with-resources を理解するための前提として推奨する。未受講でも第3回の到達目標は追える。
- **推奨タイミング**: [第3回 DAO](unit03.md) の直前（JDBC で `try` / `catch` に触れた直後）

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

- スライド原稿・本文・図は別途作成する（Gemini 等）。図版のファイル名・カリキュラム割り付けは [diagrams/Exception/README.md](../../diagrams/Exception/README.md) を参照。
- 将来、HTML 教材用フォルダを切る場合は [.cursorrules](../../.cursorrules) の命名規則（例: `02-復習用-…`、単元フォルダ `UnitXX_…`）に合わせる。
