package android.example.popularmoviespt1.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoriteExecutor {

    private static final Object LOCK = new Object();
    private static FavoriteExecutor sInstance;
    private final Executor diskIO;

    public FavoriteExecutor(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static FavoriteExecutor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new FavoriteExecutor(Executors.newSingleThreadExecutor());
//                        Executors.newFixedThreadPool(3,
//                        new FavoriteThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    private static class FavoriteThreadExecutor implements Executor {

        private Handler favoriteThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            favoriteThreadHandler.post(command);
        }
    }

}
