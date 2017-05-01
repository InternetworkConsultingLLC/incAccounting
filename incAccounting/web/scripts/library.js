function pad(num, size) {
    var s = num + "";
    while(s.length < size) 
		s = "0" + s;
    return s;
}
function round (number, precision) {
    var roundedNum = (Math.round(number * 10 ^ precision) / 10 ^ precision).toFixed(precision);
    return roundedNum;
}