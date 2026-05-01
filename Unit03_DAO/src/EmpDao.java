import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * emps テーブル専用の DAO。参照系は第3回、更新系・トランザクションは第4回で扱う。
 */
public class EmpDao extends DaoBase {

	/**
	 * 1件検索：主キー emp_id で1行を取得する（部署名は null）。
	 */
	public EmpDto findById(int empId) throws SQLException {
		String sql = "SELECT emp_id, fname, lname, salary, dept_no FROM emps WHERE emp_id = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, empId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new EmpDto(
							rs.getInt("emp_id"),
							rs.getString("fname"),
							rs.getString("lname"),
							rs.getInt("salary"),
							rs.getInt("dept_no"),
							null);
				}
				return null;
			}
		}
	}

	/**
	 * 全件検索：emps の全行を取得する。
	 */
	public List<EmpDto> findAll() throws SQLException {
		String sql = "SELECT emp_id, fname, lname, salary, dept_no FROM emps ORDER BY emp_id";
		List<EmpDto> list = new ArrayList<>();

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(new EmpDto(
						rs.getInt("emp_id"),
						rs.getString("fname"),
						rs.getString("lname"),
						rs.getInt("salary"),
						rs.getInt("dept_no"),
						null));
			}
		}
		return list;
	}

	/**
	 * 部署番号で絞り込み。
	 */
	public List<EmpDto> findByDeptNo(int deptNo) throws SQLException {
		String sql = "SELECT emp_id, fname, lname, salary, dept_no FROM emps WHERE dept_no = ? ORDER BY emp_id";
		List<EmpDto> list = new ArrayList<>();

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, deptNo);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new EmpDto(
							rs.getInt("emp_id"),
							rs.getString("fname"),
							rs.getString("lname"),
							rs.getInt("salary"),
							rs.getInt("dept_no"),
							null));
				}
			}
		}
		return list;
	}

	/**
	 * 部署名つきで全件（JOIN）。
	 */
	public List<EmpDto> findAllWithDeptName() throws SQLException {
		String sql = "SELECT e.emp_id, e.fname, e.lname, e.salary, e.dept_no, d.dept_name "
				+ "FROM emps e JOIN depts d ON e.dept_no = d.dept_no ORDER BY e.emp_id";
		List<EmpDto> list = new ArrayList<>();

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(new EmpDto(
						rs.getInt("emp_id"),
						rs.getString("fname"),
						rs.getString("lname"),
						rs.getInt("salary"),
						rs.getInt("dept_no"),
						rs.getString("dept_name")));
			}
		}
		return list;
	}

	/**
	 * 1件挿入（第4回）。
	 */
	public int insert(EmpDto e) throws SQLException {
		String sql = "INSERT INTO emps (emp_id, fname, lname, salary, dept_no) VALUES (?, ?, ?, ?, ?)";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, e.getEmpId());
			ps.setString(2, e.getFname());
			ps.setString(3, e.getLname());
			ps.setInt(4, e.getSalary());
			ps.setInt(5, e.getDeptNo());

			return ps.executeUpdate();
		}
	}

	/**
	 * 1件更新（第4回）：主キー emp_id で給与と部署を更新する例。
	 */
	public int updateSalaryAndDept(EmpDto e) throws SQLException {
		String sql = "UPDATE emps SET salary = ?, dept_no = ? WHERE emp_id = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, e.getSalary());
			ps.setInt(2, e.getDeptNo());
			ps.setInt(3, e.getEmpId());

			return ps.executeUpdate();
		}
	}

	/**
	 * 1件削除（第4回）。
	 */
	public int delete(int empId) throws SQLException {
		String sql = "DELETE FROM emps WHERE emp_id = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, empId);

			return ps.executeUpdate();
		}
	}
}
