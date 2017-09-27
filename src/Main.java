import java.util.Scanner;

/**
 * @author zhubingjing
 * @date 2017年9月20日 下午2:20:12
 */
public class Main {
	
	public static void main(String[] args) {

		AdvancedTicTacToe ttt = null;
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.err.println("Hello there! Tic-Tac-Toe time! Do you want to play X or O? (X/O)");

			// assign marker for user and AI
			ttt = initialize(ttt, scanner);
			if (ttt == null)
				continue;

			// game on			
			while (!ttt.isGameOver()) {
				System.err.println();

				if (ttt.whoseTurn == ttt.markerAI) {// AI move

					moveAIandPrint(ttt);

				} else {// user move

					System.err.println("Your turn! Please enter two numbers (first for board, second for position):");
					Move move = parseInput(scanner.next());

					// check legal move
					if (!ttt.isLegalMove(move)) {
						System.err.println("Illegal move!");
						continue;
					}

					moveUserAndPrint(ttt, move);
				}

				// change turn
				ttt.changeTurn();

			}
		}
	}

	/**
	 * Initialize the TTT: assign marker X/O to user/AI; assign boardAssigner
	 * 
	 * @param ttt
	 * @param scanner
	 * @return initialized ttt; null if wrong input
	 */
	private static AdvancedTicTacToe initialize(AdvancedTicTacToe ttt, Scanner scanner) {
		String answer;
		answer = scanner.next().toUpperCase().trim();

		if (answer.equals(new String("X"))) {
			ttt = new AdvancedTicTacToe(AdvancedTicTacToe.O);
		} else if (answer.equals(new String("O"))) {
			ttt = new AdvancedTicTacToe(AdvancedTicTacToe.X);
		} else {
			System.err.println("Wrong input!");
			return null;
		}
		return ttt;
	}

	/**
	 * 
	 * @param input
	 * @return Move(boardNum, pos); NULL if illegal
	 */
	private static Move parseInput(String input) {
		int boardNum = -1;
		int move = -1;
		try {
			// if less than 2 digits return -1
			if (input.length() < 2)
				return null;

			// the first char
			boardNum = Integer.parseInt(input.substring(0, 1)) - 1;
			// the last char
			move = Integer.parseInt(input.substring(input.length() - 1)) - 1;

		} catch (Exception e) {

		}
		return new Move(boardNum, move);
	}

	private static void moveUserAndPrint(AdvancedTicTacToe ttt, Move move) {
		ttt.makePlayerMove(move);

		// print new board
		ttt.nineBoard.print();
	}

	private static void moveAIandPrint(AdvancedTicTacToe ttt) {
		Move move = ttt.makeAIMove();
		System.err.println("I've done my move:");
		System.out.println((move.boardNum + 1) + "" + (move.position + 1));

		// print new board
		ttt.nineBoard.print();
	}

}
