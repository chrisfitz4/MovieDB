package com.example.moviedatabase.adapter;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedatabase.R;
import com.example.moviedatabase.model.query_result.Movie;
import com.example.moviedatabase.util.Constants;

import java.util.List;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.MyViewHolder> {

    public interface TransitionInterface{
        void transitionImage(Movie movie, ImageView view);
    }

    private Context context;
    private List<Movie> movies;
    private TransitionInterface anInterface;

    public MyRVAdapter(Context context, List<Movie> movies, TransitionInterface anInterface) {
        this.movies = movies;
        this.context = context;
        this.anInterface = anInterface;
    }

    @NonNull
    @Override
    public MyRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyRVAdapter.MyViewHolder holder, final int position) {
        String titleText = movies.get(position*2).getTitle().trim();
        holder.title.setText(titleText);
        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,setTextSize(titleText));
        String movieURL = movies.get(position*2).getPosterPath();
        movieURL = Constants.BASE_URL_IMAGE+movieURL;
        //Log.d("TAG_X", "onBindViewHolder: "+movieURL);
        Glide.with(context).load(movieURL).placeholder(R.drawable.icon_android)
                .into(holder.icon);
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anInterface.transitionImage(movies.get(position*2),holder.icon);
            }
        });
        ViewCompat.setTransitionName(holder.icon,Constants.TRANSITION_NAME_BASE+movies.get(position*2).getId());
        if(movies.size()%2!=0&&position==getItemCount()){
            holder.title2.setVisibility(View.INVISIBLE);
            holder.icon2.setVisibility(View.INVISIBLE);
        }else{
            String titleText2 = movies.get(position*2+1).getTitle().trim();
            holder.title2.setText(titleText2);
            holder.title2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,setTextSize(titleText2));
            String movieURL2 = Constants.BASE_URL_IMAGE+movies.get(position*2+1).getPosterPath();
            Glide.with(context).load(movieURL2).placeholder(R.drawable.icon_android).into(holder.icon2);
            holder.icon2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anInterface.transitionImage(movies.get(position*2+1),holder.icon2);
                }
            });
            ViewCompat.setTransitionName(holder.icon2,Constants.TRANSITION_NAME_BASE+movies.get(position*2+1).getId());
        }
    }

    private int setTextSize(String title){
        if(title.length()>30){
            Log.d("TAG_X", "setTextSize: "+title+title.length());
            return 16;
        }else{
            return 24;
        }
    }


    @Override
    public int getItemCount() {
        return movies.size()/2;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        TextView title2;
        ImageView icon2;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movieTitle_frag);
            icon = itemView.findViewById(R.id.movieIcon);
            title2 = itemView.findViewById(R.id.movieTitle2);
            icon2 = itemView.findViewById(R.id.movieIcon2);
        }
    }
}
