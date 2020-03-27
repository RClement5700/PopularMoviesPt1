package android.example.popularmoviespt1.utils;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.example.popularmoviespt1.R;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
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
    public void onBindViewHolder(@NonNull final TrailerViewHolder holder, final int position) {

        holder.tv_trailer.setText(trailers.get(position).name);
        holder.imgBtn_play.setClickable(true);
        holder.imgBtn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAppInstalled("com.google.android.youtube",
                        v.getContext().getPackageManager())) {
                    holder.wv_trailer.loadUrl(trailers.get(position).URL);
                }
                else {
                    String urlString = trailers.get(position).URL;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    try {
                        v.getContext().startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        // Chrome browser presumably not installed so allow user to choose instead
                        intent.setPackage(null);
                        v.getContext().startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    protected boolean isAppInstalled(String packageName, @NonNull PackageManager packageManager) {
        Intent mIntent = packageManager.getLaunchIntentForPackage(packageName);
        return mIntent != null;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trailer;
        ImageButton imgBtn_play;
        WebView wv_trailer;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBtn_play = itemView.findViewById(R.id.imgBtn_play);
            tv_trailer = itemView.findViewById(R.id.tv_trailer_title);
            wv_trailer = itemView.findViewById(R.id.wv_trailer);
            wv_trailer.getSettings().setJavaScriptEnabled(true);
            wv_trailer.setWebChromeClient(new WebChromeClient());
        }
    }
 }
