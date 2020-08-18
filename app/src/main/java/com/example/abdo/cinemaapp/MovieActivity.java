package com.example.abdo.cinemaapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovieActivity extends AppCompatActivity {
   TextView name,overview,length,revenue,date,genre,rating;
   ImageView img1;
   ScrollView linear;
   ImageView like;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        like = findViewById(R.id.movieLikeImage);
        img1 = findViewById(R.id.movieImage);
        linear = findViewById(R.id.movieLinear);
        name = findViewById(R.id.MovieName);
        overview = findViewById(R.id.MovieOverview);
        length = findViewById(R.id.MovieLength);
        revenue = findViewById(R.id.MovieRevenue);
        date = findViewById(R.id.MovieDate);
        genre = findViewById(R.id.MovieGenre);
        mAuth = FirebaseAuth.getInstance();
        rating = findViewById(R.id.MovieRating);
        final String id = getIntent().getStringExtra("id");
        final String uid=   mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference= mDatabase.child("Users").child(uid).child("movies");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(id))
                        {
                            like.setImageResource(R.drawable.unlikelike);
                            mDatabase.child("Users").child(uid).child("movies").child(id).removeValue();

                        }
                        else
                        {
                            like.setImageResource(R.drawable.like);
                            mDatabase.child("Users").child(uid).child("movies").child(id).setValue(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        DatabaseReference reference= mDatabase.child("Users").child(uid).child("movies");
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

        LoadMovie("https://api.themoviedb.org/3/movie/"+id+"?api_key="+getString(R.string.API_KEY)+"&language=en-US");
    }
    public void LoadMovie(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                   overview.setText(response.getString("overview"));
                   length.setText("Movie length : "+response.getString("runtime")+" minutes");
                   date.setText("release date : "+response.getString("release_date"));
                   JSONArray array = response.getJSONArray("genres");
                   JSONObject object =array.getJSONObject(0);
                   genre.setText("Category : "+object.getString("name"));
                   revenue.setText("Revenue : "+response.getString("revenue")+"$");
                   name.setText(response.getString("original_title"));
                   rating.setText("Movie Rating : "+response.getString("vote_average"));
                   Picasso.with(MovieActivity.this).load("https://image.tmdb.org/t/p/original"+response.getString("poster_path")).into(img1);
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
        RequestQueue queue = Volley.newRequestQueue(MovieActivity.this);
        queue.add(request);
    }
}
