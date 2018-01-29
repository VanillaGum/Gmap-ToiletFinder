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
function createApprovedInfoWindow(id,rating,genderM) {
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
    var imageSrc = null;
    switch(rating) {
        case 5:
            imageSrc="images/5-stars-rating.png";
            break;
        case 4:
            imageSrc="images/4-stars-rating.png";
            break;
        case 3:
            imageSrc="images/3-stars-rating.png";
            break;
        case 2:
            imageSrc="images/2-stars-rating.png";
            break;
        case 1:
            imageSrc="images/1-star-rating.png";
            break;
        case 0:
            imageSrc="images/0-star-rating.png";
            break;
        default:
            imageSrc="images/0-star-rating.png";
            break;
    }

    var content ='<div>' +
        '<div style="display:inline-block;" id="suggested'+id+'" class="infowindow-size">' +
        '<img width="100%" src="'+imageSrc+'"/>' +
        '<div class="infowindow-div-left">' +
        '<div style="font-size:20px;margin-top:5px;cursor:pointer;" onclick="addReviewScreen('+id+')"> ' +
        '<b>Review</b>' +
        '<img width="30" height="30" src="images/review.png"/>' +
        '</div>' +
        '<hr>' +
        '<img width="40" height="40" src="images/wheelchair-unselected.png"/>' +
        '<img width="40" height="40" class="unselected" src="images/money.png"/>' +
        '</div>' +
        '<div class="infowindow-div-right">' +
        '<img class="'+mClass+'" src="images/male_icon.png" alt="maleToiletIcon" width="40%" height="100%"/>' +
        '<img class="'+fClass+'" src="images/female_icon.png" alt="femaleToiletIcon" style="margin-left:5px;" width="52%" height="100%"/>' +
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
    var imageSrc = null;
    switch(rating) {
        case 5:
            imageSrc="images/5-stars-rating.png";
            break;
        case 4:
            imageSrc="images/4-stars-rating.png";
            break;
        case 3:
            imageSrc="images/3-stars-rating.png";
            break;
        case 2:
            imageSrc="images/2-stars-rating.png";
            break;
        case 1:
            imageSrc="images/1-star-rating.png";
            break;
        case 0:
            imageSrc="images/0-star-rating.png";
            break;
        default:
            imageSrc="images/0-star-rating.png";
            break;
    }

    var content ='<div>' +
        '<div style="display:inline-block; width:200px;height:200px" id="suggested'+id+'">' +
        '<img width="100%" src="'+imageSrc+'"/>' +
        '<div class="infowindow-div-left">' +
        '<div style="width:100%;margin-top:5px;">' +
        '<img style="margin-left:5px;" class="upvote'+id+' upvote-unselected" src="images/upvote.png" alt="upvoteIcon" width="20.48" height="20.58" onclick="upvoteToiletSuggestion('+id+','+toiletId+')"/>' +
        '<img style="margin-left:5px;" class="flag'+id+' flag-unselected unselected" src="images/flag.png" alt="upvoteIcon" width="20.48" height="20.58" onclick="flagToiletSuggestion('+id+','+toiletId+')"/>' +
        '</div>' +
        '<hr>' +
        '<div style="font-size:20px;cursor:pointer;" onclick="addReviewScreen('+id+')"> ' +
        '<b>Review</b>' +
        '<img width="30" height="30" src="images/review.png"/>' +
        '</div>' +
        '<hr>' +
        '<img width="40" height="40" src="images/wheelchair-unselected.png"/>' +
        '<img width="40" height="40" class="unselected" src="images/money.png"/>' +
        '</div>' +
        '<div class="infowindow-div-right">' +
        '<img class="'+mClass+'" src="images/male_icon.png" alt="maleToiletIcon" width="40%" height="100%"/>' +
        '<img class="'+fClass+'" src="images/female_icon.png" alt="femaleToiletIcon" style="margin-left:5px;" width="52%" height="100%"/>' +
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
//Upvoting Toilet Suggestion
function flagToiletSuggestion(id) {
    var defaultClass = "flag"+id+" ";
    var unselected = "flag-unselected unselected";
    var selected = "flag-selected selected";
    var upvoteElementList = document.getElementsByClassName("flag"+id+" flag-unselected");
    var upvoteElement = document.getElementsByClassName("flag"+id);
    if (upvoteElementList.length > 0) {
        upvoteElement[0].className = defaultClass +  selected;
        document.getElementById("formSubmitToilet:flag").value = 1;
        flagToiletIdChange(id);
    }else {
        upvoteElement[0].className = defaultClass + unselected;
    }
}

//Change upvotedToiletId
function upvoteToiletIdChange(id) {
    document.getElementById("formSubmitToilet:upvoteToiletId").value = id;
    upvoteSubmit();
}

function flagToiletIdChange(id) {
    document.getElementById("formSubmitToilet:uniqueId").value = id;
    flagSubmit();
}

//Reviewing Toilets
function addReviewScreen(uniqueId) {
    //Create Review Screen And Reveal Overlay Screen
    var selected="selected-Overlay";
    var screen = '<div id="screen" class="middleDiv">' +
        '<div id="iconHolder">' +
        '<img src="images/toilet.png" class="icon1 icon1-unselected" width="76.8" height="76.8" onclick="icon1()"/>' +
        '<img src="images/garbage.png" class="icon2 icon2-unselected unselected" width="76.8" height="76.8" onclick="icon2()"/>' +
        '<img src="images/slippery.png" class="icon3 icon3-unselected" width="76.8" height="76.8" onclick="icon3()"/>' +
        '<img src="images/cockroach.png" class="icon4 icon4-unselected" width="60" height="60" style="padding:8.4px" onclick="icon4()"/>' +
        '<img src="images/smelly.png" class="icon5 icon5-unselected" width="76.8" height="76.8" onclick="icon5()"/>' +
        '<img src="images/faulty-unselected.png" class="icon8 icon8-unselected" width="76.8" height="76.8" onclick="icon8()" />' +
        '</div>' +
        '<div id="comment-section">' +
        '<p style="font-size:1.5em;margin-bottom:1%">Comments</p>' +
        '<form id="comments-area"action="javascript:void(0);">' +
        '<textarea id="comments" rows="13" cols="30" maxlength="255">' +
        '</textarea>' +
        '</form>' +
        '</div>' +
        '<img id="closeIcon" src="images/close_icon.png" onclick="removeScreen()"/>' +
        '<img id="helpIcon" width="30px" height="30px" style="margin-top:0.5%" src="images/help-unselected.png"/>' +
        '<p style="font-size:1.75em;margin-top:56%;margin-left:40%;position:absolute;" class="review-submit" onclick="ToiletReview('+uniqueId+')">' +
        '<b>Submit</b></p>'+
        '</div>';
    document.getElementById("greyOverlay").className = selected;
    $('#greyOverlay').append(screen);
}
function removeScreen() {
    //Remove Overlay Screen And Review Screen
    var unselected="unselected-Overlay";

    document.getElementById('greyOverlay').innerHTML = "";
    document.getElementById("greyOverlay").className=unselected;
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
        document.getElementById("formSubmitToilet:icon1").value = 1;
        iconElement[0].className = selected1;
        iconElement[0].src="images/toilet_silver.png";
    }else if(iconElementCheck2.length > 0) {
        //Currently Selected 1
        document.getElementById("formSubmitToilet:icon1").value = 2;
        iconElement[0].className = selected2;
        iconElement[0].src="images/toilet_gold.png";
    }else {
        document.getElementById("formSubmitToilet:icon1").value = 0;
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
        document.getElementById("formSubmitToilet:icon2").value = 1;
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        document.getElementById("formSubmitToilet:icon2").value = 0;
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
        document.getElementById("formSubmitToilet:icon3").value = 1;
        iconElement[0].src = "images/slippery-selected.png";
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        document.getElementById("formSubmitToilet:icon3").value = 0;
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
        document.getElementById("formSubmitToilet:icon4").value = 1;
        iconElement[0].src = "images/cockroach-selected.png";
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        document.getElementById("formSubmitToilet:icon4").value = 0;
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
        document.getElementById("formSubmitToilet:icon5").value = 1;
        iconElement[0].src = "images/smelly-selected.png";
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        document.getElementById("formSubmitToilet:icon5").value = 0;
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
        document.getElementById("formSubmitToilet:icon6").value = 1;
        iconElement[0].src = "images/wheelchair.png";
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        document.getElementById("formSubmitToilet:icon6").value = 0;
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
        document.getElementById("formSubmitToilet:icon7").value = 1;
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        document.getElementById("formSubmitToilet:icon7").value = 0;
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
        document.getElementById("formSubmitToilet:icon8").value = 1;
        iconElement[0].src = "images/faulty.png";
        iconElement[0].className = selected;
    }else {
        //Currently Selected
        document.getElementById("formSubmitToilet:icon8").value = 0;
        iconElement[0].src ="images/faulty-unselected.png";
        iconElement[0].className = unselected;
    }
}
//Submit Review(Suggested Toilet)
function ToiletReview(uniqueId) {
    document.getElementById("formSubmitToilet:comments").value = document.getElementById("comments").value;
    document.getElementById("formSubmitToilet:uniqueId").value = uniqueId;
    reviewToilet();
    removeScreen();
}