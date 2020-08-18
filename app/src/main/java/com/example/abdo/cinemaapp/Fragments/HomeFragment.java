package com.example.abdo.cinemaapp.Fragments;


import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public HomeFragment()
    {

    }
    RecyclerView movieTrendRecyclerView;
    TrendAdapter movieTrendAdapter;
    ArrayList<Trend> movieArrayList;
    RecyclerView personTrendRecyclerView;
    TrendAdapter personTrendAdapter;
    ArrayList<Trend> personArrayList;
    ArrayList<Trend> showArrayList;
    String API_KEY;
    RecyclerView showTrendRecyclerView;
    TrendAdapter showTrendAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        movieArrayList = new ArrayList<>();
        showArrayList = new ArrayList<>();
        personArrayList = new ArrayList<>();
        API_KEY = getString(R.string.API_KEY);
        LoadTrendingMovies("https://api.themoviedb.org/3/trending/all/week?api_key="+API_KEY);
        LoadTrendingShows("https://api.themoviedb.org/3/trending/tv/week?api_key="+API_KEY);
        LoadTrendingPerson("https://api.themoviedb.org/3/trending/person/week?api_key="+API_KEY);
         movieTrendRecyclerView = v.findViewById(R.id.MovieTrendRecyclerView);
        showTrendRecyclerView = v.findViewById(R.id.ShowsTrendRecyclerView);
        personTrendRecyclerView = v.findViewById(R.id.PersonsTrendRecyclerView);
         movieTrendAdapter = new TrendAdapter(movieArrayList);
         showTrendAdapter = new TrendAdapter(showArrayList);
           personTrendAdapter = new TrendAdapter(personArrayList);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutManagaer3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        movieTrendRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        showTrendRecyclerView.setLayoutManager(horizontalLayoutManagaer2);
        personTrendRecyclerView.setLayoutManager(horizontalLayoutManagaer3);
        movieTrendRecyclerView.setAdapter(movieTrendAdapter);
        showTrendRecyclerView.setAdapter(showTrendAdapter);
        personTrendRecyclerView.setAdapter(personTrendAdapter);
        return v;
    }
    public void LoadTrendingPerson(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray array = response.getJSONArray("results");
                    for (int i =0;i<5;i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        Trend movieTrend = new Trend(object.getString("id"),"https://image.tmdb.org/t/p/w200"+object.getString("profile_path"),"person");
                        personArrayList.add(movieTrend);
                        personTrendAdapter.notifyDataSetChanged();
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
    public void LoadTrendingMovies(String url)
    {
         JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray array = response.getJSONArray("results");
                    for (int i =0;i<5;i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        Trend movieTrend = new Trend(object.getString("id"),"https://image.tmdb.org/t/p/w200"+object.getString("poster_path"),"movie");
                        movieArrayList.add(movieTrend);
                        movieTrendAdapter.notifyDataSetChanged();
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
                    for (int i =0;i<5;i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        Trend movieTrend = new Trend(object.getString("id"),"https://image.tmdb.org/t/p/w200"+object.getString("poster_path"),"show");
                        showArrayList.add(movieTrend);
                        showTrendAdapter.notifyDataSetChanged();
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
