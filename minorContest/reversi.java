/**
 * 8x8の盤上で行われたオセロゲームの棋譜（ログ）が、
 * 以下の形式で標準入力として与えられる。
 * 先頭の数値はオセロゲーム終了までの手の総数。
 * 2行目以降は先頭から石の色、X座標、Y座標がスペース区切りで与えられる。
 *
 * 40
 * B 4 5
 * W 4 4
 * B 4 5
 * W 5 5
 * ...
 *
 * 最終的な黒、白の石の数、勝者の判定を標準出力せよ。
 *
 */





import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {

		// ********変数定義********
		// リバーシの盤面の配列を作成
		int[][] board = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = 0;
			}
		}

		// 盤面の初期配置入力
		board[3][4] = 1;
		board[4][3] = 1;
		board[3][3] = 2;
		board[4][4] = 2;

		// ********入力値の取得********

		// ゲーム終了までの手の合計数を取得
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		int stonePutNum = Integer.parseInt(line);

		// ゲーム実行
		for (int turn = 0; turn < stonePutNum; turn++) {
			// 石の色と置いた位置を取得
			line = br.readLine();
			String[] putStonePos = line.split(" ");

			String stoneColor = putStonePos[0];
			int stonePosX = Integer.parseInt(putStonePos[1]) - 1;
			int stonePosY = Integer.parseInt(putStonePos[2]) - 1;
			

			// プレイヤーの置いた石を配置
			if (stoneColor.equals("B")) {
				board[stonePosY][stonePosX] = 1;
			} else {
				board[stonePosY][stonePosX] = 2;
			}

			/**
			 * リバース可否の判定・リバース実行 checkReverse関数の第4,第5引数に下記のチェック方向を与える (0,1) (1,
			 * 1) (-1, 1) (1,0) center (-1,0) (1,-1) (-1,-1) (0,-1)
			 */

			board = checkReverse(board, stonePosX, stonePosY, 0, 1, stoneColor); // 上方向のチェック
			board = checkReverse(board, stonePosX, stonePosY, -1, 1, stoneColor); // 右斜め上方向のチェック
			board = checkReverse(board, stonePosX, stonePosY, -1, 0, stoneColor); // 右方向のチェック
			board = checkReverse(board, stonePosX, stonePosY, -1, -1,
					stoneColor); // 右斜め下方向のチェック
			board = checkReverse(board, stonePosX, stonePosY, 0, -1, stoneColor); // 下方向のチェック
			board = checkReverse(board, stonePosX, stonePosY, 1, -1, stoneColor); // 左斜め下方向のチェック
			board = checkReverse(board, stonePosX, stonePosY, 1, 0, stoneColor); // 左方向のチェック
			board = checkReverse(board, stonePosX, stonePosY, 1, 1, stoneColor); // 左斜め上方向のチェック

		}

		printResult(board);

	}

	/**
	 * プレイヤーが石を置いた後に、 裏返せる石があるか判定・実行する
	 * 
	 * @param int board ボードの状態を表現した2次元配列
	 * @param int checkStartPosX プレイヤーが置いた石のX座標
	 * @param int checkStartPosY プレイヤーが置いた石のY座標
	 * @param int angleX 裏返せる石の有無の判定方向（全8方向）の指定変数X座標
	 * @param int angleY 裏返せる石の有無の判定方向（全8方向）の指定変数Y座標
	 * @param string
	 *            myColor プレイヤーが置いた石の色(B:黒石 or W：白石)
	 * 
	 * @return int board ボードの状態を表現した2次元配列（リバース判定・実行済み）
	 * 
	 */

	private static int[][] checkReverse(int[][] board, int checkStartPosX,
			int checkStartPosY, int angleX, int angleY, String myColor) {

		int putColor;

		// 黒石を1, 白石を2に変換
		if (myColor.equals("B")) {
			putColor = 1;
		} else {
			putColor = 2;
		}

		// リバース対象候補マスの一時格納配列を初期化
		ArrayList<int[]> reverse = new ArrayList<int[]>();

		// チェック対象を進行方向に1マス進める
		int checkPosX = checkStartPosX - angleX;
		int checkPosY = checkStartPosY - angleY;

		while (true) {

			// チェック対象マスが盤面外に出たらチェック終了
			if (checkPosX < 0 || checkPosX > 7 || checkPosY < 0
					|| checkPosY > 7) {
				return board;
			}

			// 0(石が無いマス)があれば、リバースはできないのでチェック終了
			if (board[checkPosY][checkPosX] == 0) {
				return board;
			}

			// 自分と同じ色の石があればチェックループ終了
			if (board[checkPosY][checkPosX] == putColor) {
				break;
			}

			// リバース対象候補を保持
			int[] checkPos = { checkPosY, checkPosX };
			reverse.add(checkPos);
			// reverse.add(board[checkPosY][checkPosX]);

			// チェック対象を進行方向に1マス進む
			checkPosX = checkPosX - angleX;
			checkPosY = checkPosY - angleY;
		}

		// リバース対象マスを裏返す
		if (reverse.size() > 0) {
			for (int i = 0; i < reverse.size(); i++) {
				board[reverse.get(i)[0]][reverse.get(i)[1]] = putColor;
			}
		}

		return board;

	}

	/**
	 * 勝敗結果の出力をする
	 * 
	 * @param int board ボードの状態を表現した2次元配列配列
	 * 
	 */
	private static void printResult(int[][] board) {
		int brackNum = 0;
		int whiteNum = 0;

		for (int[] boardY : board) {
			for (int boardX : boardY) {
				if (boardX == 1) {
					brackNum++;
				} else if (boardX == 2) {
					whiteNum++;
				}
			}
		}

		String winner;
		if (brackNum > whiteNum) {
			winner = " The black won!";
		} else if (brackNum < whiteNum) {
			winner = " The white won!";
		} else {
			winner = " Draw!";
		}

		// echo sprintf("%02d",brackNum) . "-" . sprintf("%02d", whiteNum) .
		// winner ."\n";
		System.out.printf("%02d-%02d%s", brackNum, whiteNum, winner);

	}
}
