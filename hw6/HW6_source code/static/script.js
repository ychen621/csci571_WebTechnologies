const INPUT_NAME = document.getElementsByName('input_name')[0];

var searchForm = document.getElementById("search_form");
var searchRow = document.getElementById("searchBar");
var submitButton = document.getElementById("submit_button");
var noResultFound = document.getElementById("not_found");
var searchResult = document.getElementById("search_result");
var resultList = document.getElementById("result_list");
var innerLoading = document.getElementById("inner_dotdotdot");
var artistDetail = document.getElementById("artist_content");
var artistList = new XMLHttpRequest();

document.onreadystatechange = function(){
    if(document.readyState !== "complete"){
        document.querySelector("#dotdotdot").style.display = "block";
    }
    else{
        document.querySelector("#dotdotdot").style.display = "none";
    }
}
function displayNoResult(present){
    if(present === "on"){
        noResultFound.style.display = "block";
    }
    else if(present === "off"){
        noResultFound.style.display = "none";
    }
}

function displayResultList(present){
    if(present === "on"){
        resultList.style.display = "block";
    }
    else if(present === "off"){
        resultList.style.display = "none";
    }
}

function displayArtistDetail(present){
    if(present === "on"){
        artistDetail.style.display = "block";
    }
    else if(present === "off"){
        artistDetail.style.display = "none";
    }
}

function processDetailResponse(response){
    displayNoResult("off");
    displayResultList("on");
    
    artistDetail.textContent = "";

    //artist name and birth/death day
    var name = document.createElement("h1");
    var artistName = response["name"];
    var artistBD = response["birthday"];
    var artistDD = response["deathday"];
    name.setAttribute("class", "detail_name");
    name.innerHTML = artistName + " (" + artistBD + "~" + artistDD + ")";
    
    //artist nationality
    var nationality = document.createElement("h2");
    var artistNN = response["nationality"];
    nationality.setAttribute("class", "detail_nationality");
    nationality.innerHTML = artistNN;

    //artist bio
    var biography = document.createElement("p");
    var artistBio = response["biography"];
    biography.setAttribute("class", "detail_biography");
    biography.innerHTML = artistBio;

    artistDetail.append(name);
    artistDetail.append(nationality);
    artistDetail.append(biography);
    
    displayArtistDetail("on");
}

function getDetail(input){
    var artistDetail = new XMLHttpRequest();
    artistDetail.onreadystatechange = function(){
        if(artistDetail.readyState == 4 && artistDetail.status == 200){
            var detail = JSON.parse(artistDetail.responseText);
            processDetailResponse(detail);
        }
    }
    artistDetail.open("GET", "/info/" + input, true);
    artistDetail.send();
}

function processListResponse(response){
    displayNoResult("off");
    displayResultList("off");
    displayArtistDetail("off");

    var artistArray = response["ten_artists"];
    resultList.textContent = "";
    if(artistArray.length == 0){
        displayNoResult("on");
    }
    else{
        for(i=0; i<artistArray.length; i++){
            var currArtist = artistArray[i];

            //artist image
            var img = document.createElement("img");
            if(currArtist["image"] == "/assets/shared/missing_image.png"){
                img.setAttribute("src", "static/artsy_logo.svg");
            }
            else{
                img.setAttribute("src", currArtist["image"]);
            }
            img.setAttribute("id", currArtist["ID"]);
            img.setAttribute("class", "artist_picture");

            //artist name
            var name = document.createElement("p");
            name.innerHTML = currArtist["name"];
            name.setAttribute("class", "artist_name");
            name.setAttribute("id", currArtist["ID"]);

            //creare clickable block
            var block = document.createElement("button");
            block.setAttribute("class", "artistButton");
            block.setAttribute("value", currArtist["ID"]);
            block.setAttribute("id", currArtist["ID"])

            var imgHolder = document.createElement("div");
            imgHolder.setAttribute("class", "image_holder");
            imgHolder.append(img);

            var nameHolder = document.createElement("div");
            nameHolder.setAttribute("class", "name_holder");
            nameHolder.append(name);

            block.appendChild(imgHolder);
            block.appendChild(nameHolder);

            //add to result list
            resultList.append(block);
        }
        displayResultList("on");
    }
}

function getList(input){
    artistList.onreadystatechange = function(){
        if(artistList.readyState == 4 && artistList.status == 200){
            var result = JSON.parse(artistList.responseText);
            processListResponse(result);
        }
    }
    artistList.open("GET", "/search_list/" + input, true);
    artistList.send();
}

function searching(event){
    event.preventDefault();
    var searchName = INPUT_NAME.value.replaceAll(' ', '%20');
    getList(searchName);
    displayNoResult("off");
}

function detailShowing(event){
    event.preventDefault();
    var targetID = event.target.getAttribute("id");
    getDetail(targetID);
    displayNoResult("off");
}


searchForm.addEventListener('submit', searching, false)
resultList.addEventListener('click', detailShowing, false)

