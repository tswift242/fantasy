package football.stats;

import java.util.Objects;

//TODO: make T extend Cat
public class Stat<T extends Enum<T>> implements Comparable<Stat<T>>
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

	@Override
	public String toString() {
		return ("(" + category.toString() + "," + value + ")");
	}

	//stats considered equal if they're of the same category
	@Override
	public boolean equals(Object o) {
		if((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		if(this == o) {
			return true;
		}
		@SuppressWarnings("unchecked") //unavoidable
		Stat<T> other = (Stat<T>)o;
		return (this.category == other.category);
	}

	@Override
	public int hashCode() {
		return Objects.hash(category);
	}

	//induce natural ordering on Stat using ordering of its enum
	@Override //implements
	public int compareTo(Stat<T> other) {
		return this.category.compareTo(other.category);
	}
}
