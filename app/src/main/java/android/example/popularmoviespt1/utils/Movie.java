package android.example.popularmoviespt1.utils;

import java.io.Serializable;

public class Movie implements Serializable {


    String id, title, rating, overview, releaseDate, posterURL;

    public Movie(String id, String title, String rating, String overview, String releaseDate, String posterURL) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterURL = posterURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }
}
