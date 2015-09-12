/** -----------------
 * 下記のような標準入力を渡される
 *
 * 5 4
 * __\_
 * _\__
 * ____
 * _\_/
 * \//_
 *
 * 上記は4x4マスに内部を区切った箱を上から見ている状態を表し、
 * 左上を中心座標0,0とする。
 * 箱の中の座標0,0の地点に対し、箱の外側から左側から箱の内部に
 * 直線の光が照射される。
 * 箱の内部の / 及び \ は 45度及び-45度で設置された鏡を表し、
 * 光が鏡に当たると、光の法則に沿って90度方向を変えた反射が起こる。
 * _(アンダーバー)は鏡が無いマス目を表す。
 * 上述の例のように、1行目に箱の高さと幅のマス目数が与えられ、
 * その後、箱の内部の詳細が_\/3つの記号で渡される。
 * この時、箱の内部に光が照射され、箱の外部に出て行くまでに、
 * 経由したマス目の数を標準出力せよ。
 *
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        
        //********** 変数定義 **********
	    // 現在のビームの所在区画座標
	    int nowX = 0;
	    int nowY = 0;
	    
	    // 通過した区画のカウント変数
	    int count = 0;
	    
        // ビームの進行方向の初期値
        /* 左から進んできたビーム：L (from left)
           右から進んできたビーム：R (from right)
           上から進んできたビーム：T (from top)
           下から進んできたビーム：B (from bottom) */
        String beamDirection = "L";
        
        // 現在のビームの所在区画座標
        String checkMirror;
        
        
	
	    //********** 入力処理 **********
        // 箱の高さと幅を取得
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		String[] boxSize = line.split(" ");
		
		int boxHeight = Integer.parseInt(boxSize[0]);
		int boxWidth = Integer.parseInt(boxSize[1]);
		
		// 箱の構造データの取得
		String[][] box = new String[boxHeight][boxWidth];
		for (int i=0; i < boxHeight; i++) {
		    line = br.readLine();
		    box[i] = line.split("");
		}
		
		
		
		//********** ビームの通過シミュレート **********
		
		while(true) {
		    
		    // 通過区画数のカウント
		    count++;
		    
		    // 現在のビームの所在区画座標の鏡の有無確認
		    checkMirror = box[nowY][nowX];
		    
		    // ビームの反射方向・反射後の区画座標算出
    		if (checkMirror.equals("/")) {
    			
    			switch(beamDirection) {
    				case "L":
    					nowY--;
    					beamDirection = "B";
    					break;
    				case "R":
    					nowY++;
    					beamDirection = "T";
    					break;
    				case "T":
    					nowX--;
    					beamDirection = "R";
    					break;
    				case "B":
    					nowX++;
    					beamDirection = "L";
    					break;
    			}
    			
    		} else if (checkMirror.equals("\\")) {
    			
    			switch(beamDirection) {
    				case "L":
    					nowY++;
    					beamDirection = "T";
    					break;
    				case "R":
    					nowY--;
    					beamDirection = "B";
    					break;
    				case "T":
    					nowX++;
    					beamDirection = "L";
    					break;
    				case "B":
    					nowX--;
    					beamDirection = "R";
    					break;
    			}
    			
    		} else {
    			switch(beamDirection) {
    				case "L":
    					nowX++;
    					break;
    				case "R":
    					nowX--;
    					break;
    				case "T":
    					nowY++;
    					break;
    				case "B":
    					nowY--;
    					break;
    			}
    
    		}
    		
    		// ビームが箱の外に出たらシミュレート終了
    		if (nowX < 0 || nowX >= boxWidth ||
    			nowY < 0 || nowY >= boxHeight) {
    			break;
    		}
		    
		}
		
		//********** 出力処理 **********
    	// ビームが通過した区画数合計を出力
    	System.out.println(count);
		
		
    }
}
