import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DAO 共通クラス：接続情報を1か所に集約する。
 * 各テーブル用の DAO（例：ItemDao）はこのクラスを継承し、getConnection() で DB に接続する。
 * URL・ユーザ・パスワードは自分の MySQL 環境に合わせて書き換えてください。
 */
public class DaoBase {

	// TODO: 自分の環境に合わせて修正（Unit02 の JDBC サンプルと同じ前提）
	private static final String URL = "jdbc:mysql://localhost:3306/lesson?useSSL=false&serverTimezone=Asia/Tokyo";
	private static final String USER = "root";
	private static final String PASS = "password";

	/**
	 * DB への接続を取得する。
	 *
	 * @return Connection
	 * @throws SQLException 接続に失敗した場合
	 */
	protected Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
