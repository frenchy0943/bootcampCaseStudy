var TLOURating;
var BL2Rating;
var FF7Rating;
var sortedArray = [];
function getRatings(){
    var TLOU = document.getElementById("TLOUrating");
    if(TLOU.value == "N/A"){ 
        TLOURating = 0;
    }else{
        TLOURating = parseInt(TLOU.value);
    }

    var BL2 = document.getElementById("BL2rating");
    if(BL2.value == "N/A"){
        BL2Rating = 0;
    }else{
        BL2Rating = parseInt(BL2.value);
    }

    var FF7 = document.getElementById("FF7Rrating");
    if(FF7.value == "N/A"){
        FF7Rating = 0;
    }else{
        FF7Rating = parseInt(FF7.value);
    }
    sortRatings();
}

function sortRatings(){
    sortedArray = [];
    if(TLOURating >= BL2Rating && TLOURating >= FF7Rating){
        sortedArray.push("The Last of Us");
        if(BL2Rating >= FF7Rating){
            sortedArray.push("Borderlands 2");
            sortedArray.push("Final Fantasy 7 Remake");
        }else{
            sortedArray.push("Final Fantasy 7 Remake");
            sortedArray.push("Borderlands 2");
        }
    }else if(BL2Rating >= TLOURating && BL2Rating >= FF7Rating){
        sortedArray.push("Borderlands 2");
        if(TLOURating >= FF7Rating){
            sortedArray.push("The Last of Us");
            sortedArray.push("Final Fantasy 7 Remake");
        }else{
            sortedArray.push("Final Fantasy 7 Remake");
            sortedArray.push("The Last of Us");
        }
    }else{
        sortedArray.push("Final Fantasy 7 Remake");
        if(TLOURating >= BL2Rating){
            sortedArray.push("The Last of Us");
            sortedArray.push("Borderlands 2");
        }else{
            sortedArray.push("Borderlands 2");
            sortedArray.push("The Last of Us");
        }
    }
    printGames();
}

function printGames(){
    var gamesInOrder = "<h2>In order from best to worst, this is how you rated the games:";
    for(i = 0; i < sortedArray.length; i++){
        gamesInOrder += "<h5>" + (i+1) + ". " + sortedArray[i] + "</h5>";
    }
    document.getElementById("results").innerHTML = gamesInOrder + "</h2>";
}