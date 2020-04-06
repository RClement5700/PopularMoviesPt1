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

import java.util.List;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter
        <FavoriteRecyclerViewAdapter.FavoriteRecyclerViewHolder> {

    List<Favorite> favorites;

    public FavoriteRecyclerViewAdapter(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoriteRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new FavoriteRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteRecyclerViewHolder holder, final int position) {
        Picasso.get()
                .load(favorites.get(position).getPoster_url())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
        holder.imageView.setClickable(true);
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra("movie", favorites.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }


    class FavoriteRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public FavoriteRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_movie_poster);
        }
    }
}
