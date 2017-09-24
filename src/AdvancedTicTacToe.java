import java.util.ArrayList;
import java.util.List;

/**
 * Advanced TicTacToe with nine regular boards
 * 
 * @author zhubingjing
 * @date 2017年9月20日 下午12:05:44
 */
public class AdvancedTicTacToe {
	static final int X = Integer.MIN_VALUE;
	static final int O = Integer.MAX_VALUE;
	static final int UTILITY_WIN_AI = 1;
	static final int UTILITY_WIN_PLAYER = -1;
	static final int UTILITY_TIE = 0;
	static final int NONTERMINAL = Integer.MIN_VALUE;

	int markerAI;// the marker AI takes
	int markerPlayer;// the marker user takes
	int whoseTurn = X;// Assume X moves first

	NineBoard nineBoard;

	public AdvancedTicTacToe(int marker) {
		this.nineBoard = new NineBoard();

		this.markerAI = marker;
		if (marker == X) {
			this.markerPlayer = O;
			this.nineBoard.setBoardAssigner(markerAI);
		} else {
			this.markerPlayer = X;
			this.nineBoard.setBoardAssigner(markerPlayer);
		}
	}

	void makePlayerMove(Move move) {
		this.nineBoard = this.nineBoard.applyMove(move, markerPlayer);
	}

	Move makeAIMove() {
		Move move = advancedMinimax();

		this.nineBoard = this.nineBoard.applyMove(move, markerAI);

		return move;
	}

	boolean isLegalMove(Move move) {

		if (move == null)
			return false;
		
		int boardNum = move.boardNum;
		int pos = move.position;

		// if board assigner is not player, the board number must be the same as
		// current
		if (!(this.nineBoard.getBoardAssigner() == this.markerPlayer)) {
			if (this.nineBoard.getCurrentBoardNum() != boardNum)
				return false;
		}

		// check the move is [0,8] and the position is not taken
		if (pos < 0 || pos > 8 || this.nineBoard.getBoards()[boardNum].isFilled(pos)) {
			return false;
		}
		return true;
	}

	/**
	 * find the best move in nine boards
	 * 
	 * @return the board number and the best position
	 */
	private Move advancedMinimax() {
		List<Move> possibleMove = possibleMove(this.nineBoard, markerAI);

		Move bestMove = new Move();

		int max = Integer.MIN_VALUE;

		for (Move move : possibleMove) {
			NineBoard b = this.nineBoard.applyMove(move, markerAI);
			int v = advancedMinValue(b);
			System.err.println("The value of action " + (move.boardNum + 1) + (move.position + 1) + " is " + v);

			if (max < v) {
				max = v;
				bestMove = move;
				System.err.println((move.boardNum + 1) + (move.position + 1) + " is a better move!");
			}
		}

		return bestMove;
	}

	private int advancedMinValue(NineBoard nb) {
		int utility = advancedUtility(nb);

		// state is a terminal state
		if (utility != NONTERMINAL)
			return utility;

		// state is not terminal
		int min = Integer.MAX_VALUE;
		List<Move> possibleMove = possibleMove(nb, markerPlayer);

		for (Move move : possibleMove) {
			NineBoard b = nb.applyMove(move, markerPlayer);
			int v = advancedMaxValue(b);
			System.err.println("Move " + move.boardNum + move.position + "'s value is " + v);

			if (min > v) {
				min = v;
			}
		}
		return min;
	}

	private int advancedMaxValue(NineBoard nb) {
		int utility = advancedUtility(nb);

		// state is a terminal state
		if (utility != NONTERMINAL)
			return utility;

		// state is not terminal
		int max = Integer.MIN_VALUE;
		List<Move> possibleMove = possibleMove(nb, markerAI);

		for (Move move : possibleMove) {
			NineBoard b = nb.applyMove(move, markerAI);
			int v = advancedMinValue(b);
			System.err.println("Move " + move.boardNum + move.position + "'s value is " + v);

			if (max < v) {
				max = v;
			}
		}
		return max;
	}

	/**
	 * compute the utility of a single board
	 * 
	 * @param board
	 * @return UTILITY_WIN_AI if AI wins; UTILITU_WIN_PLAYER if player wins;
	 *         UTILITY_TIE if tie; NONTERMINAL if not done
	 * 
	 */
	int utility(Board board) {
		int[] state = board.getValue();

		// check row
		for (int i = 0; i < state.length; i = i + Board.ROW) {
			if (board.isFilled(i) && state[i] == state[i + 1] && state[i + 1] == state[i + 2]) {
				if (state[i] == this.markerAI)
					return UTILITY_WIN_AI;
				else if (state[i] == this.markerPlayer)
					return UTILITY_WIN_PLAYER;
			}
		}

		// check column
		for (int i = 0; i < Board.ROW; i++) {
			if (board.isFilled(i) && state[i] == state[i + 3] && state[i + 3] == state[i + 6]) {
				if (state[i] == this.markerAI)
					return UTILITY_WIN_AI;
				else if (state[i] == this.markerPlayer)
					return UTILITY_WIN_PLAYER;
			}
		}

		// check diagonal
		if ((board.isFilled(4) && state[0] == state[4] && state[4] == state[8])
				|| (state[4] != 5 && state[2] == state[4] && state[4] == state[6])) {
			if (state[4] == this.markerAI)
				return UTILITY_WIN_AI;
			else if (state[4] == this.markerPlayer)
				return UTILITY_WIN_PLAYER;
		}

		// check tie
		if (board.isFull()) {
			return UTILITY_TIE;
		}

		return NONTERMINAL;
	}

	/**
	 * compute the utility of an advanced ttt game
	 * 
	 * @param nineboard
	 * @return UTILITY_WIN_AI if AI wins; UTILITU_WIN_PLAYER if player wins;
	 *         UTILITY_TIE if tie; NONTERMINAL if not done
	 */
	int advancedUtility(NineBoard nineboard) {

		// check each board
		for (int i = 0; i < 9; i++) {

			// skip if the board is a tie board
			if (nineboard.isTieBoard(i))
				continue;

			int utility = utility(nineboard.getSingleBoard(i));

			if (utility == UTILITY_WIN_AI)
				return UTILITY_WIN_AI;
			else if (utility == UTILITY_WIN_PLAYER)
				return UTILITY_WIN_PLAYER;
		}

		if (nineboard.getTieBoardNum() == 9)
			return UTILITY_TIE;

		return NONTERMINAL;
	}

	boolean isGameOver() {
		int utility = advancedUtility(this.nineBoard);
		return checkWinnerAndPrint(utility);
	}

	/**
	 * check who is the winner according to the utility and print information
	 * 
	 * @param utility
	 * @return true if the game is over
	 */
	boolean checkWinnerAndPrint(int utility) {
		if (utility == UTILITY_WIN_AI) {
			System.err.println("HAHAHA I WIN!!!");
			System.err.println("New game!");
			System.err.println();
			return true;
		} else if (utility == UTILITY_WIN_PLAYER) {
			System.err.println("Ooooops YOU WIN!!!");
			System.err.println("New game!");
			System.err.println();
			return true;
		} else if (utility == UTILITY_TIE) {
			System.err.println("Tie!");
			return true;
		} else {
			return false;
		}
	}

	void changeTurn() {
		if (this.whoseTurn == this.markerAI) {
			this.whoseTurn = this.markerPlayer;
		} else {
			this.whoseTurn = this.markerAI;
		}
	}

	/**
	 * return the possible moves for marker of a nine board state
	 * 
	 * @param marker
	 * @return list of possible moves
	 */
	List<Move> possibleMove(NineBoard nb, int marker) {
		List<Move> possibleMove = null;

		if (nb.getBoardAssigner() == this.markerAI)
			possibleMove = nb.possibleMove();
		else {
			Board current = nb.getSingleBoard(nb.getCurrentBoardNum());

			if (current.isFull()) {
				possibleMove = nb.possibleMove();
			} else {
				possibleMove = new ArrayList<Move>();
				for (int pos : current.blankSpace()) {
					possibleMove.add(new Move(nb.getCurrentBoardNum(), pos));
				}
			}
		}
		return possibleMove;
	}
}
