package android.example.popularmoviespt1.utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    List<Favorite> getAll();

    @Insert
    void addFavorite(Favorite favorite);

    @Query("SELECT * FROM favorite WHERE title = :title")
    LiveData<Favorite> loadFavoriteByTitle(String title);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Favorite favorite);


    @Delete
    void deleteFavorite(Favorite favorite);
}
