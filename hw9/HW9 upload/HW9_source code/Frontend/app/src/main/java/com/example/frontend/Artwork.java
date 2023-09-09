package com.example.frontend;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Artwork extends Fragment {

    private String artistID;
    private RequestQueue myRequestQueue;
    private LinearLayout artworkOuter;
    private RelativeLayout outerHolder;
    private ImageView imageHolder;
    private TextView titleHolder;

    private TextView alertMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artwork, container, false);
        myRequestQueue = Volley.newRequestQueue(getActivity());

        if(getArguments() != null){
            artistID = getArguments().getString("params");
        }

        return inflater.inflate(R.layout.fragment_artwork, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        artworkOuter = (LinearLayout) view.findViewById(R.id.outerSpace);
        extractArtworkFromBackend(artistID);

    }

    private void extractArtworkFromBackend(String artistID) {

        String processedID = artistID.substring(34);
        ArtworksBackend artworksBackend = new ArtworksBackend(processedID);
        String artworkURL = artworksBackend.getArtworkURL();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, artworkURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.length() < 1){
                        alertMsg = new TextView(getActivity());
                        alertMsg.setLayoutParams(new LinearLayout.LayoutParams(600,LinearLayout.LayoutParams.WRAP_CONTENT));
                        alertMsg.setGravity(Gravity.CENTER);
                        alertMsg.setBackgroundColor(Color.parseColor("#eeeeee"));
                        alertMsg.setTypeface(alertMsg.getTypeface(), Typeface.BOLD);
                        alertMsg.setText("No Artwork Available");

                        outerHolder = new RelativeLayout(getActivity());
                        outerHolder.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                        outerHolder.setGravity(Gravity.CENTER_HORIZONTAL);

                        outerHolder.addView(alertMsg);
                        artworkOuter.addView(outerHolder);
                    }
                    for(int i = 0; i<response.length(); i++){
                        JSONObject currWork = response.getJSONObject(i);
                        String currWorkID = currWork.getString("id");
                        String currWorkTitle = currWork.getString("title");
                        String currWorkDate = currWork.getString("date");
                        String currImage = currWork.getString("image");

                        RelativeLayout.LayoutParams relPara = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        RelativeLayout.LayoutParams modifiedPara = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        modifiedPara.addRule(RelativeLayout.BELOW,100+i);

                        outerHolder = new RelativeLayout(getActivity());
                        outerHolder.setLayoutParams(relPara);
                        //outerHolder.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.clickable_square));

                        imageHolder = new ImageView(getActivity());
                        imageHolder.setLayoutParams(modifiedPara);
                        imageHolder.setPadding(0,0,0,100);
                        Picasso.get().load(currImage).resize(600,400).into(imageHolder);

                        titleHolder = new TextView(getActivity());
                        titleHolder.setLayoutParams(relPara);
                        titleHolder.setId(100+i);
                        titleHolder.setGravity(Gravity.CENTER);
                        titleHolder.setPadding(0,10,0,50);
                        titleHolder.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                        titleHolder.setTypeface(titleHolder.getTypeface(), Typeface.BOLD);
                        titleHolder.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                        titleHolder.setText(currWorkTitle);

                        outerHolder.addView(imageHolder);
                        outerHolder.addView(titleHolder);
                        artworkOuter.addView(outerHolder);

                        outerHolder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = Pop.makeGenesIntent(getActivity(), currWorkID);
                                startActivity(intent);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        myRequestQueue.add(jsonArrayRequest);

    }
}