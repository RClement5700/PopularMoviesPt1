package android.example.popularmoviespt1.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favorite.class}, version = 1, exportSchema = false)
public abstract class FavoriteDB extends RoomDatabase {
    private static FavoriteDB sInstance;
    private final static Object LOCK = new Object();
    private static final String DB_NAME = "favorites";

    public static FavoriteDB getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavoriteDB.class, DB_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract FavoriteDao favoriteDao();
}
