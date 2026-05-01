# Unit03 / Unit04：PDF同期手順（テンプレ生成＋PDF出力）

このリポジトリでは、講義テキスト HTML（`01-テキスト-*.html`）から **印刷用HTML（`src/template.html`）** を生成し、Chrome headless で PDF を出力します。

## 前提

- リポジトリ直下（`Java-Web`）で実行する
- Google Chrome が入っていると PDF まで自動生成される（無い場合は `src/template.html` だけ生成される）

## Unit03（DAO①）

- 入力（正本）: `Unit03_DAO/01-テキスト-DAOパターンと安全性.html`
- 生成（中間）: `Unit03_DAO/src/template.html`
- 生成（成果物）: `Unit03_DAO/99-テキストpdf-DAOパターンと安全性.pdf`

実行コマンド:

```bash
python scripts/build_unit03_dao_pdf.py
```

確認ポイント:

- `WROTE Unit03_DAO/src/template.html` が出る
- Chrome が見つかる環境なら `WROTE Unit03_DAO/99-テキストpdf-...pdf` が出る
- 図や textarea が見切れていないか（特に大きい code ブロック）

## Unit04（DAO②：更新＋トランザクション）

- 入力（正本）: `Unit04_DAO_Update/01-テキスト-DAO更新とトランザクション.html`
- 生成（中間）: `Unit04_DAO_Update/src/template.html`
- 生成（成果物）: `Unit04_DAO_Update/99-テキストpdf-DAO更新とトランザクション.pdf`

実行コマンド:

```bash
python scripts/build_unit04_dao_update_pdf.py
```

確認ポイント:

- `WROTE Unit04_DAO_Update/src/template.html` が出る
- PDF の本文が A4 に収まっているか
- `details` の開閉状態（PDF用途のテンプレ側の意図どおりか）

