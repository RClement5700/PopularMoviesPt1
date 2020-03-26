package android.example.popularmoviespt1.utils;

import android.content.Intent;
import android.example.popularmoviespt1.DetailsActivity;
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

    ArrayList<Movie> movies;

    public MoviesRecyclerViewAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }



    @NonNull
    @Override
    public MoviesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MoviesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesRecyclerViewHolder holder, final int position) {

        Picasso.get()
                .load(movies.get(position).posterURL)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
        holder.imageView.setClickable(true);
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra("movie", movies.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    class MoviesRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MoviesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_movie_poster);

        }

    }
}
