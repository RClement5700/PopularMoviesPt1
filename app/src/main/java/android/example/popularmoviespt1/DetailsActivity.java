package android.example.popularmoviespt1;

import android.example.popularmoviespt1.utils.Movie;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView imageView = (ImageView) findViewById(R.id.iv_poster_thumbnail);
        TextView tv_title = (TextView) findViewById(R.id.movie_title);
        TextView tv_rating = (TextView) findViewById(R.id.rating);
        TextView tv_summary = (TextView) findViewById(R.id.summary);
        TextView tv_release_date = (TextView) findViewById(R.id.release_date);

        Serializable serializableMovie = getIntent().getSerializableExtra("movie");
        if (serializableMovie != null) {
            Movie movie = (Movie) serializableMovie;
            String poster_path = movie.getPosterURL();
            Picasso.get()
                    .load(poster_path)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
            String title = tv_title.getText().toString() + " " + movie.getTitle();
            String rating = tv_rating.getText().toString() + " " + movie.getRating();
            String summary = tv_summary.getText().toString() + " " + movie.getOverview();
            String releaseDate = tv_release_date.getText().toString() + " " + movie.getReleaseDate();

            tv_title.setText(title);
            tv_rating.setText(rating);
            tv_summary.setText(summary);
            tv_release_date.setText(releaseDate);
        }
    }
}
