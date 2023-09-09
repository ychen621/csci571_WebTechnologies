package com.example.frontend;

public class InfoBackend {

    private String HOST = "https://ashleyhw9-backend-571.wl.r.appspot.com/";
    private String artistInfo = HOST + "info/";

    private String infoURL;

    public InfoBackend(String id){
        infoURL = artistInfo + id;
    }

    public String getInfoURL() {
        return infoURL;
    }
}
