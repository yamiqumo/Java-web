# Unit03→Unit04（DAO①SELECT→DAO②更新＋トランザクション）図作成スクリプト（NotebookLM用）

この文章は、NotebookLM に貼り付けて **図（スライド用イラスト）を作ってもらうための台本**です。  
1つの図＝1ブロックで、`【図】` 行に「どういう図にしてほしいか」を強めに書いています。

---

## 図1：DAO①（SELECT）の型：Service→DAO→DB→ResultSet→DTO

**タイトル候補**：DAO①：SELECT の型（ResultSet→EmpDto）

**太字キーワード**：**EmpDao** / **findById** / **executeQuery** / **ResultSet** / **rs.next()** / **EmpDto**

【図】Service が `EmpDao.findById(empId)` を呼ぶ。DAO が `PreparedStatement` を準備し、`executeQuery()` で `ResultSet` を受け取る。`rs.next()` でカーソルを進め、列を `getXxx` で読み、`EmpDto` を組み立てて返す。該当行がなければ `null`。  
矢印の流れは **Service → DAO → DB → ResultSet → DAO → DTO → Service**。

- Service：`findById(empId)` を呼ぶ（SQL は持たない）
- DAO：SQL（SELECT…WHERE emp_id = ?）を保持する
- DAO：`prepareStatement(sql)` → `setInt(1, empId)`
- DAO：`executeQuery()` → `ResultSet`
- DAO：`rs.next()` が true のとき、`rs.getInt/getString...` → `new EmpDto(...)`
- DAO：該当行なしなら `null`
- 戻り値：`EmpDto`（または `null`）

---

## 図2：参照と更新の分岐：executeQuery / executeUpdate と戻り値

**タイトル候補**：参照と更新の分岐（戻り値が違う）

**太字キーワード**：**executeQuery** / **executeUpdate** / **ResultSet** / **更新件数(int)**

【図】同じ「SQL実行」でも、SELECT は `executeQuery()` で `ResultSet` を返す。更新系（INSERT/UPDATE/DELETE）は `executeUpdate()` で **更新件数（int）** を返す。  
左右に並べて比較する図にする（左：参照、右：更新）。

- 参照（SELECT）
  - 実行：`executeQuery()`
  - 戻り：`ResultSet`
  - その後：`rs.next()` / `rs.getXxx(...)` → DTO
- 更新（INSERT/UPDATE/DELETE）
  - 実行：`executeUpdate()`
  - 戻り：`int`（影響行数）

---

## 図3：DAO②（更新系）の型：PreparedStatement と ? の使い方は同じ

**タイトル候補**：DAO②：更新系も PreparedStatement（? と setXxx）

**太字キーワード**：**INSERT** / **UPDATE** / **DELETE** / **?** / **setXxx** / **更新件数**

【図】更新系も「SQL を固定し、値は `?` に `setXxx` で渡す」という点は、DAO①（SELECT）と同じ。違うのは実行メソッド（`executeUpdate`）と戻り値（更新件数）。  
「文字列連結でSQLを作らない」も入れる。

- DAO：SQLテンプレ（INSERT/UPDATE/DELETE）
- DAO：`prepareStatement(sql)` → `setXxx(...)`（`?` に値をバインド）
- DAO：`executeUpdate()` → 更新件数（int）

---

## 図4：トランザクション：全部成功か、全部なかったこと（commit/rollback）

**タイトル候補**：トランザクション：中途半端を防ぐ

**太字キーワード**：**トランザクション** / **commit** / **rollback** / **中途半端防止**

【図】更新①→更新②…の途中で失敗すると「前半だけ反映」が起きる。トランザクションなら失敗時に `rollback` で全部取り消し、成功時は `commit` でまとめて確定できる。  
状態遷移（成功/失敗）を図で見せる。

- トランザクションなし（自動コミット）
  - 更新①成功 → DBに反映済み
  - 更新②失敗 → 更新①だけ残る（中途半端）
- トランザクションあり
  - 更新①成功（まだ確定しない）
  - 更新②失敗 → `rollback()` → 更新①も含めて全部取り消し
  - すべて成功 → `commit()` → まとめて確定

---

## 図5：なぜ「同じConnection」じゃないとトランザクションにならないのか

**タイトル候補**：トランザクションは同じ Connection の範囲

**太字キーワード**：**同じConnection** / **setAutoCommit(false)** / **DAO内getConnection問題**

【図】トランザクションは「同じ Connection の上で行った更新」をまとめる仕組み。DAOメソッドが内部で `getConnection()` してしまうと、更新①と更新②が別 Connection になり、まとめられない。  
2本線（Connection A/B）で、NG/OK を対比させる。

- NG（別Connectionになってしまう）
  - `EmpDao.insert()` が内部で Connection A
  - `EmpDao.updateSalaryAndDept()` が内部で Connection B
  - → rollbackしても片方だけ残る可能性
- OK（同じConnectionで束ねる）
  - 呼び出し側が Connection A を1本持つ
  - `setAutoCommit(false)` → 複数の `PreparedStatement` を同じ Connection A で実行
  - 成功：`commit()` / 失敗：`rollback()` / 最後に `setAutoCommit(true)`

