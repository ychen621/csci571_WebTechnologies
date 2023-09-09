package com.example.frontend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

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


public class Pop extends Activity {

    private static final String SELECTED_WORK = "selected_artwork";

    private RequestQueue myRequestQueue;
    private String artworkID;

    private Context context;
    private LinearLayout categoryHolder;
    private RelativeLayout blockHolder;
    private TextView cate;
    private TextView nameHolder;
    private TextView descrpt;
    private TextView descrptHolder;
    private ImageView imageHolder;

    private TextView alertMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        WindowMetrics windowMetrics = this.getWindowManager().getCurrentWindowMetrics();
        Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility((WindowInsets.Type.systemBars()));

        int width = windowMetrics.getBounds().width() - insets.left - insets.right;
        int height = windowMetrics.getBounds().height() - insets.top -insets.bottom;

        getWindow().setLayout((int) (width*0.8), (int) (height*0.4));

        context = this;
        myRequestQueue = Volley.newRequestQueue(context);
        categoryHolder = (LinearLayout) findViewById(R.id.category_holder);

        extractDataFromIntent();
        extractListFromBackend();

    }

    private void extractListFromBackend() {
        GenesBackend genesBackend = new GenesBackend(artworkID);
        String genesURL = genesBackend.getGenesURL();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, genesURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    if(response.length()<1){
                        alertMsg = new TextView(context);
                        alertMsg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        alertMsg.setGravity(Gravity.CENTER);
                        alertMsg.setBackgroundColor(Color.parseColor("#eeeeee"));
                        alertMsg.setTypeface(alertMsg.getTypeface(), Typeface.BOLD);
                        alertMsg.setText("No Category Available");

                        blockHolder = new RelativeLayout(context);
                        blockHolder.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                        blockHolder.setGravity(Gravity.CENTER_HORIZONTAL);

                        blockHolder.addView(alertMsg);
                        categoryHolder.addView(blockHolder);
                    }
                        for(int i = 0; i<1; i++) {
                            JSONObject currCate = response.getJSONObject(i);
                            String name = currCate.getString("name");
                            String image = currCate.getString("category");
                            String description = currCate.getString("discription");

                            RelativeLayout.LayoutParams blockPara = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            RelativeLayout.LayoutParams catePara = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            RelativeLayout.LayoutParams namePara = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            RelativeLayout.LayoutParams imagePara = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            RelativeLayout.LayoutParams desLeftPara = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            RelativeLayout.LayoutParams desPara = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                            namePara.addRule(RelativeLayout.BELOW, 100);
                            //imagePara.addRule(RelativeLayout.RIGHT_OF, 100);
                            imagePara.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            desLeftPara.addRule(RelativeLayout.BELOW, 300);
                            desPara.addRule(RelativeLayout.BELOW, 300);
                            desPara.addRule(RelativeLayout.RIGHT_OF, 400);

                            blockHolder = new RelativeLayout(context);
                            blockHolder.setLayoutParams(blockPara);

                            cate =  new TextView(context);
                            cate.setLayoutParams(catePara);
                            cate.setId((int) 100);
                            cate.setText("Category");
                            cate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                            cate.setTypeface(cate.getTypeface(), Typeface.BOLD);
                            cate.setTextColor(ContextCompat.getColor(context, R.color.black));
                            cate.setPadding(40,40,0,40);

                            nameHolder = new TextView(context);
                            nameHolder.setLayoutParams(namePara);
                            nameHolder.setId((int) 200);
                            nameHolder.setText(name);
                            nameHolder.setTypeface(nameHolder.getTypeface(), Typeface.BOLD);
                            nameHolder.setTextColor(ContextCompat.getColor(context, R.color.black));
                            nameHolder.setPadding(40,0,0,80);

                            imageHolder = new ImageView(context);
                            imageHolder.setLayoutParams(imagePara);
                            imageHolder.setId((int) 300);
                            imageHolder.setPadding(0,0,0,80);
                            Picasso.get().load(image).resize(200,200).into(imageHolder);

                            descrpt = new TextView(context);
                            descrpt.setLayoutParams(desLeftPara);
                            descrpt.setId((int) 400);
                            descrpt.setText("Description");
                            descrpt.setTypeface(descrpt.getTypeface(), Typeface.BOLD);
                            descrpt.setTextColor(ContextCompat.getColor(context, R.color.black));
                            descrpt.setPadding(40,0,40,0);

                            descrptHolder = new TextView(context);
                            descrptHolder.setLayoutParams(desPara);
                            descrptHolder.setId((int) 500);
                            descrptHolder.setText(description);
                            descrptHolder.setPadding(0,0,40,0);

                            blockHolder.addView(cate);
                            blockHolder.addView(nameHolder);
                            blockHolder.addView(imageHolder);
                            blockHolder.addView(descrpt);
                            blockHolder.addView(descrptHolder);
                            categoryHolder.addView(blockHolder);

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
        artworkID = intent.getStringExtra(SELECTED_WORK);
    }

    public static Intent makeGenesIntent(Context context, String artworkID){
        Intent intent = new Intent(context, Pop.class);
        intent.putExtra(SELECTED_WORK, artworkID);
        return intent;
    }
}
