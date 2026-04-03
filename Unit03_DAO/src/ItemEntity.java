/**
 * items テーブルの1行を表すクラス（Entity）。
 * DB の列（item_id, category, item_name, unit_price）と1対1で対応する。
 */
public class ItemEntity {

	// フィールド
	private int itemId;
	private String category;
	private String itemName;
	private int unitPrice;

	/**
	 * コンストラクタ（全フィールドを受け取る）
	 */
	public ItemEntity(int itemId, String category, String itemName, int unitPrice) {
		this.itemId = itemId;
		this.category = category;
		this.itemName = itemName;
		this.unitPrice = unitPrice;
	}

	// getter
	public int getItemId() {
		return itemId;
	}

	public String getCategory() {
		return category;
	}

	public String getItemName() {
		return itemName;
	}

	public int getUnitPrice() {
		return unitPrice;
	}
}
