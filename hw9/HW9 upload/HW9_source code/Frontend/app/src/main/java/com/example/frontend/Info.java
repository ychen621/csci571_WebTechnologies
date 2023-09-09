package com.example.frontend;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Info extends Fragment {

    private String artistID;
    private RequestQueue myRequestQueue;
    private TextView currName;
    private TextView currNationality;
    private TextView currBirth;
    private TextView currDeath;
    private TextView currBio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        myRequestQueue = Volley.newRequestQueue(getActivity());


        if(getArguments() != null){
            artistID = getArguments().getString("params");
        }

        extractDetailFromBackend(artistID);

        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currName = view.findViewById(R.id.artist_name);
        currNationality = view.findViewById(R.id.artist_nationality);
        currBirth = view.findViewById(R.id.artist_birthday);
        currDeath = view.findViewById(R.id.artist_deathday);
        currBio = view.findViewById(R.id.artist_biography);
    }

    private void extractDetailFromBackend(String artistID) {

        String processedID = artistID.substring(34);
        InfoBackend infoBackend = new InfoBackend(processedID);
        String infoURL = infoBackend.getInfoURL();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, infoURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("name");
                    String birthday = response.getString("birthday");
                    String deathday = response.getString("deathday");
                    String nationality = response.getString("nationality");
                    String biography = response.getString("biography");

                    currName.setText(name);
                    currNationality.setText(nationality);
                    currBirth.setText(birthday);
                    currDeath.setText(deathday);
                    currBio.setText(biography);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        myRequestQueue.add(jsonObjectRequest);
    }
}