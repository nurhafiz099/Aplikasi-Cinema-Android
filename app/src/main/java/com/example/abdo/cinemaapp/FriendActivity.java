package com.example.abdo.cinemaapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdo.cinemaapp.Adapters.FavoriteAdapter;
import com.example.abdo.cinemaapp.Model.Favorite;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity {
    private Uri filePath;
    TextView username;
    List<Favorite> list;
    ListView listView;
    FirebaseStorage storage;
    StorageReference storageReference;
    FavoriteAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        String id = getIntent().getStringExtra("id");
        username = findViewById(R.id.friendUsername);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        image = findViewById(R.id.friendImage);
        listView = findViewById(R.id.friendListView);
        final String API_KEY=getString(R.string.API_KEY);
        list=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userReference = mDatabase.child("Users").child(id).child("Username");
        StorageReference ref = storageReference.child("images/"+id);
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(FriendActivity.this).load(uri).into(image);
            }
        });
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference reference1= mDatabase.child("Users").child(id).child("shows");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()
                        ) {
                    String id = snap.getKey();
                    LoadData1("https://api.themoviedb.org/3/tv/" + id + "?api_key=" + getString(R.string.API_KEY) + "&language=en-US");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference= mDatabase.child("Users").child(id).child("movies");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren()
                        ) {
                    String id = snap.getKey();
                    LoadData("https://api.themoviedb.org/3/movie/"+id+"?api_key="+getString(R.string.API_KEY)+"&language=en-US");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter = new FavoriteAdapter(FriendActivity.this,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Favorite f = list.get(position);
                if (f.getType().equals("movie"))
                {
                    Intent intent = new Intent(FriendActivity.this,MovieActivity.class);
                    intent.putExtra("id",f.getId());
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(FriendActivity.this,TvShowActivity.class);
                    intent.putExtra("id",list.get(position).getId());
                    startActivity(intent);
                }
            }
        });

    }
    public void LoadData1(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    String id = response.getString("id");
                    String name = response.getString("name");
                    String img = "https://image.tmdb.org/t/p/w200"+response.getString("poster_path");
                    list.add(new Favorite(id,name,img,"show"));
                    adapter.notifyDataSetChanged();
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
        RequestQueue queue = Volley.newRequestQueue(FriendActivity.this);
        queue.add(request);
    }
    public void LoadData(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    String id = response.getString("id");
                    String name = response.getString("original_title");
                    String img = "https://image.tmdb.org/t/p/w200"+response.getString("poster_path");
                    list.add(new Favorite(id,name,img,"movie"));
                    adapter.notifyDataSetChanged();
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
        RequestQueue queue = Volley.newRequestQueue(FriendActivity.this);
        queue.add(request);
    }
}
