package android.example.popularmoviespt1.utils;

public class Trailer {

    String id, key, name, site, size, type, URL;

    public Trailer(String id, String key, String name, String site, String size, String type,
                   String URL) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
        this.URL = URL;
    }
    public Trailer(String id, String key, String name, String site, String size, String type) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }
}
