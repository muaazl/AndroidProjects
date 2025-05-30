package com.example.mysuqerheroapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleHeroView extends AppCompatActivity {

    String url = "https://akabab.github.io/superhero-api/api/";
    RequestQueue queue;
    TextView tv;
    ImageView single_pic;
    Button nextButton;
    String name, imageURL;
    int ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_single_hero_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv = findViewById(R.id.tv_name_single);
        single_pic = findViewById(R.id.img_single);
        nextButton = findViewById(R.id.nextButton);

        queue = Volley.newRequestQueue(this);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonTapped();
            }
        });
    }

    private void LoadSuperHero(String id) {
        String singleHeroEndPoint = url+"id/"+id+".json";
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, singleHeroEndPoint, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    name = response.getString("name");
                                    imageURL = response.getJSONObject("images").getString("sm");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String err = error.toString();
                        tv.setText("Cannot Get Data: "+error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void buttonTapped() {
        ++ID;
        LoadSuperHero(String.valueOf(ID));
        tv.setText(name);
        Picasso.get().load(imageURL).resize(480, 500).centerCrop().into(single_pic);
    }
}