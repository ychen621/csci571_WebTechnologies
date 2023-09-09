package com.example.frontend;

public class GenesBackend {

    private String HOST = "https://ashleyhw9-backend-571.wl.r.appspot.com/";
    private String genes = HOST + "genes/";

    private String genesURL;

    public GenesBackend(String id){
        genesURL = genes + id;
    }

    public String getGenesURL() {
        return genesURL;
    }
}
