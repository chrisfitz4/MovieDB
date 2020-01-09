package com.example.moviedatabase.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.transition.TransitionInflater;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.moviedatabase.R;
import com.example.moviedatabase.model.query_result.Movie;
import com.example.moviedatabase.util.Constants;

import javax.security.auth.callback.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMovieFrag extends Fragment {

    @BindView(R.id.movie_icon_frag)
    ImageView icon;
    @BindView(R.id.movieTitle_frag)
    TextView title;
    @BindView(R.id.movieDescrip_frag)
    TextView description;
    @BindView(R.id.movieReleaseDate_frag)
    TextView date;
    @BindView(R.id.movieRating_frag)
    TextView rating;
    @BindView(R.id.base_image_frag)
    ImageView baseView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.single_movie_frag,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        postponeEnterTransition();
        Bundle args = this.getArguments();
        Movie movie = args.getParcelable(Constants.RETRIEVE_MOVIE_KEY);

        //attempting to do a shared element transition (unsuccessfully)
        String transitionName = Constants.TRANSITION_NAME_BASE+movie.getId();
        icon.setTransitionName(transitionName);
        Log.d("TAG_X", "onViewCreated: "+transitionName);
        Glide.with(this)
                .load(Constants.BASE_URL_IMAGE+movie.getPosterPath())
                .dontAnimate()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(icon);

        title.setText(movie.getTitle());
        if(movie.getTitle().length()>20){
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        }
        description.setText(movie.getOverview());
        description.setMovementMethod(new ScrollingMovementMethod());
        date.setText(movie.getReleaseDate());
        rating.setText("Rating: "+movie.getVoteAverage());
        Glide.with(this)
                .load(Constants.BASE_URL_IMAGE+movie.getBackdropPath())
                .into(baseView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getFragmentManager().popBackStack();
    }
}
