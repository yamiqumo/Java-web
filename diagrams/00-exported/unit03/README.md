# Unit03 DAO 図（exported）

このフォルダには `diagrams/03-DAO/` にある図を、Unit03 用に `unit03_XX_内容.png` 形式へリネームして配置しています。

## 使用対象（Unit03-DAO）

- 実装前提が `Servlet` 未実施のため、Unit03-DAO 側では **`unit03_12_conclusion_value-of-separating.png` は使用しない**（図中に Servlet/Service の記載が含まれるため）。
- 上記以外（`01`〜`11`）を、説明に必要な箇所で使用してください。

## ファイル一覧

| No | ファイル名 | トピック（日本語要約） |
|---:|---|---|
| 01 | `unit03_01_dao-pattern-security_escape-direct-jdbc.png` | DAOパターンと安全性（直書きJDBCからの脱却） |
| 02 | `unit03_02_brittle-vulnerable-direct-jdbc.png` | 直書きJDBCの致命的弱点（変更・攻撃） |
| 03 | `unit03_03_system-requirements_encapsulation_type-safe_data-journey.png` | 専門現場で求める3つの要件 |
| 04 | `unit03_04_three-boxes_dao-dto-entity.png` | DAO/DTO/Entityの3箱と役割 |
| 05 | `unit03_05_entity-vs-dto_reason.png` | EntityとDTOを分ける理由 |
| 06 | `unit03_06_data-journey_save.png` | データの旅（保存：User→Service→DAO→DB） |
| 07 | `unit03_07_data-journey_load.png` | データの旅（取得：DB→DAO→User） |
| 08 | `unit03_08_sql-injection_anatomy.png` | SQLインジェクションの解剖 |
| 09 | `unit03_09_preparedstatement_strong-shield.png` | PreparedStatementによる無効化 |
| 10 | `unit03_10_vulnerable-concat_vs_preparedstatement.png` | 脆弱な書き方 vs PreparedStatement |
| 11 | `unit03_11_dao-refactoring_mission_preparedstatement.png` | DAOへ切り出す・PreparedStatementへ置換 |
| 12 | `unit03_12_conclusion_value-of-separating.png` | 分けることの本当の価値（※Servlet記載のためUnit03-DAOでは不使用） |

