package android.example.popularmoviespt1;

import android.content.res.Configuration;
import android.example.popularmoviespt1.utils.FavoriteDB;
import android.example.popularmoviespt1.utils.Movie;
import android.example.popularmoviespt1.utils.MoviesRecyclerViewAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

//import android.example.popularmoviespt1.utils.FavoriteDB;

public class MainActivity extends AppCompatActivity {
    JSONObject jsonObject;
    JSONArray jsonArray;
    Toast toast;
    RecyclerView movies;
    Spinner spinner_sort;
    ProgressBar progressBar;
    final String API_KEY = "7d20fe59c0f72a12c165f5867aa3cb70";
    final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    FavoriteDB favoriteDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        favoriteDB = FavoriteDB.getInstance(getApplicationContext());
        for (int i = 0; i < favoriteDB.favoriteDao().getAll().size(); i++) {
            System.out.println("favorite: " + favoriteDB.favoriteDao().getAll().get(i).getTitle());
        }
        spinner_sort = (Spinner) findViewById(R.id.spinner_sort);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_order, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_sort.setAdapter(adapter);
        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    getImages("top_rated");
                }
                if (i == 0) {
                    getImages("popular");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.movies_progress_bar);
        movies = (RecyclerView) findViewById(R.id.rv_movies);
        movies.setVisibility(View.INVISIBLE);
        movies.setLayoutManager(new GridLayoutManager(this, 2));
        movies.setAdapter(new MoviesRecyclerViewAdapter(new ArrayList<Movie>()));
        getImages("popular");
        toast = Toast.makeText(this,
                "Error retrieving data", Toast.LENGTH_LONG);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getImages(String query) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = BASE_URL + query + "?api_key=" + API_KEY;
        final ArrayList<Movie> moviesList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject currentObject = jsonArray.getJSONObject(i);
                                String title = currentObject.getString("original_title");
                                String rating = currentObject.getString("vote_average");
                                String overview = currentObject.getString("overview");
                                String releaseDate = currentObject.getString("release_date");
                                String id = currentObject.getString("id");
                                String posterURL
                                        = "https://image.tmdb.org/t/p/w780//" +
                                        currentObject.getString("poster_path");

                                Movie currentMovie = new Movie(id, title, rating, overview, releaseDate, posterURL);
                                moviesList.add(currentMovie);
                            }
                            //create new adapter
                            MoviesRecyclerViewAdapter newAdapter =
                                    new MoviesRecyclerViewAdapter(moviesList);
                            movies.swapAdapter(newAdapter,true);
                            progressBar.setVisibility(View.GONE);
                            movies.setVisibility(View.VISIBLE);
                            if (getApplicationContext().getResources().getConfiguration().orientation
                                == Configuration.ORIENTATION_LANDSCAPE) {
                                LinearLayoutManager llm = new LinearLayoutManager(getParent());
                                llm.canScrollHorizontally();
                                llm.setOrientation(RecyclerView.HORIZONTAL);
                                movies.setLayoutManager(llm);
                                movies.setHasFixedSize(true);
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
