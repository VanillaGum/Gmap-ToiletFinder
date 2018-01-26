markers = [];
requestMarkers = [];
function markerInit() {
//    Method for initialising the markerlist
}
function clearMarkerList() {

    markers.forEach(function(marker){
        marker.setMap(null);
    });
    markers=[];
}
function createSuggestedInfoWindow(id,rating,genderM) {
    document.getElementById("formSubmitToilet:genderM").value = 0;
    document.getElementById("formSubmitToilet:genderF").value = 0;
    var mClass = "";
    var fClass = "";
    if(genderM == 2) {
        mClass = "maleToiletSelectIcon-selected";
        fClass = "femaleToiletSelectIcon-selected";
    }else if(genderM== 1){
        mClass = "maleToiletSelectIcon-selected";
        fClass = "femaleToiletSelectIcon-unselected";
    }else if (genderM == 0){
        mClass = "maleToiletSelectIcon-unselected";
        fClass = "femaleToiletSelectIcon-selected";
    }
    var content = '<div>' +
        '<div id="suggested'+id+'" class="infowindow-size">' +
        'rating='+rating +
        ' genderm='+ genderM +
        'upvoted=true' +
        '<img class="'+mClass+'" src="images/male_icon.png" alt="maleToiletIcon" width="23.2" height="62.6px"/>' +
        '<img class="'+fClass+'" src="images/female_icon.png" alt="femaleToiletIcon" width="31px" height="62.6"/>' +
        '</div>' +
        '</div>';
    return content;
}
function createSuggestionInfoWindow(id,rating,genderM,toiletId) {
    var mClass = "";
    var fClass = "";
    if(genderM == 2) {
        mClass = "maleToiletSelectIcon-selected";
        fClass = "femaleToiletSelectIcon-selected";
    }else if(genderM== 1){
        mClass = "maleToiletSelectIcon-selected";
        fClass = "femaleToiletSelectIcon-unselected";
    }else if (genderM == 0){
        mClass = "maleToiletSelectIcon-unselected";
        fClass = "femaleToiletSelectIcon-selected";
    }
    var content ='<div>' +
        '<div id="suggested'+id+'" class="infowindow-size">' +
        'rating='+rating +
        ' genderm='+ genderM +
        '<button onclick="addReviewScreen('+id+')"></button>' +
        '<img class="upvote'+id+' upvote-unselected" src="images/upvote.png" alt="upvoteIcon" width="20.48" height="20.58" onclick="upvoteToiletSuggestion('+id+','+toiletId+')"/>' +
        '<img class="'+mClass+'" src="images/male_icon.png" alt="maleToiletIcon" width="23.2" height="62.6px"/>' +
        '<img class="'+fClass+'" src="images/female_icon.png" alt="femaleToiletIcon" width="31px" height="62.6"/>' +
        '</div>' +
        '</div>';
    return content;
}
//Upvoting Toilet Suggestion
function upvoteToiletSuggestion(id,toilet_id) {
    //Track Upvote For Checking If Upvoted Or Cnacel Upvote
    //-1 = Cancel
    //1 = Upvote
    var defaultClass = "upvote"+id+" ";
    var unselected = "upvote-unselected";
    var selected = "upvote-selected";
    var upvoteElementList = document.getElementsByClassName("upvote"+id+" upvote-unselected");
    var upvoteElement = document.getElementsByClassName("upvote"+id);
    if (upvoteElementList.length > 0) {
        upvoteElement[0].className = defaultClass +  selected;
        document.getElementById("formSubmitToilet:upvote").value = 1;
        upvoteToiletIdChange(toilet_id);

    }else {
        upvoteElement[0].className = defaultClass + unselected;
        document.getElementById("formSubmitToilet:upvote").value = 0;
        upvoteToiletIdChange(toilet_id);
    }
}

//Change upvotedToiletId
function upvoteToiletIdChange(id) {
    document.getElementById("formSubmitToilet:upvoteToiletId").value = id;
    upvoteSubmit();
}


//Reviewing Toilets
function addReviewScreen(uniqueId) {
    var unselected="unselected-Overlay";
    var selected="selected-Overlay";
    var screen = '<div id="screen'+uniqueId+'" class="middleDiv">' +
        '<img src="images/toilet.png" class="icon1 icon1-unselected" width="51.2" height="51.2" onclick="icon1()"/>' +
        '<img src="images/slippery.png" class="icon3 icon3-unselected" width="51.2" height="51.2" onclick="icon3()"/>' +
        '<img src="images/cockroach.png" class="icon4 icon4-unselected" width="40" height="40" onclick="icon4()"/>' +
        '<img src="images/smelly.png" class="icon5 icon5-unselected" width="51.2" height="51.2" onclick="icon5()"/>' +
        '</div>';
    document.getElementById("greyOverlay").className = selected;
    $('#greyOverlay').append(screen);
}
function removeScreen(uniqueId) {
    $('#greyOverlay').remove("#screen"+uniqueId+"")
}

function addNewMarker(lat,lng,image) {
    newMarker = null;
    newMarker = new google.maps.Marker({
        position:{lat:lat,lng:lng},
        map:mapdis,
        title:"User Loc",
        icon:image
    });
    markers.push(newMarker);

    markers.push(new google.maps.Marker({
        
    }))
    var window = '<div>' +
        '   <p>Select:</p>' +
        '</div>'
}

//Icon1 = Clean Toilet
function icon1() {
    var iconElementCheck = document.getElementsByClassName("icon1-unselected");
    var iconElementCheck2 = document.getElementsByClassName("icon1-selected1");
    var iconElement = document.getElementsByClassName("icon1");
    var unselected = "icon1 icon1-unselected";
    var selected1 = "icon1 icon1-selected1";
    var selected2 = "icon1 icon1-selected2";
    if (iconElementCheck.length === 1) {
        //Current unselected
        iconElement[0].className = selected1;
        iconElement[0].src="images/toilet_silver.png";
    }else if(iconElementCheck2.length > 0) {
        //Currently Selected 1
        iconElement[0].className = selected2;
        iconElement[0].src="images/toilet_gold.png";
    }else {
        //Currently Selected 2
        iconElement[0].className = unselected;
        iconElement[0].src="images/toilet.png";
    }
}
//Icon 2 = Rate Dirty Environment
function icon2() {
    var iconElementCheck = document.getElementsByClassName("icon2-unselected");
    var iconElement = document.getElementsByClassName("icon2");
    var unselected = "icon2 icon2-unselected unselected";
    var selected = "icon2 icon2-selected selected";
    if (iconElementCheck.length >0) {
        //Current unselected
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        iconElement[0].className = unselected;
    }
}
//Icon 3 = Slippery Floor
function icon3() {
    var iconElementCheck = document.getElementsByClassName("icon3-unselected");
    var iconElement = document.getElementsByClassName("icon3");
    var unselected = "icon3 icon3-unselected";
    var selected = "icon3 icon3-selected";
    if (iconElementCheck.length >0) {
        //Current unselected
        iconElement[0].src = "images/slippery-selected.png";
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        iconElement[0].src ="images/slippery.png";
        iconElement[0].className = unselected;
    }
}
//Icon 4 = Insects
function icon4() {
    var iconElementCheck = document.getElementsByClassName("icon4-unselected");
    var iconElement = document.getElementsByClassName("icon4");
    var unselected = "icon4 icon4-unselected";
    var selected = "icon4 icon4-selected";
    if (iconElementCheck.length >0) {
        //Current unselected
        iconElement[0].src = "images/cockroach-selected.png";
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        iconElement[0].src ="images/cockroach.png";
        iconElement[0].className = unselected;
    }
}
//Icon 5 = Smelly
function icon5() {
    var iconElementCheck = document.getElementsByClassName("icon5-unselected");
    var iconElement = document.getElementsByClassName("icon5");
    var unselected = "icon5 icon5-unselected";
    var selected = "icon5 icon5-selected";
    if (iconElementCheck.length >0) {
        //Current unselected
        iconElement[0].src = "images/smelly-selected.png";
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        iconElement[0].src ="images/smelly.png";
        iconElement[0].className = unselected;
    }
}
//Icon 6 = WheelChair
function icon6() {
    var iconElementCheck = document.getElementsByClassName("icon6-unselected");
    var iconElement = document.getElementsByClassName("icon6");
    var unselected = "icon6 icon6-unselected";
    var selected = "icon6 icon6-selected selected";
    if (iconElementCheck.length >0) {
        //Current unselected
        iconElement[0].src = "images/wheelchair.png";
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        iconElement[0].src ="images/wheelchair-unselected.png";
        iconElement[0].className = unselected;
    }
}
//Icon 7 = Cost Money
function icon7() {
    var iconElementCheck = document.getElementsByClassName("icon7-unselected");
    var iconElement = document.getElementsByClassName("icon7");
    var unselected = "icon7 icon7-unselected unselected";
    var selected = "icon7 icon7-selected selected";
    if (iconElementCheck.length >0) {
        //Current unselected
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        iconElement[0].className = unselected;
    }
}
//Icon 8 = Faulty Toilet
function icon8() {
    var iconElementCheck = document.getElementsByClassName("icon8-unselected");
    var iconElement = document.getElementsByClassName("icon8");
    var unselected = "icon8 icon8-unselected";
    var selected = "icon8 icon8-selected";
    if (iconElementCheck.length >0) {
        //Current unselected
        iconElement[0].src = "images/faulty.png";
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        iconElement[0].src ="images/faulty-unselected.png";
        iconElement[0].className = unselected;
    }
}
function getIcons() {
    var icon1Check = document.getElementsByClassName("icon1-unselected");
    var icon2Check = document.getElementsByClassName("icon2-unselected");
}