package com.example.abdo.cinemaapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

public class TvShowActivity extends AppCompatActivity {
    TextView name,overview,seasons,episodes,date,genre,rating;
    ImageView img1;
    ScrollView linearLayout;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ImageView like;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        img1 = findViewById(R.id.tvShowImag);
        linearLayout = findViewById(R.id.a);
        name = findViewById(R.id.ShowName);
        like = findViewById(R.id.showLikeImage);
        overview = findViewById(R.id.ShowOverview);
        mAuth = FirebaseAuth.getInstance();
        seasons = findViewById(R.id.ShowNumberOfSeasons);
        episodes = findViewById(R.id.ShowNumberOfEpisodes);
        date = findViewById(R.id.ShowDate);
        genre = findViewById(R.id.ShowGenre);
        rating = findViewById(R.id.ShowRating);
        final String id = getIntent().getStringExtra("id");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String uid=   mAuth.getCurrentUser().getUid();
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference= mDatabase.child("Users").child(uid).child("shows");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(id))
                        {
                            like.setImageResource(R.drawable.unlikelike);
                            mDatabase.child("Users").child(uid).child("shows").child(id).removeValue();

                        }
                        else
                        {
                            like.setImageResource(R.drawable.like);
                            mDatabase.child("Users").child(uid).child("shows").child(id).setValue(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });
        DatabaseReference reference= mDatabase.child("Users").child(uid).child("shows");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(id))
                {
                    like.setImageResource(R.drawable.like);


                }
                else
                {
                    like.setImageResource(R.drawable.unlikelike);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        LoadMovie("https://api.themoviedb.org/3/tv/"+id+"?api_key="+getString(R.string.API_KEY)+"&language=en-US");

    }
    public void LoadMovie(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    overview.setText(response.getString("overview"));
                    seasons.setText("Number of seasons : "+response.getString("number_of_seasons"));
                    date.setText("release date : "+response.getString("first_air_date"));
                    JSONArray array = response.getJSONArray("genres");
                    JSONObject object =array.getJSONObject(0);
                    genre.setText("Category : "+object.getString("name"));
                    episodes.setText("Number of Episodes : "+response.getString("number_of_episodes"));
                    name.setText(response.getString("name"));
                    rating.setText("Rating : "+response.getString("vote_average"));
                    Picasso.with(TvShowActivity.this).load("https://image.tmdb.org/t/p/original"+response.getString("poster_path")).into(img1);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("tag",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(TvShowActivity.this);
        queue.add(request);
    }
}
