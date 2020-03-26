package android.example.popularmoviespt1.utils;


import android.example.popularmoviespt1.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrailerRecyclerViewAdapter extends
        RecyclerView.Adapter<TrailerRecyclerViewAdapter.TrailerViewHolder> {

    ArrayList<Trailer> trailers;

    public TrailerRecyclerViewAdapter(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerRecyclerViewAdapter.TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, final int position) {

        holder.tv_trailer.setText(trailers.get(position).name);
        holder.imgBtn_play.setClickable(true);
//        holder.imgBtn_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trailer;
        ImageButton imgBtn_play;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBtn_play = itemView.findViewById(R.id.imgBtn_play);
            tv_trailer = itemView.findViewById(R.id.tv_trailer_title);
        }
    }
 }
