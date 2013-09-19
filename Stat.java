//TODO: make T extend Cat
public class Stat<T extends Enum<T>> //forces T to be an enum
{
	private T category;
	private int value;

	public Stat(T category, int value) {
		this.category = category;
		this.value = value;
	}

	public T getCategory() {
		return category;
	}

	public int getValue() {
		return value;
	}
}
