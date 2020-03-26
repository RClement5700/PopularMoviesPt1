package android.example.popularmoviespt1;

import android.example.popularmoviespt1.utils.Movie;
import android.example.popularmoviespt1.utils.Trailer;
import android.example.popularmoviespt1.utils.TrailerRecyclerViewAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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

public class DetailsActivity extends AppCompatActivity {

    private final static String API_KEY = "7d20fe59c0f72a12c165f5867aa3cb70";
    private static String id;
    ProgressBar progressBar;
    RecyclerView trailers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView imageView = (ImageView) findViewById(R.id.iv_poster_thumbnail);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_rating = (TextView) findViewById(R.id.tv_rating);
        TextView tv_summary = (TextView) findViewById(R.id.tv_summary);
        TextView tv_release_date = (TextView) findViewById(R.id.tv_year);

        Serializable serializableMovie = getIntent().getSerializableExtra("movie");
        if (serializableMovie != null) {
            Movie movie = (Movie) serializableMovie;
            String poster_path = movie.getPosterURL();
            Picasso.get()
                    .load(poster_path)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
            id = movie.getId();
            String title = movie.getTitle();
            String rating = movie.getRating() + "/10";
            String summary = movie.getOverview();
            String releaseDate = movie.getReleaseDate().substring(0, 4);

            tv_title.setText(title);
            tv_rating.setText(rating);
            tv_summary.setText(summary);
            tv_release_date.setText(releaseDate);
            getTrailers();
        }
        progressBar = (ProgressBar) findViewById(R.id.trailers_progress_bar);
        trailers = (RecyclerView) findViewById(R.id.rv_trailers);
        trailers.setVisibility(View.INVISIBLE);
        trailers.setLayoutManager(new LinearLayoutManager(this));
        trailers.setAdapter(new TrailerRecyclerViewAdapter(new ArrayList<Trailer>()));
    }

    public void getTrailers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String TRAILER_URL =
                "https://api.themoviedb.org/3/movie/"+ id +"/videos?api_key=" + API_KEY;
        final ArrayList<Trailer> trailersList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, TRAILER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject currentObject = jsonArray.getJSONObject(i);
                                //System.err.println("Objects: " + currentObject);
                                String movieID = currentObject.getString("id");
                                String key = currentObject.getString("key");
                                String name = currentObject.getString("name");
                                String site = currentObject.getString("site");
                                String size = currentObject.getString("size");
                                String type = currentObject.getString("type");
                                trailersList.add(new Trailer(movieID, key, name, site, size, type));
                            }
                            trailers.swapAdapter(new TrailerRecyclerViewAdapter(trailersList),
                                    true);
                            progressBar.setVisibility(View.GONE);
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
