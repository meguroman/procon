<?php

/***
 * あみだくじをシュミレートし、当たりになるくじは左から何番目かを標準出力せよ。
 * 入力形式は標準入力として以下のように与えられるものとする。
 *
 * 5 5 8
 * 3 3 4
 * 1 3 2
 * 4 2 2
 *
 * 入力値の一行目は左から、縦線の長さ[cm]、縦線の本数、ヨコ線の本数が与えられる。
 * 二行目以降は、左から、横線の始点となる縦線の番号X、Xの縦線の上端からの位置[cm]、
 * 横線の終点となる縦線(Xの縦線の一つ右側の縦線)の上端からの位置[cm]を表す。
 *
 *
 */








//*** 入力値取得処理
while($input = trim(fgets(STDIN))) {
    $amida[] = explode(" ", $input);
}

//print_r($amida);

$rowLength = $amida[0][0];  // 縦棒の長さ取得
$colLength = $amida[0][1];  // 縦棒の数取得
$barNum = $amida[0][2];     // 横棒の数取得

// 左の棒を示す0と右の棒を示す1を定数宣言
define("LEFT_BAR","0");
define("RIGHT_BAR","1");

// 各縦棒ごとに横棒の接続位置をまとめる
for ($i = 1; $i <= $barNum; $i++) {
    $barLeft = $amida[$i][0];
    $barLeftPos = $amida[$i][1];
    $barRight = $amida[$i][0] + 1;
    $barRightPos = $amida[$i][2];
    
    // バーの右側に生えている横棒
    $bar[$barLeft-1][] = array($barLeftPos, $barRightPos, RIGHT_BAR);
    
    // バーの左側に生えている横棒
    $bar[$barRight-1][] = array($barRightPos, $barLeftPos, LEFT_BAR);
}


print_r($bar);


//*** あみだくじをゴールから逆順にたどる

// 初期位置を定義（左端の縦棒の一番下）
$barPos = 0;
$lengthPos = $rowLength;

while(true){
    
    echo "現在は、枝番号" . $barPos . "現在の高さ". $lengthPos ."cmです".PHP_EOL;
    
    $nextBarKey = -1;
    
    // もし、その縦棒に横棒が一つもなければ、検索処理終了
    if (count($bar[$barPos]) == 0) {
        echo "枝が無いので検索終了です".PHP_EOL;
        break;
    }
    
    // 横棒を探して、現在地から見て次の横棒で移動をする
    for($i=0; $i < count($bar[$barPos]); $i++){
        echo "枝番号" . $barPos . "の枝".$i."番目を見ています".PHP_EOL;
        if ($bar[$barPos][$i][0] < $lengthPos) {
            echo "　　枝".$i."は、".$bar[$barPos][$i][0]."cmなので、現在地よりも上にあります".PHP_EOL;
            if ($nextBarKey == -1) $nextBarKey = $i;
            echo "　　続いて、".$bar[$barPos][$nextBarKey][0]."と".$bar[$barPos][$i][0]."を比較します".PHP_EOL;
            if ($bar[$barPos][$nextBarKey][0] < $bar[$barPos][$i][0]) {
                echo "　　枝".$i."は、".$bar[$barPos][$i][0]."cmと、今のところ最寄りなので、次の枝として定義します".PHP_EOL;
                $nextBarKey = $i;
            }
            echo "　　次の枝は、枝番号".$barPos."の".$nextBarKey."番目の枝です".PHP_EOL;
        }
    }
    
    // もし、その縦棒の一番上の枝よりも現在地が上であれば、検索処理終了
    if ($nextBarKey == -1) {
        echo "現在地より上の枝がないので検索終了".PHP_EOL;
        break;
    }
    
    echo "　最終的な次の枝は、枝番号".$barPos."の".$nextBarKey."番目の枝です".PHP_EOL;
    
    $lengthPos = $bar[$barPos][$nextBarKey][1];
    if ($bar[$barPos][$nextBarKey][2] == 0) {
        $barPos = $barPos - 1;
    } else {
        $barPos = $barPos + 1;
    }
    
    echo "次の枝に行きます。次の枝は、".$barPos."番目の枝の".$lengthPos."cmの位置です".PHP_EOL.PHP_EOL;
    
}

echo $barPos+1;


?>
