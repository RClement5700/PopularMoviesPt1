package android.example.popularmoviespt1.utils;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favorite")
public class Favorite implements Serializable {

    @PrimaryKey
    @NonNull
    private String title;
    private String id;
    private String poster_url;
    private String overview;
    private String rating;
    private String releaseDate;

    public Favorite(@NonNull String title, String id, String overview,
                    String rating, String releaseDate, String poster_url) {
        this.title = title;
        this.id = id;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.poster_url = poster_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
