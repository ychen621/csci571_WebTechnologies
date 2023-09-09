//This file implements the functions processing data from Artsy API
const axios = require('axios');

module.exports.authentication = authentication;
module.exports.get_token = get_token;
module.exports.search_endpoint = search_endpoint;
module.exports.get_searchList = get_searchList;
module.exports.artist_endpoint = artist_endpoint;
module.exports.get_artistDetail = get_artistDetail;
module.exports.artwork_endpoint = artwork_endpoint;
module.exports.get_artworks = get_artworks;
module.exports.genes_endpoint = genes_endpoint;
module.exports.get_genes = get_genes;

async function authentication(){
    try{
        const response = await axios.post('https://api.artsy.net/api/tokens/xapp_token','',
        {
            params: {
                'client_id': '09098df81d60702eb326',
                'client_secret': '1468cf45cb6f660d9266d3d550737337'
            }
        })
        return response;
    }
    catch(err){
        console.log(err);
    }
}

async function get_token(){
    const response = await authentication();
    return response.data.token;
}

async function search_endpoint(name){
    var myToken = await get_token();
    var searchURL = "https://api.artsy.net/api/search?q=" + name + "&size=10";
    try{
        const response = await axios.get(searchURL,
        {
            headers:{
                'X-XAPP-Token': myToken
            }
        })
        return response;
    }
    catch(err){
        console.log(err);
    }
}

async function get_searchList(name){
    var response = await search_endpoint(name);
    var result = await response.data._embedded;
    var list = await result.results;
    var filtered = await list.filter(function(ele){
        return ele.og_type == "artist";
    })
    
    let final = new Array;
    for(i=0;i<filtered.length;i++){
        let currNode;
        let links = await filtered[i]._links;
        let self = await links.self;
        let id = await self.href;
        let title = await filtered[i].title;
        let thumbnail = await links.thumbnail;
        let image = await thumbnail.href;
        if(image == "/assets/shared/missing_image.png"){
            image = "../assets/artsy_logo.svg"
        }
        currNode = {
            id: id,
            title: title,
            image: image
        }
        final.push(currNode);
    }

    console.log(final);
    console.log(typeof(final))
    return final;
}

async function artist_endpoint(id){
    var myToken = await get_token();
    var artistURL = "https://api.artsy.net/api/artists/" + id;
    try{
        const response = await axios.get(artistURL,
        {
            headers:{
                'X-XAPP-Token': myToken
            }
        })
        return response;
    }
    catch(err){
        console.log(err);
    }
}

async function get_artistDetail(id){
    var response = await artist_endpoint(id);
    var result = await response.data;

    let final;
    let name = await result.name;
    let birthday = await result.birthday;
    let deathday = await result.deathday;
    let nationality = await result.nationality;
    let biography = await result.biography;

    final = {
        name: name,
        birthday: birthday,
        deathday: deathday,
        nationality: nationality,
        biography: biography
    }

    console.log(final);
    console.log(typeof(final))

    return final;
}

async function artwork_endpoint(id){
    var myToken = await get_token();
    var artistURL = "https://api.artsy.net/api/artworks?artist_id=" + id + "&size=10";
    try{
        const response = await axios.get(artistURL,
        {
            headers:{
                'X-XAPP-Token': myToken
            }
        })
        return response;
    }
    catch(err){
        console.log(err);
    }
}

async function get_artworks(id){
    var response = await artwork_endpoint(id);
    var result = await response.data._embedded;
    var artwork = await result.artworks;

    let final = new Array;
    for(i=0; i<artwork.length; i++){
        let currArtwork;
        let id = await artwork[i].id;
        let title = await artwork[i].title;
        let date = await artwork[i].date;
        let links = await artwork[i]._links;
        let thumbnail = await links.thumbnail;
        let href = await thumbnail.href;
        currArtwork = {
            id: id,
            title: title,
            date: date,
            image: href
        }
        final.push(currArtwork);
    }
    console.log(final);
    console.log(typeof(final))
    return final;
}

async function genes_endpoint(id){
    var myToken = await get_token();
    var genesURL = "https://api.artsy.net/api/genes?artwork_id=" + id;
    try{
        const response = await axios.get(genesURL,
        {
            headers:{
                'X-XAPP-Token': myToken
            }
        })
        return response;
    }
    catch(err){
        console.log(err);
    }
}

async function get_genes(id){
    var response = await genes_endpoint(id);
    var result = await response.data._embedded;
    var genes = await result.genes;

    let final = new Array;
    for(i=0; i<genes.length; i++){
        let currNode;
        let name = await genes[i].name;
        let description = await genes[i].description;
        let link = await genes[i]._links;
        let thumbnail = await link.thumbnail;
        let href = await thumbnail.href;
    
        currNode = {
            name: name,
            category: href,
            discription: description
        }
        final.push(currNode);
    }
    

    console.log(final);
    console.log(typeof(final));
    return final;
}