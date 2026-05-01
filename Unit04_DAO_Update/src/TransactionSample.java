import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 1つの Connection でトランザクションを張り、
 * INSERT → SELECT → UPDATE → SELECT → DELETE → SELECT を連続実行する例。
 * DaoBase / EmpDao と同じプロジェクト（同一パッケージなし）に置いて実行する。
 */
public class TransactionSample extends DaoBase {

	public static void main(String[] args) throws SQLException {
		new TransactionSample().run();
	}

	private void run() throws SQLException {
		final int testEmpId = 99;

		try (Connection con = getConnection()) {
			con.setAutoCommit(false);
			try {
				// 1 INSERT
				String ins = "INSERT INTO emps (emp_id, fname, lname, salary, dept_no) VALUES (?, ?, ?, ?, ?)";
				try (PreparedStatement ps = con.prepareStatement(ins)) {
					ps.setInt(1, testEmpId);
					ps.setString(2, "演習");
					ps.setString(3, "用");
					ps.setInt(4, 200000);
					ps.setInt(5, 10);
					ps.executeUpdate();
				}

				// 2 SELECT
				printSalary(con, testEmpId, "挿入直後");

				// 3 UPDATE
				String upd = "UPDATE emps SET salary = ? WHERE emp_id = ?";
				try (PreparedStatement ps = con.prepareStatement(upd)) {
					ps.setInt(1, 220000);
					ps.setInt(2, testEmpId);
					ps.executeUpdate();
				}

				// 4 SELECT
				printSalary(con, testEmpId, "更新後");

				// 5 DELETE
				String del = "DELETE FROM emps WHERE emp_id = ?";
				try (PreparedStatement ps = con.prepareStatement(del)) {
					ps.setInt(1, testEmpId);
					ps.executeUpdate();
				}

				// 6 SELECT
				printSalary(con, testEmpId, "削除後");

				con.commit();
			} catch (SQLException e) {
				con.rollback();
				throw e;
			} finally {
				con.setAutoCommit(true);
			}
		}
	}

	private static void printSalary(Connection con, int empId, String label) throws SQLException {
		String sql = "SELECT salary FROM emps WHERE emp_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, empId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					System.out.println(label + ": salary=" + rs.getInt("salary"));
				} else {
					System.out.println(label + ": 該当行なし");
				}
			}
		}
	}
}
