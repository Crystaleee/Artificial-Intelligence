import java.util.ArrayList;
import java.util.List;

/**
 * @author zhubingjing
 * @date 2017年9月20日 下午1:38:55
 */
public class Board {
	private int filled = 0;// the number of position taken
	private int[] value;// the situation on the board
	static final int ROW = 3;

	public Board() {
		this.value = new int[9];

		// initialize the array to show position number
		for (int i = 0; i < this.value.length; i++) {
			this.value[i] = i+1;
		}
	}

	public Board(int[] v) {
		this.value = v;

		for (int i = 0; i < this.value.length; i++) {
			if (this.value[i] != i) {
				this.filled++;
			}
		}
	}

	public int[] getValue() {
		return value;
	}

	public void setValue(int[] value) {
		this.value = value;
	}

	public Board setPosition(int position, int marker) {
		this.getValue()[position] = marker;
		this.setFilled(this.getFilled() + 1);
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

	public void setFilled(int filled) {
		this.filled = filled;
	}

	public static String valueToMarker(int value) {
		if (value == TicTacToe.X)
			return "X";
		if (value == TicTacToe.O)
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
			if (this.value[i] == i+1) {
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
		System.arraycopy(this.getValue(), 0, tmp, 0, this.getValue().length);

		Board b2 = new Board();
		b2.setValue(tmp);
		b2.setFilled(this.getFilled());

		return b2;
	}

}
