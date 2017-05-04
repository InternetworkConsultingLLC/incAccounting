function pad(num, size) {
    var s = num + "";
    while(s.length < size) 
		s = "0" + s;
    return s;
}
function round (number, precision) {
    var multiplier = Math.pow( 10, precision );
    var multipliedNum = number * multiplier;
    var roundedNum = Math.round(multipliedNum);
    var rootNumBeforeRound = roundedNum / multiplier;    
    var toFixedRoundedNum = rootNumBeforeRound.toFixed(precision);
    return toFixedRoundedNum;
}