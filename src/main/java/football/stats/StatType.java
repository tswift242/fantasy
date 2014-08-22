package football.stats;

/*
 * Tagging interface for Stat
 * Represents a type of football stat (e.g. Passing, Rushing, Receiving, etc.)
 */

public interface StatType
{
	// return the display-friendly name of this object
	public String getText();
}
