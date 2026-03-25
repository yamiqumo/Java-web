import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 自習（Html-rv-02）ブロック3用：JDBC で DB に接続し、切断までを試す最小サンプル。
 * URL・ユーザ・パスワードは自分の MySQL 環境に合わせて書き換えてください。
 * クラスパスに mysql-connector-j（または互換ドライバ）を含めて実行します。
 */
public class SampleConnection {

	// TODO: 自分の環境に合わせて修正
	private static final String URL = "jdbc:mysql://localhost:3306/your_database?useSSL=false&serverTimezone=Asia/Tokyo";
	private static final String USER = "root";
	private static final String PASS = "your_password";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("MySQL ドライバが見つかりません。ビルドパスにコネクタJARを追加してください。");
			e.printStackTrace();
			return;
		}

		try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
			System.out.println("接続に成功しました。");
		} catch (SQLException e) {
			System.err.println("接続に失敗しました: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
