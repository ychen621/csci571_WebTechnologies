package com.example.frontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Search extends AppCompatActivity {

    private static final String INPUT_ARTIST_NAME = "inputArtistName";

    private String name;
    private LinearLayout buttonContainer;
//    private Button currBtn;
    private RelativeLayout currClickable;
    private ImageView currView;
    private TextView currName;
    private Context context;
    private RequestQueue myRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buttonContainer = (LinearLayout) findViewById(R.id.linear_layout);
        buttonContainer.removeAllViews();
        context = this;
        myRequestQueue = Volley.newRequestQueue(this);
        
        extractDataFromIntent();
        extractListFromBackend();
        setTitle(name);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void extractListFromBackend() {
        String processedName = name.replaceAll(" ", "%20");
        SearchBackend list = new SearchBackend(processedName);
        String SearchURL = list.getSearchURL();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, SearchURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject currArtist = response.getJSONObject(i);
                        String name = currArtist.getString("title");
                        String image = currArtist.getString("image");
                        String id = currArtist.getString("id");

                        /**
                        currBtn = new Button(context);
                        currBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        currBtn.setTag("currArtist" + i);
                        currBtn.setGravity(Gravity.CENTER_VERTICAL);
                        currBtn.setText(name);

                        buttonContainer.addView(currBtn);
                         */

                        currClickable = new RelativeLayout(context);
                        currView = new ImageView(context);
                        currName = new TextView(context);


                        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        relParams.addRule(RelativeLayout.BELOW, 100+i);

                        currClickable.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        currClickable.setBackground(ContextCompat.getDrawable(context, R.drawable.clickable_square));


                        currView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT));
                        Picasso.get().load(image).resize(600,800).into(currView);
                        currView.setPadding(0,100,0,20);
                        currView.setId(100+i);

                        currName.setLayoutParams(relParams);
                        currName.setGravity(Gravity.CENTER);
                        currName.setPadding(0,10,0,50);
                        currName.setText(name);
                        currName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                        currName.setTextColor(ContextCompat.getColor(context, R.color.black));

                        currClickable.addView(currView);
                        currClickable.addView(currName);
                        buttonContainer.addView(currClickable);

                        currClickable.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = Detail.makeDetailIntent(context, name, id);
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

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        name = intent.getStringExtra(INPUT_ARTIST_NAME);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent makeIntent(Context context, String artistName){
        Intent intent = new Intent(context, Search.class);
        intent.putExtra(INPUT_ARTIST_NAME, artistName);
        return intent;
    }
}