package android.example.popularmoviespt1.utils;


import android.example.popularmoviespt1.R;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewsRecyclerViewAdapter extends
        RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ReviewViewHolder> {

    ArrayList<Review> reviews;

    public ReviewsRecyclerViewAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_list_item, parent, false);
        return new ReviewsRecyclerViewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.tv_author.setText(reviews.get(position).author);
        String url = reviews.get(position).url;
        String link = "<a href='" + url + "'>" + url + "</a>";
        holder.tv_url.setText(Html.fromHtml(link));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView tv_author;
        TextView tv_url;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_url= itemView.findViewById(R.id.tv_url);
            tv_url.setClickable(true);
            tv_url.setMovementMethod(LinkMovementMethod.getInstance());

        }
    }
 }
