package android.example.popularmoviespt1.utils;

import android.example.popularmoviespt1.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter
        <MoviesRecyclerViewAdapter.MoviesRecyclerViewHolder> {

    ArrayList<String> images;

    public MoviesRecyclerViewAdapter(ArrayList<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public MoviesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MoviesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesRecyclerViewHolder holder, int position) {
        //use picasso here to set image resource
        Picasso.get()
                .load(images.get(position))
                .fit()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    class MoviesRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MoviesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_movie_poster);
        }
    }
}
