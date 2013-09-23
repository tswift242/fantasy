package football;

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

	//stats considered equal if they're of the same category
	public boolean equals(Stat<T> other) {
		return (this.category == other.category);
	}
}
