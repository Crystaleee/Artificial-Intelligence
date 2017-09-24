import java.util.ArrayList;
import java.util.List;

/**
 * @author zhubingjing
 * @date 2017年9月21日 下午11:03:21
 */
public class NineBoard {
	private Board[] boards;
	private List<Integer> tieBoard; // the list of tie boards
	private int currentBoardNum = -1;// the board the first player just played
										// on (0-8)
	private int boardAssigner; // who assign which board to play
	static final int ROW = 3;

	public NineBoard() {
		this.boards = new Board[9];
		for (int i = 0; i < this.boards.length; i++) {
			this.boards[i] = new Board();
		}

		this.tieBoard = new ArrayList<Integer>();
	}

	public Board getSingleBoard(int i) {
		return boards[i];
	}

	public Board[] getBoards() {
		return boards;
	}

	public void setBoards(Board[] boards) {
		this.boards = boards;
		
		for(int i =0;i<boards.length;i++){
			if(boards[i].isFull()){
				tieBoard.add(i);
			}
		}
	}

	private NineBoard setPosition(int boardNum, int position, int marker) {
		Board b = this.boards[boardNum];
		b.setPosition(position, marker);

		return this;
	}

	public int getTieBoardNum() {
		return this.tieBoard.size();
	}

	public void addTieBoard(int index) {
		this.tieBoard.add(index);
	}

	public int getCurrentBoardNum() {
		return currentBoardNum;
	}

	public void setCurrentBoardNum(int currentBoardNum) {
		this.currentBoardNum = currentBoardNum;
	}

	public int getBoardAssigner() {
		return boardAssigner;
	}

	public void setBoardAssigner(int boardAssigner) {
		this.boardAssigner = boardAssigner;
	}

	public void print() {
		System.err.println("Current board:");
		for (int i = 0; i < this.boards.length; i = i + ROW) {

			Board b0 = this.boards[i];
			Board b1 = this.boards[i + 1];
			Board b2 = this.boards[i + 2];

			for (int j = 0; j < 9; j += ROW) {
				System.err
						.println(Board.valueToMarker(b0.getValue()[j]) + "\t"
								+ Board.valueToMarker(b0.getValue()[j + 1]) + "\t" + Board.valueToMarker(
										b0.getValue()[j + 2])
								+ "\t\t" + Board.valueToMarker(b1.getValue()[j]) + "\t"
								+ Board.valueToMarker(b1.getValue()[j + 1]) + "\t"
								+ Board.valueToMarker(b1.getValue()[j + 2]) + "\t\t"
								+ Board.valueToMarker(b2.getValue()[j]) + "\t"
								+ Board.valueToMarker(b2.getValue()[j + 1]) + "\t"
								+ Board.valueToMarker(b2.getValue()[j + 2]));
			}
			System.err.println();
		}
	}

	public List<Move> possibleMove() {
		List<Move> possibleMove = new ArrayList<Move>();

		for (int i = 0; i < this.boards.length; i++) {
			if (this.boards[i].isFull())
				continue;

			for (int j : this.boards[i].blankSpace()) {
				possibleMove.add(new Move(i, j));
			}
		}
		return possibleMove;
	}

	/**
	 * if boards[i] is a tie board
	 * 
	 * @param i
	 * @return true if the board is tie
	 */
	public boolean isTieBoard(int i) {
		return (this.tieBoard.contains(i));
	}

	public NineBoard copy() {
		NineBoard nb = new NineBoard();

		for (int i = 0; i < 9; i++) {
			nb.boards[i] = this.boards[i].copy();
		}
		nb.tieBoard.addAll(this.tieBoard);
		nb.boardAssigner = this.boardAssigner;
		nb.currentBoardNum = this.currentBoardNum;

		return nb;
	}

	/**
	 * 
	 * @param move
	 * @param marker
	 * @return return a different nineboard after applying move on this
	 *         nineboard
	 */
	public NineBoard applyMove(Move move, int marker) {
		NineBoard nb = this.copy();

		if (nb.boardAssigner == marker)
			nb.currentBoardNum = move.boardNum;

		nb.setPosition(move.boardNum, move.position, marker);

		// change board assigner if necessary
		Board b = nb.boards[move.boardNum];
		if (b.isFull()) {
			nb.changeBoardAssigner();
		}

		// add tie board if there's a new tie board
		if (b.checkWinner() == TicTacToe.UTILITY_TIE) {
			nb.addTieBoard(move.boardNum);
		}

		return nb;
	}

	public void changeBoardAssigner() {
		if (this.boardAssigner == AdvancedTicTacToe.X) {
			this.boardAssigner = AdvancedTicTacToe.O;
		} else {
			this.boardAssigner = AdvancedTicTacToe.X;
		}
	}
}
