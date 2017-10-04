import java.util.ArrayList;
import java.util.List;

/**
 * @author zhubingjing
 * @date 2017年9月20日 下午1:38:55
 */
public class Board {
	static final int ROW = 3;
	private List<Integer> blankspace = new ArrayList<Integer>();

	private int filled = 0;// the number of position taken
	private int[] value;// the situation on the board

	public Board() {
		this.value = new int[9];

		// initialize the array to show position number
		for (int i = 0; i < this.value.length; i++) {
			// add all position into blankspace
			this.blankspace.add(i);
			this.value[i] = i + 1;
		}
	}

	public Board(int[] v) {
		this.value = v;

		for (int i = 0; i < this.value.length; i++) {
			if (this.value[i] != i + 1) {
				this.filled++;
			}else{
				this.blankspace.add(i);
			}
		}
	}

	public void setValue(int[] value) {
		this.value = value;
	}

	public int[] getValue() {
		return this.value;
	}

	public Board setPosition(int position, int marker) {
		this.value[position] = marker;
		this.filled++;
		this.blankspace.remove(new Integer(position));

		return this;
	}

	public void print() {
		System.err.println("Current board:");
		for (int i = 0; i < this.value.length; i = i + ROW) {
			System.err.println(
					valueToMarker(value[i]) + "\t" + valueToMarker(value[i + 1]) + "\t" + valueToMarker(value[i + 2]));
		}
	}

	public int getFilled() {
		return filled;
	}

	public static String valueToMarker(int value) {
		if (value == AdvancedTicTacToe.X)
			return "X";
		if (value == AdvancedTicTacToe.O)
			return "O";
		return value + "";
	}

	/**
	 * return the possible position in the board
	 * 
	 * @return
	 */
	public List<Integer> blankSpace() {
		List<Integer> blanks = new ArrayList<Integer>();

		for (int i = 0; i < this.value.length; i++) {
			if (this.value[i] == i + 1) {
				blanks.add(i);
			}
		}
		return blanks;
	}

	/**
	 * copy the current board and new a same board to apply possible actions
	 * 
	 * @param b
	 * @return
	 */
	public Board copy() {
		// copy the array so the two board won't point to the same array
		int[] tmp = new int[9];
		System.arraycopy(this.value, 0, tmp, 0, this.value.length);

		List<Integer> tmp2 = new ArrayList<Integer>();
		tmp2.addAll(this.blankspace);

		Board b2 = new Board();
		b2.value = tmp;
		b2.filled = this.filled;
		b2.blankspace = tmp2;

		return b2;
	}

	// if a board is full
	public Boolean isFull() {
		return this.filled == ROW * ROW;
	}

	// if a position is filled
	public Boolean isFilled(int position) {
		return !(this.value[position] == position + 1);
	}

	/**
	 * check status of the board
	 * 
	 * @return X if X wins, O if O wins, UTILITY_TIE if is a tie, NONTERMINAL is
	 *         not done
	 */
	int checkWinner() {
		int[] state = this.getValue();

		// check row
		for (int i = 0; i < state.length; i = i + ROW) {
			if (isFilled(i) && state[i] == state[i + 1] && state[i + 1] == state[i + 2]) {
				if (state[i] == AdvancedTicTacToe.O)
					return AdvancedTicTacToe.O;
				else if (state[i] == AdvancedTicTacToe.X)
					return AdvancedTicTacToe.X;
			}
		}

		// check column
		for (int i = 0; i < ROW; i++) {
			if (isFilled(i) && state[i] == state[i + 3] && state[i + 3] == state[i + 6]) {
				if (state[i] == AdvancedTicTacToe.O)
					return AdvancedTicTacToe.O;
				else if (state[i] == AdvancedTicTacToe.X)
					return AdvancedTicTacToe.X;
			}
		}

		// check diagonal
		if ((isFilled(4) && state[0] == state[4] && state[4] == state[8])
				|| (state[4] != 5 && state[2] == state[4] && state[4] == state[6])) {
			if (state[4] == AdvancedTicTacToe.O)
				return AdvancedTicTacToe.O;
			else if (state[4] == AdvancedTicTacToe.X)
				return AdvancedTicTacToe.X;
		}

		// check tie
		if (isFull()) {
			return AdvancedTicTacToe.UTILITY_TIE;
		}

		return AdvancedTicTacToe.NONTERMINAL;
	}
}
