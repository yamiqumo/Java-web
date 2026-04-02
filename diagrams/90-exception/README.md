# 例外処理オプション講座用図（PNG）

元ファイルは `Java_Exception_Blueprint_ページ_02`〜`_10`（計9枚）。**講義ストーリー順の連番**に合わせて `opt-exception_01`〜`09` にリネーム済み。

## ファイル一覧とカリキュラム割り付け

| No | ファイル名 | 対応セクション（あらすじ） | 内容（要約） |
| :--- | :--- | :--- | :--- |
| 01 | `opt-exception_01_what-is-exception.png` | §1 例外とは（導入） | 例外＝実行中のトラブル／ハンドリングあり・なしの対比（コンベア） |
| 02 | `opt-exception_02_compile-vs-runtime.png` | §1 コンパイル vs 実行時 | ビルド時と実行中の境界、対処の違い |
| 03 | `opt-exception_03_throwable-hierarchy-map.png` | §2 階層構造（導入） | `Throwable` → `Error` / `Exception` → `RuntimeException` の全体マップ |
| 04 | `opt-exception_04_checked-exception.png` | §2 チェック例外 | 外部要因・コンパイラが処理を強制・`IOException` / `SQLException` 例 |
| 05 | `opt-exception_05_unchecked-exception.png` | §2 非チェック例外 | `RuntimeException` 系・バグ／論理ミス・対処は任意 |
| 06 | `opt-exception_06_error-class.png` | §2 エラー（Error） | JVM／ハード層・対処不能・捕まえない |
| 07 | `opt-exception_07_responsibility-matrix.png` | §2 まとめ（なぜそう分かれるか） | 外部／内部 × 対処義務の 2×2（責任分界） |
| 08 | `opt-exception_08_try-catch-finally-anatomy.png` | §3 try-catch-finally | try／catch／finally の役割（防衛網の比喩） |
| 09 | `opt-exception_09_execution-flow-normal-vs-exception.png` | §3 実行フロー | 正常系と異常系で catch・スキップ・finally 合流を対比 |

## 現状、図がまだないブロック

以下はあらすじにはあるが、本フォルダには**未収録**（コード例・口頭・別途図で補完）。

- §4 複数 catch の順序、`Exception` を最後に、multi-catch（`|`）
- §5 コラム：スコープと finally（変数は try の外で宣言）
- §6 コラム：try-with-resources

追加したら同じ命名規則 `opt-exception_10_...` 以降で連番を伸ばすとよい。
