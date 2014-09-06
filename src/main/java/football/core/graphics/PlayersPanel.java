package football.core.graphics;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

		// add to panel without JScrollPane
		this.setLayout(new BorderLayout());
		this.add(table.getTableHeader(), BorderLayout.PAGE_START);
		this.add(table, BorderLayout.CENTER);

		// add to panel with JScrollPane
		/*table.setPreferredScrollableViewportSize(table.getPreferredSize());
		//table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		//scrollPane.setPreferredSize(new Dimension(1100, 200));
		this.add(scrollPane);*/
	}

	@SuppressWarnings("unchecked")
	public void updatePlayerScores(List<Player> players) {
		PlayersTableModel model = (PlayersTableModel)table.getModel();
		int col = model.getColumnCount() - 1;

		// update score for each player
		for(Player player : players) {
			String name = player.getName();
			Double score = player.getScore();

			// look up row for this player based on the player name
			int row = model.getIndexFromName(name);
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

		/*
		* Table data is in the following format, as specified by the columnNames:
		*
		* player_name	stat1	...	statN	player_score
		*/
		private String[] columnNames;
		private Object[][] playerData;
		// maps the name of each Player to the row index of the Player in playerData
		// used to quickly lookup player row given name
		// Note: don't need to update values in here because model data doesn't change location
		private Map<String,Integer> namesToIndices;

		public PlayersTableModel(List<Player> players) {
			// create column names
			setColumnNames(players);

			// create row data
			setPlayerData(players);
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

		// look up row index of Player with given name
		public int getIndexFromName(String name) {
			if(!namesToIndices.containsKey(name)) {
				logger.error("{} is not a valid name based on the players used to construct this model", name);
				throw new IllegalArgumentException("invalid player name");
			}
			Integer index = namesToIndices.get(name);
			String actualName = (String)playerData[index][0];
			// Note: should NEVER happen (this is a sanity check)
			if(!actualName.equalsIgnoreCase(name)) {
				logger.error("namesToIndices map is not up to date and needs to be modified\nfound: {}\nexpected: {}", actualName, name);
				return -1;
			}

			return index;
		}


		private void setColumnNames(List<Player> players) {
			// stat categories for this group of players
			String[] categories = players.get(0).categoriesToString().split(WHITESPACE_REGEX);
			// numStats == numCategories
			int numStats = categories.length;
			// add 2 more columns for player names and scores
			int numCols = numStats+2;
			columnNames = new String[numCols];
			columnNames[0] = "Players";
			System.arraycopy(categories, 0, columnNames, 1, numStats);
			columnNames[numCols-1] = "Score";
		}

		private void setPlayerData(List<Player> players) {
			int numCols = getColumnCount();

			playerData = new Object[players.size()][numCols];
			namesToIndices = new HashMap<String,Integer>();
			int rowIdx = 0; // don't assume List has random access
			for(Player player : players) {
				String name = player.getName();
				// set name
				playerData[rowIdx][0] = name;
				// map name to row index for quick lookup later
				namesToIndices.put(name,rowIdx);

				// set stats
				String[] statStrings = player.statsToString().split(WHITESPACE_REGEX);
				// convert stats back from strings to ints
				//TODO: see if we can avoid double converson by changing Player subclasses
				Integer[] stats = toIntegerArray(statStrings);
				System.arraycopy(stats, 0, playerData[rowIdx], 1, stats.length);

				// set score
				playerData[rowIdx++][numCols-1] = player.getScore();
			}
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
