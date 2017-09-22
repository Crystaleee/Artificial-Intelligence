import java.util.List;

/**
 * @author zhubingjing
 * @date 2017年9月20日 下午12:05:44
 */
public class TicTacToe {
	static final int X = 1;
	static final int O = -1;
	static final int UTILITY_WIN_AI = 1;
	static final int UTILITY_WIN_PLAYER = -1;
	static final int UTILITY_TIE = 0;
	static final int NONTERMINAL = Integer.MIN_VALUE;

	int markerAI;// the marker AI takes
	int markerPlayer;// the marker user takes
	int whoseTurn = X;// Assume X moves first
	Board board;

	public TicTacToe(int marker) {
		this.board = new Board();
		// int[] v = {-1,1,0,-1,1,1,0,-1,0};
		// this.board = new Board(v);
		this.markerAI = marker;
		this.markerPlayer = -markerAI;
	}

	boolean isLegalMove(int move) {
		// check the move is [1,9] and the position is not taken
		if (move <= 0 || move > 9 || Board.valueToMarker(this.board.getValue()[move - 1]) != null) {
			return false;
		}

		return true;
	}

	void makePlayerMove(int move) {
		this.board.setPosition(move - 1, markerPlayer);
	}

	int makeAIMove() {

		// java.util.Random random = new java.util.Random();// 定义随机类
		// int i;
		// while(true){
		// i = random.nextInt(9);
		// if(isLegalMove(i+1)){
		// break;
		// }
		// }
		//
		// this.board.setPosition(i, markerAI);
		// return i;

		int move = minimax(board);

		this.board.setPosition(move, markerAI);
		return move;
	}

	private int minimax(Board board) {
		List<Integer> possibleActions = board.blankSpace();
		int bestMove = -1;
		int max = Integer.MIN_VALUE;

		for (int i = 0; i < possibleActions.size(); i++) {

			Board b = board.copy();
			b.setPosition(possibleActions.get(i), this.markerAI);

			int v = minValue(b);
			System.err.println("The value of action " + (possibleActions.get(i) + 1) + " is " + v);

			if (max < v) {
				max = v;
				bestMove = possibleActions.get(i);
				System.err.println(possibleActions.get(i) + " is a better move!");
			}
		}
		return bestMove;
	}

	private int minValue(Board board) {
		int utility = isTerminal(board);

		// state is a terminal state
		if (utility != NONTERMINAL) {
			return utility;
		}

		// state is not terminal
		List<Integer> possibleActions = board.blankSpace();

		int min = Integer.MAX_VALUE;

		for (int i : possibleActions) {
			Board b2 = board.copy();
			b2.setPosition(i, this.markerPlayer);

			int v = maxValue(b2);
			if (min > v) {
				min = v;
			}
		}

		return min;
	}

	private int maxValue(Board board) {
		int utility = isTerminal(board);

		// state is a terminal state
		if (utility != NONTERMINAL) {
			return utility;
		}

		// state is not terminal
		List<Integer> possibleActions = board.blankSpace();

		int max = Integer.MIN_VALUE;

		for (int i : possibleActions) {
			Board b2 = board.copy();
			b2.setPosition(i, this.markerAI);

			int v = minValue(b2);
			if (max < v) {
				max = v;
			}
		}
		return max;
	}

	/**
	 * 
	 * @param state
	 * @return the utility if game is over, or null if not
	 */
	int isTerminal(Board board) {
		int[] state = board.getValue();

		// check row
		for (int i = 0; i < state.length; i = i + Board.ROW) {
			if (state[i] != 0 && state[i] == state[i + 1] && state[i + 1] == state[i + 2]) {
				if (state[i] == this.markerAI)
					return UTILITY_WIN_AI;
				else if (state[i] == this.markerPlayer)
					return UTILITY_WIN_PLAYER;
			}
		}

		// check column
		for (int i = 0; i < Board.ROW; i++) {
			if (state[i] != 0 && state[i] == state[i + 3] && state[i + 3] == state[i + 6]) {
				if (state[i] == this.markerAI)
					return UTILITY_WIN_AI;
				else if (state[i] == this.markerPlayer)
					return UTILITY_WIN_PLAYER;
			}
		}

		// check diagonal
		if ((state[4] != 0 && state[0] == state[4] && state[4] == state[8])
				|| (state[4] != 0 && state[2] == state[4] && state[4] == state[6])) {
			if (state[4] == this.markerAI)
				return UTILITY_WIN_AI;
			else if (state[4] == this.markerPlayer)
				return UTILITY_WIN_PLAYER;
		}

		// check tie
		if (board.getFilled() == Board.ROW * Board.ROW) {
			return UTILITY_TIE;
		}

		return NONTERMINAL;
	}

	boolean isGameOver() {
		int utility = isTerminal(this.board);
		return checkWinnerAndPrint(utility);
	}

	/**
	 * check who is the winner according to the utility
	 * 
	 * @param utility
	 * @return true if the game is over
	 */
	private boolean checkWinnerAndPrint(int utility) {
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
			System.err.println("Nobody wins!");
			System.err.println("New game!");
			return true;
		} else {
			return false;
		}
	}

}
