var startTime = 0;
var endTime = 0;
var timerStarted = false;
function startTimer(){
	if(!timeStarted){
    	startTime = Date.now();
    	document.getElementById("Time this session").value = "Timer started";
    	timerStarted = true;
    }
}

function endTimer(){
    if(startTime != 0 && timerStarted){
        endTime = Date.now();
    }
    var timePlayedMil = endTime - startTime;
    var timePlayedSec = timePlayedMil / 1000;
    var timePlayedMin = timePlayedSec / 60;
    var timePlayedHours = timePlayedSec / 60;
    document.getElementById("Time this session").value = timePlayedHours;
    timerStarted = false;
}