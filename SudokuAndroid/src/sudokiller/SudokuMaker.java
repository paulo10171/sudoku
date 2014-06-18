package sudokiller;

public class SudokuMaker {

	/**
	 * 
	 * @param <code>board</code> the board of Puzzle
	 * @return <code>board</code> the board of Puzzle fill with integer
	 */
	private static boolean tryMakeSudoku(int[][] board) throws Exception {

		boolean guess = false;

		long time = System.currentTimeMillis();

		while (!guess) {

			SudokuBoard b = new SudokuBoard(board);
			SudoKiller solver = new SudokuSolver(b);

			b.makeRandomBoard(20);

			if (System.currentTimeMillis() - time > 5000)
				throw new Exception("Error on create sudoku");

			guess = solver.guess(0, 0);
		}

		return guess;
	}

	public static int[][] makeSudoku(int board[][]) {

		boolean guess = false;

		while (!guess) {
			try {
				guess = SudokuMaker.tryMakeSudoku(board);
			} catch (Exception e) {
				board = new int[9][9];
			}
		}
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
				System.out.print(" " + board[i][j]);

			System.out.println();
		}
		
		return board;
	}
}
