package android.example.popularmoviespt1;

import android.example.popularmoviespt1.utils.MoviesRecyclerViewAdapter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    JSONObject jsonObject;
    JSONArray jsonArray;
    Toast toast;
    RecyclerView movies;
    final String API_KEY = "7d20fe59c0f72a12c165f5867aa3cb70";
    final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = (RecyclerView) findViewById(R.id.rv_movies);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        MoviesRecyclerViewAdapter moviesAdapter =
                new MoviesRecyclerViewAdapter(new ArrayList<String>());
        getImages("popular");
        movies.setLayoutManager(layoutManager);
        movies.setAdapter(moviesAdapter);
        toast = Toast.makeText(this,
                "Error retrieving data", Toast.LENGTH_LONG);
    }


    public void getImages(String query) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = BASE_URL + query + "?api_key=" + API_KEY;
        final ArrayList<String> imagesList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                imagesList.add("http://image.tmdb.org/t/p/w185//" + jsonArray
                                                .getJSONObject(i)
                                                .getString("poster_path")
                                );
                            }
                            //create new adapter
                            MoviesRecyclerViewAdapter newAdapter =
                                    new MoviesRecyclerViewAdapter(imagesList);
                            movies.swapAdapter(newAdapter, false);
                            System.err.println("size: " + imagesList.size());
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast.show();
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }


    public void mapMovies(String query, final String jsonQuery) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL = BASE_URL + query + "?api_key=" + API_KEY;
        final HashMap<Object, Object> map = new HashMap<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                map.put(jsonArray.getJSONObject(i).get("original_title"),
                                        jsonArray.getJSONObject(i).get(jsonQuery));
                            }
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast.show();
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }
}
