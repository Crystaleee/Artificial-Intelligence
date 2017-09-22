import java.util.Scanner;

import org.omg.CosNaming.IstringHelper;

/**
 * @author zhubingjing
 * @date 2017年9月20日 下午2:20:12
 */
public class Main {
	

	public static void main(String[] args) {
//		TicTacToe ttt =new TicTacToe(TicTacToe.O);
//		int[] v = {1,1,-1,-1,-1,1,-1-1,1};
//		Board b =new Board(v);
//		int i = ttt.isTerminal(v);
//		System.out.println(i);
		TicTacToe ttt = null;
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.err.println("Hello there! Tic-Tac-Toe time! Do you want to play X or O? (X/O)");

			// assign marker for user and AI
			ttt = assignMarker(ttt, scanner);
			if (ttt == null)
				continue;

			// game on
			while (!ttt.isGameOver()) {
				System.err.println();

				if (ttt.whoseTurn == ttt.markerAI) {// AI move

					moveAIandPrint(ttt);

				} else {// user move

					System.err.println("Your turn! Please enter a position: (1-9)");
					int move = parseInput(scanner.next());

					// check legal move
					if (!ttt.isLegalMove(move)) {
						System.err.println("Illegal move!");
						continue;
					}

					moveUserAndPrint(ttt, move);
				}
				ttt.whoseTurn = -ttt.whoseTurn;
			}
		}
	}

	/**
	 * assign marker X/O to user/AI
	 * 
	 * @param ttt
	 * @param scanner
	 * @return marker assigned ttt; null if wrong input
	 */
	private static TicTacToe assignMarker(TicTacToe ttt, Scanner scanner) {
		String answer;
		answer = scanner.next().toUpperCase().trim();
		if (answer.equals(new String("X"))) {
			ttt = new TicTacToe(TicTacToe.O);

		} else if (answer.equals(new String("O"))) {
			ttt = new TicTacToe(TicTacToe.X);
		} else {
			System.err.println("Wrong input!");
			return null;
		}
		return ttt;
	}

	private static int parseInput(String input) {
		int move = -1;
		try {
			move = Integer.parseInt(input);
		} catch (Exception e) {

		}
		return move;
	}

	private static void moveUserAndPrint(TicTacToe ttt, int move) {
		ttt.makePlayerMove(move);

		// print new board
		ttt.board.print();
	}

	private static void moveAIandPrint(TicTacToe ttt) {
		int move = ttt.makeAIMove();
		System.err.println("I've done my move:");
		System.out.println(move + 1);

		// print new board
		ttt.board.print();
	}

}
