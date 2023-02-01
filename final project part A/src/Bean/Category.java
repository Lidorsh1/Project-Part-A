package Bean;

//categories for the coupons
public enum Category {

	FOOD(1), ELECTRICITY(2), RESTAURANT(3), VACATION(4), Clothes(5);

	int id;

	Category(int id) {
		this.id = id;

	}

	public int getId() {
		return id;
	}

}
