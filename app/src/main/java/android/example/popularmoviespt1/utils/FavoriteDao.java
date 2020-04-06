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
    LiveData<List<Favorite>> getAll();

//    @Query("SELECT * FROM favorite WHERE title IN (:titles)")
//    List<Favorite>getTitles(String titles);

    @Insert
    void addFavorite(Favorite favorite);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Favorite favorite);


    @Delete
    void deleteFavorite(Favorite favorite);
}
