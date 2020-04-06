package android.example.popularmoviespt1;

import android.app.Application;
import android.example.popularmoviespt1.utils.Favorite;
import android.example.popularmoviespt1.utils.FavoriteDB;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Favorite>> favorites;
    public MainViewModel(@NonNull Application application) {
        super(application);
        favorites =
                FavoriteDB.getInstance(application.getApplicationContext()).favoriteDao().getAll();
    }

    public LiveData<List<Favorite>> getFavorites() {
        return favorites;
    }
}
