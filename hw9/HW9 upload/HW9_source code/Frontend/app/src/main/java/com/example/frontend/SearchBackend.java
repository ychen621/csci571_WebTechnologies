package com.example.frontend;

public class SearchBackend {

    private String HOST = "https://ashleyhw9-backend-571.wl.r.appspot.com/";
    private String searchArtist = HOST + "search_list/";

    private String searchURL;

    public SearchBackend(String artistName){
        searchURL = searchArtist + artistName;
    }

    public String getSearchURL() {
        return searchURL;
    }
}
