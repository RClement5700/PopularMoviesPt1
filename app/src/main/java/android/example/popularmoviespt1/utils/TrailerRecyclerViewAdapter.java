package android.example.popularmoviespt1.utils;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrailerRecyclerViewAdapter extends
        RecyclerView.Adapter<TrailerRecyclerViewAdapter.TrailerViewHolder> {

    ArrayList<Trailer> trailers = new ArrayList<>();


    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {


        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
 }
