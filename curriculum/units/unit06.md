# 第6回 Servlet（司令塔）の仕組み（2026/04/21・Online）

## 0. 5分間プレゼン（先に宣言）

**お題**: Servletの司令塔メカニズム  
**ルール**: 講師のOKが出るまで次に進めない。

---

## 1. 到達目標（最小）

- Servletが「受け取る→判断→渡す→返す」の司令塔だと言える
- `request.getParameter` と `request.setAttribute` の役割を言える
- 画面→Servlet→DAO→DB→JSP の往復がつながる

---

## 2. 本日のアーキテクチャ位置づけ

- 今日は黄金ルートの中心 **Servlet（Controller）** を固める回。

---

## 3. 65秒集中動画（集中視聴セクション）

| セグメント | 動画 | start | end | 手動停止の目安 | 65秒集中メモ（写経するもの） |
| :--- | :--- | ---: | ---: | :--- | :--- |
| 1 | サーバーJava | 3062 | 3127 | 52:07 | doGet/doPost の型（3行） |

写経（これだけ）

```text
getParameter（受け取る）
処理（Service/DAOへ）
setAttribute + forward（表示へ渡す）
```

---

## 4. 5分図解プレゼン：OKチェック

- Servletを「中央の司令塔」として描ける
- request/response の矢印が描ける
- 「Servletは表示しない、JSPが表示する」を言える

---

## 5. 実戦演習（10分で終わる最小動作確認）

### 5-1. 最小タスク（10分）

- フォームから1値を送る（POST）
- Servletで `getParameter` し、JSPに `setAttribute` して表示する

### 5-2. 余力があれば（拡張）

- 受け取る値を2つに増やす（型変換を1つ含める）

