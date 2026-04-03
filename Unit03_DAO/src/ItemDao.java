import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * items テーブル専用の DAO。CRUD をこのクラスに集約する。
 */
public class ItemDao extends DaoBase {

	/**
	 * 1件検索：主キー item_id で1行を取得する。
	 *
	 * @param itemId 商品ID
	 * @return 該当行があれば ItemEntity、なければ null
	 */
	public ItemEntity findById(int itemId) throws SQLException {
		String sql = "SELECT item_id, category, item_name, unit_price FROM items WHERE item_id = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, itemId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new ItemEntity(
							rs.getInt("item_id"),
							rs.getString("category"),
							rs.getString("item_name"),
							rs.getInt("unit_price"));
				}
				return null;
			}
		}
	}

	/**
	 * 全件検索：items の全行を取得する。
	 *
	 * @return ItemEntity のリスト（0件なら空リスト）
	 */
	public List<ItemEntity> findAll() throws SQLException {
		String sql = "SELECT item_id, category, item_name, unit_price FROM items ORDER BY item_id";
		List<ItemEntity> list = new ArrayList<>();

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(new ItemEntity(
						rs.getInt("item_id"),
						rs.getString("category"),
						rs.getString("item_name"),
						rs.getInt("unit_price")));
			}
		}
		return list;
	}

	/**
	 * 1件挿入：新しい商品行を追加する。
	 *
	 * @param item 追加する商品
	 * @return 更新件数（通常は1）
	 */
	public int insert(ItemEntity item) throws SQLException {
		String sql = "INSERT INTO items (item_id, category, item_name, unit_price) VALUES (?, ?, ?, ?)";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, item.getItemId());
			ps.setString(2, item.getCategory());
			ps.setString(3, item.getItemName());
			ps.setInt(4, item.getUnitPrice());

			return ps.executeUpdate();
		}
	}

	/**
	 * 1件更新：主キー item_id で行を更新する。
	 *
	 * @param item 更新後の内容
	 * @return 更新件数（通常は1）
	 */
	public int update(ItemEntity item) throws SQLException {
		String sql = "UPDATE items SET category = ?, item_name = ?, unit_price = ? WHERE item_id = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, item.getCategory());
			ps.setString(2, item.getItemName());
			ps.setInt(3, item.getUnitPrice());
			ps.setInt(4, item.getItemId());

			return ps.executeUpdate();
		}
	}

	/**
	 * 1件削除：主キー item_id で行を削除する。
	 *
	 * @param itemId 削除する商品ID
	 * @return 削除件数（通常は1）
	 */
	public int delete(int itemId) throws SQLException {
		String sql = "DELETE FROM items WHERE item_id = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, itemId);

			return ps.executeUpdate();
		}
	}

	/**
	 * 発展：カテゴリと最低単価で絞り込み、単価の昇順で取得する。
	 *
	 * @param category カテゴリ
	 * @param minPrice   この単価以上
	 * @return 該当行のリスト
	 */
	public List<ItemEntity> findByCategoryAndMinPriceOrderByPrice(String category, int minPrice) throws SQLException {
		String sql = "SELECT item_id, category, item_name, unit_price FROM items "
				+ "WHERE category = ? AND unit_price >= ? ORDER BY unit_price ASC";
		List<ItemEntity> list = new ArrayList<>();

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, category);
			ps.setInt(2, minPrice);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new ItemEntity(
							rs.getInt("item_id"),
							rs.getString("category"),
							rs.getString("item_name"),
							rs.getInt("unit_price")));
				}
			}
		}
		return list;
	}
}
