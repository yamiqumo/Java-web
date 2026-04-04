/**
 * items テーブルの1行を表す DTO（Data Transfer Object）。
 * 本講義では DB の列と1対1で対応させ、Entity と同一視して扱う（JavaBeans 形式）。
 */
public class ItemDto {

	private int itemId;
	private String category;
	private String itemName;
	private int unitPrice;

	/** JavaBeans 用の引数なしコンストラクタ */
	public ItemDto() {
	}

	/**
	 * 全フィールドを受け取るコンストラクタ（DAO で ResultSet から組み立てるときに便利）
	 */
	public ItemDto(int itemId, String category, String itemName, int unitPrice) {
		this.itemId = itemId;
		this.category = category;
		this.itemName = itemName;
		this.unitPrice = unitPrice;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
}
