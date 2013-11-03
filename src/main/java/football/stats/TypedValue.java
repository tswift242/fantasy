package football.stats;

import java.util.Objects;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.StatType;

/*
 * Class representing a numeric value tagged with a given type. Similar to a generic Pair, 
 * except that the first object must be an enum StatType, and the second value must be a number.
 */

public abstract class TypedValue<T extends Enum<T> & StatType, V extends Number> implements Comparable<T,V>
{
	protected final T category;
	protected final V value;

	public TypedValue(T category, V value) {
		checkNotNull(category, "category is null");
		checkNotNull(value, "value is null");
		this.category = category;
		this.value = value;
	}

	public T getCategory() {
		return category;
	}

	public V getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)", category.toString(), value.toString());
	}

	//typedvalues considered equal if they're of the same category
	@Override
	public boolean equals(Object o) {
		if((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		if(this == o) {
			return true;
		}
		@SuppressWarnings("unchecked") //unavoidable
		TypedValue<T,V> other = (TypedValue<T,V>)o;
		return (this.category == other.category);
	}

	@Override
	public int hashCode() {
		return Objects.hash(category);
	}

	//induce natural ordering on TypedValue using ordering of its enum type
	@Override
	public int compareTo(TypedValue<T,V> other) {
		checkNotNull(other, "other is null");
		return this.category.compareTo(other.category);
	}
}
