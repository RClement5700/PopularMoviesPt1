package android.example.popularmoviespt1;

//import android.example.popularmoviespt1.utils.Favorite;
//import android.example.popularmoviespt1.utils.FavoriteDB;
//import android.example.popularmoviespt1.utils.FavoriteExecutor;

import android.example.popularmoviespt1.utils.Favorite;
import android.example.popularmoviespt1.utils.FavoriteDB;
import android.example.popularmoviespt1.utils.Movie;
import android.example.popularmoviespt1.utils.Review;
import android.example.popularmoviespt1.utils.ReviewsRecyclerViewAdapter;
import android.example.popularmoviespt1.utils.Trailer;
import android.example.popularmoviespt1.utils.TrailerRecyclerViewAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private final static String API_KEY = "7d20fe59c0f72a12c165f5867aa3cb70";
    private final static String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private static String id;
    ProgressBar trailersProgressBar;
    RecyclerView trailers;
    ProgressBar reviewsProgressBar;
    RecyclerView reviews;
    ImageView emptyStar, goldStar;
    String title;
    String rating;
    String summary;
    String releaseDate;
    String posterPath;
    FavoriteDB favoriteDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        favoriteDB = FavoriteDB.getInstance(getApplicationContext());
        ImageView imageView = (ImageView) findViewById(R.id.iv_poster_thumbnail);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_rating = (TextView) findViewById(R.id.tv_rating);
        TextView tv_summary = (TextView) findViewById(R.id.tv_summary);
        TextView tv_release_date = (TextView) findViewById(R.id.tv_year);
        emptyStar = (ImageView) findViewById(R.id.iv_empty_star);
        goldStar = (ImageView) findViewById(R.id.iv_gold_star);
        goldStar.setVisibility(View.INVISIBLE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        Serializable serializableMovie = getIntent().getSerializableExtra("movie");
        if (serializableMovie != null) {
            if (serializableMovie.getClass() == Movie.class) {
                Movie movie = (Movie) serializableMovie;
                posterPath = movie.getPosterURL();
                Picasso.get()
                        .load(posterPath)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
                id = movie.getId();
                title = movie.getTitle();
                rating = movie.getRating() + "/10";
                summary = movie.getOverview();
                releaseDate = movie.getReleaseDate().substring(0, 4);
            }
            else if (serializableMovie.getClass() == Favorite.class) {
                Favorite movie = (Favorite) serializableMovie;
                posterPath = movie.getPoster_url();
                Picasso.get()
                        .load(posterPath)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
                id = movie.getId();
                title = movie.getTitle();
                rating = movie.getRating() + "/10";
                summary = movie.getOverview();
                releaseDate = movie.getReleaseDate().substring(0, 4);
            }

            tv_title.setText(title);
            tv_rating.setText(rating);
            tv_summary.setText(summary);
            tv_release_date.setText(releaseDate);
            getTrailers();
            getReviews();
        }
        trailersProgressBar = (ProgressBar) findViewById(R.id.trailers_progress_bar);
        trailers = (RecyclerView) findViewById(R.id.rv_trailers);
        trailers.setVisibility(View.INVISIBLE);
        trailers.setLayoutManager(new LinearLayoutManager(this));
        trailers.setAdapter(new TrailerRecyclerViewAdapter(new ArrayList<Trailer>()));

        reviewsProgressBar = (ProgressBar) findViewById(R.id.reviews_progress_bar);
        reviews = (RecyclerView) findViewById(R.id.rv_reviews);
        reviews.setVisibility(View.INVISIBLE);
        reviews.setLayoutManager(new LinearLayoutManager(this));
        reviews.setAdapter(new ReviewsRecyclerViewAdapter(new ArrayList<Review>()));
        favoriteDB.favoriteDao().getAll().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                for (int i = 0; i < favorites.size(); i++) {
                    String favorite_title = favorites.get(i).getTitle();
                    if (title.equals(favorite_title)) {
                        goldStar.setVisibility(View.VISIBLE);
                        emptyStar.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void isFavorite(View v) {
        goldStar.setVisibility(View.VISIBLE);
        emptyStar.setVisibility(View.INVISIBLE);
        final Favorite favorite = new Favorite(title, id, summary, rating, releaseDate, posterPath);
        favoriteDB.favoriteDao().getAll().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                favorites.add(favorite);
            }
        });
    }

    public void notFavorite(View v) {
        goldStar.setVisibility(View.INVISIBLE);
        emptyStar.setVisibility(View.VISIBLE);
        final Favorite favorite = new Favorite(title, id, summary, rating, releaseDate, posterPath);
        favoriteDB.favoriteDao().getAll().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                favorites.remove(favorite);
            }
        });
    }

    public void getReviews() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String URL =
                "https://api.themoviedb.org/3/movie/"+ id + "/reviews?api_key=" + API_KEY;
        final ArrayList<Review> reviewsList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject currentObject = jsonArray.getJSONObject(i);
                                reviewsList.add(new Review(currentObject.getString("author"),
                                        currentObject.getString("url")));
                            }
                            reviews.swapAdapter(new ReviewsRecyclerViewAdapter(reviewsList),
                                    true);
                            reviewsProgressBar.setVisibility(View.GONE);
                            reviews.setVisibility(View.VISIBLE);
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    public void getTrailers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String URL =
                "https://api.themoviedb.org/3/movie/"+ id + "/videos?api_key=" + API_KEY;
        final ArrayList<Trailer> trailersList = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject currentObject = jsonArray.getJSONObject(i);
                                String movieID = currentObject.getString("id");
                                String key = currentObject.getString("key");
                                String name = currentObject.getString("name");
                                String site = currentObject.getString("site");
                                String size = currentObject.getString("size");
                                String type = currentObject.getString("type");
                                String URL = YOUTUBE_URL + key;
                                trailersList.add(new Trailer(movieID, key, name, site, size, type, URL));
                            }
                            trailers.swapAdapter(new TrailerRecyclerViewAdapter(trailersList),
                                    true);
                            trailersProgressBar.setVisibility(View.GONE);
                            trailers.setVisibility(View.VISIBLE);

                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }
}
