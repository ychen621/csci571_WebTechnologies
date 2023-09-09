package com.example.frontend;

public class ArtworksBackend {

    private String HOST = "https://ashleyhw9-backend-571.wl.r.appspot.com/";
    private String artworks = HOST + "artworks/";

    private String artworkURL;

    public ArtworksBackend(String id){
        artworkURL = artworks + id;
    }

    public String getArtworkURL(){
        return artworkURL;
    }
}
