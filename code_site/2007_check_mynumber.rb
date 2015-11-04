# マイナンバーの12桁の個人番号のうち、1桁はチェックデジット。
# チェックデジットを正しく計算するプログラムを作成せよ。
# 計算式については、以下のURLに「検査用数字を算出する算式」として規定されている。
# http://law.e-gov.go.jp/announce/H26F11001000085.html
#
# 標準入力から与えられた内容について、以下の処理を行うこと。
# 1) 数字11桁の場合、マイナンバーのチェックデジット1桁を出力
# 2) 数字12桁の場合、チェックデジットの値が正しければOK、間違っていればNGを出力
# 3) 数字以外が入っている、もしくは11桁や12桁でない場合、Errorと出力


def pn(main_num, digit)
  get_digit = 11 - (digit)
  return main_num.slice(get_digit).to_i
end

def qn(digit)
  if (digit <= 6)
    return digit + 1
  else
    return digit - 5
  end
end

def get_chk_digit(main_num)
  sum = 0

  11.times do |i|
    sum += pn(main_num, i+1) * qn(i+1)
  end

  result = 11 - (sum % 11)
  "get_chk_digit_mae = #{result}"
  result = 0 if sum < 1

  "get_chk_digit = #{result}"
  return result
end

def is_invalid?(my_number)
  if my_number.match(/\D/)
    return true
  elsif my_number.length < 11 ||
        my_number.length > 12
    return true
  else
    return false
  end
end

def check_my_numbar(my_number)
  if is_invalid?(my_number)
    puts "Error"
    return
  end

  if my_number.length == 11
    puts get_chk_digit(my_number)
  else
    main_num  = my_number
    input_check_digit = main_num.slice!(11).to_i

    calced_check_digit = get_chk_digit(main_num)

    if input_check_digit == calced_check_digit
      puts "OK"
    else
      puts "NG"
    end
  end
end




####################
#以下実際の実行処理
####################

file = $stdin
lines = file.readlines
file.close

lines.each do |line|
  check_my_numbar(line.chomp!)
end
