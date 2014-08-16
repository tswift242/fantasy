package football.core.graphics;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import football.players.Player;

/*
 * Panel displaying a list of players of a given position, their relevant stats, 
 * and their scores.
 */

public final class PlayersPanel extends JPanel
{
	private static final long serialVersionUID = 7401650925506787631L;

	public PlayersPanel(List<Player> players) {
		// construct table and add to panel
		JTable table = new JTable(new PlayersTableModel(players));
		this.setLayout(new BorderLayout());
		this.add(table.getTableHeader(), BorderLayout.PAGE_START);
		this.add(table, BorderLayout.CENTER);

		//JScrollPane scrollPane = new JScrollPane(table);
		//table.setFillsViewportHeight(true);
		//this.add(scrollPane);
	}



	private class PlayersTableModel extends AbstractTableModel {
		private static final String WHITESPACE_REGEX = "\\s+";

		private String[] columnNames;
		private Object[][] playerData;

		public PlayersTableModel(List<Player> players) {
			// stat categories for this group of players
			String[] categories = players.get(0).categoriesToString().split(WHITESPACE_REGEX);
			// create column names
			int numStats = categories.length; // numStats == numCategories
			int numCols = numStats+2;
			columnNames = new String[numCols];
			columnNames[0] = "Players";
			System.arraycopy(categories, 0, columnNames, 1, numStats);
			columnNames[numCols-1] = "Score";

			// create row data
			int numPlayers = players.size();
			playerData = new Object[numPlayers][numCols];
			int rowIdx = 0; // don't assume List has random access
			for(Player player : players) {
				playerData[rowIdx][0] = player.getName();
				String[] statStrings = player.statsToString().split(WHITESPACE_REGEX);
				// convert stats back from strings to ints
				//TODO: see if we can avoid double converson by changing Player subclasses
				Integer[] stats = toIntegerArray(statStrings);
				System.arraycopy(stats, 0, playerData[rowIdx], 1, stats.length);
				playerData[rowIdx++][numCols-1] = player.getScore();
			}
		}

		@Override
		public int getRowCount() {
			return playerData.length;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int row, int col) {
			return playerData[row][col];
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Class<?> getColumnClass(int col) {
			return getValueAt(0, col).getClass();
		}


		// convert String[] to Integer[]
		private Integer[] toIntegerArray(String[] array) {
			int length = array.length;
			Integer[] result = new Integer[length];
			for(int i = 0; i < length; i++) {
				result[i] = Integer.valueOf(array[i]);
			}
			return result;
		}
	}
}
