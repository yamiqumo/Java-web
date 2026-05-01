/**
 * emps テーブルの1行を表す DTO（本講義では Entity と同一視）。
 * deptName は JOIN 時のみ使用（単表なら null）。
 */
public class EmpDto {

	private int empId;
	private String fname;
	private String lname;
	private int salary;
	private int deptNo;
	private String deptName;

	public EmpDto() {
	}

	public EmpDto(int empId, String fname, String lname, int salary, int deptNo, String deptName) {
		this.empId = empId;
		this.fname = fname;
		this.lname = lname;
		this.salary = salary;
		this.deptNo = deptNo;
		this.deptName = deptName;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
