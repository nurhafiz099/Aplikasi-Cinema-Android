package com.example.abdo.cinemaapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdo.cinemaapp.Adapters.TrendAdapter;
import com.example.abdo.cinemaapp.Model.Trend;
import com.example.abdo.cinemaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment {

   TrendAdapter adapterMovie;
   ArrayList<Trend> listMovie;
   RecyclerView listViewMovie;
    TrendAdapter adapterShow;
    ArrayList<Trend> listShow;
    RecyclerView listViewShow;
   String id;
    private FirebaseAuth mAuth;

    public RecommendFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_recommend, container, false);
        listMovie = new ArrayList<>();
        listViewMovie = v.findViewById(R.id.RecommendedMovieRecyclerView);
        listShow = new ArrayList<>();
        listViewShow = v.findViewById(R.id.RecommendedShowsRecyclerView);
        mAuth = FirebaseAuth.getInstance();
        id = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refMovies = database.getReference("Users").child(id).child("movies");
        final String API_KEY = getString(R.string.API_KEY);
        refMovies.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren()
                     ) {
                    String s = snap.getKey();
                    LoadTrendingMovies("https://api.themoviedb.org/3/movie/"+s+"/recommendations?api_key="+API_KEY+"&language=en-US&page=1");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapterMovie = new TrendAdapter(listMovie);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listViewMovie.setLayoutManager(horizontalLayoutManagaer);
        listViewMovie.setAdapter(adapterMovie);
        DatabaseReference refShows = database.getReference("Users").child(id).child("shows");
        refShows.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren()
                        ) {
                    String s = snap.getKey();
                    LoadTrendingShows("https://api.themoviedb.org/3/tv/"+s+"/recommendations?api_key="+API_KEY+"&language=en-US&page=1");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapterShow = new TrendAdapter(listShow);
        LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listViewShow.setLayoutManager(horizontalLayoutManagaer1);
        listViewShow.setAdapter(adapterShow);
        return v;
    }
    public void LoadTrendingMovies(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray array = response.getJSONArray("results");
                    for (int i =0;i<2;i++) {
                        JSONObject object = array.getJSONObject(i);
                        Trend movieTrend = new Trend(object.getString("id"), "https://image.tmdb.org/t/p/w200" + object.getString("poster_path"), "movie");
                        listMovie.add(movieTrend);
                        adapterMovie.notifyDataSetChanged();
                    }

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
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
    public void LoadTrendingShows(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray array = response.getJSONArray("results");
                    for (int i =0;i<2;i++) {
                        JSONObject object = array.getJSONObject(i);
                        Trend movieTrend = new Trend(object.getString("id"), "https://image.tmdb.org/t/p/w200" + object.getString("poster_path"), "show");
                        listShow.add(movieTrend);
                        adapterShow.notifyDataSetChanged();
                    }

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
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
