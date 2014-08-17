package football.core.graphics;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import static javax.swing.RowSorter.SortKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.players.Player;

/*
 * Panel displaying a list of players of a given position, their relevant stats, 
 * and their scores.
 */

public final class PlayersPanel extends JPanel
{
	private static final long serialVersionUID = 7401650925506787631L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private JTable table;

	public PlayersPanel(List<Player> players) {
		// construct table
		table = new JTable(new PlayersTableModel(players));
		setSortingBehavior(table);

		// add to panel
		this.setLayout(new BorderLayout());
		this.add(table.getTableHeader(), BorderLayout.PAGE_START);
		this.add(table, BorderLayout.CENTER);

		//table.setFillsViewportHeight(true);
		//JScrollPane scrollPane = new JScrollPane(table);
		//this.add(scrollPane);
	}

	public void updatePlayerScores(List<Player> players) {
		PlayersTableModel model = (PlayersTableModel)table.getModel();
		int col = model.getColumnCount() - 1;

		// update score for each player
		for(Player player : players) {
			String name = player.getName();
			Double score = player.getScore();

			// look up row for this player based on the player name
			//TODO: replace this
			int row = model.getIndexForName(name);
			logger.debug("updating score for {}", name);
			model.setValueAt(score, row, col);
		}

		// sort players once all scores set
		((TableRowSorter<TableModel>)table.getRowSorter()).sort();
	}

	// set default sorting options for table
	private void setSortingBehavior(JTable table) {
		// enable sorting
		TableModel model = table.getModel();
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);

		// sort by score in ascending order
		List<SortKey> keys = new ArrayList<SortKey>();
		keys.add(new SortKey(model.getColumnCount()-1, SortOrder.ASCENDING));
		sorter.setSortKeys(keys);
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

		@Override
		public void setValueAt(Object value, int row, int col) {
			logger.debug("setting value at ({},{}) to {}", row, col, value);
			playerData[row][col] = value;
			fireTableCellUpdated(row, col);
		}

		// searches "Player" column for name matching given name, and returns corresponding index
		//TODO: replace this with name -> index map for better efficiency (look at RowSorterListener)
		public int getIndexForName(String name) {
			int index = -1;
			int numRows = getRowCount();
			for(int i = 0; i < numRows; i++) {
				String currName = playerData[i][0].toString();
				if(currName.equalsIgnoreCase(name)) {
					index = i;
					break;
				}
			}

			return index;
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
