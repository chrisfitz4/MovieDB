package com.example.moviedatabase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedatabase.R;
import com.example.moviedatabase.adapter.MyRVAdapter;
import com.example.moviedatabase.model.query_result.Movie;
import com.example.moviedatabase.model.query_result.RepoResultQuery;
import com.example.moviedatabase.modelview.MyViewModel;
import com.example.moviedatabase.network.MovieDBRetrofitInstance;
import com.example.moviedatabase.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MyRVAdapter.TransitionInterface {

    @BindView(R.id.send_query_button)
    Button sendButton;
    @BindView(R.id.my_query_editText)
    EditText editText;
    @BindView(R.id.listMovies_rv)
    RecyclerView recyclerView;

    Handler handler;

    MovieDBRetrofitInstance instance;

    List<Movie> movies = new ArrayList<>();
    MyRVAdapter adapter;
    Fragment fragment = new ViewMovieFrag();
    MyViewModel viewModel;
    Observer<RepoResultQuery> myObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_trans));

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        myObservable = new Observer<RepoResultQuery>() {
            @Override
            public void onChanged(RepoResultQuery repoResultQuery) {
                movies = repoResultQuery.getMovies();
                Log.d("TAG_X", "onChanged: "+movies.size());
                setUpRV();
            }
        };
        viewModel.getLiveData().observe(this,myObservable);

        handler = new Handler();

        instance = new MovieDBRetrofitInstance();

        adapter = new MyRVAdapter(this.getApplicationContext(),movies,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void setUpRV(){
        adapter = new MyRVAdapter(this.getApplicationContext(),movies,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    public void onClick(View view){
        movies.clear();
//        instance.getMovies(editText.getText().toString().trim()).enqueue(new Callback<RepoResultQuery>() {
//
//            @Override
//            public void onResponse(Call<RepoResultQuery> call, Response<RepoResultQuery> response) {
//                Log.d("TAG_X", "onResponse: "+movies.size());
//                final String name = response.body().getMovies().get(0).getTitle();
//                for(int i = 0; i<response.body().getMovies().size();i++){
//                    movies.add(response.body().getMovies().get(i));
//                }
//                Log.d("TAG_X", "onResponse: 2: "+movies.size());
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.notifyDataSetChanged();
//                        Log.d("TAG_X", "run: "+adapter.getItemCount());
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<RepoResultQuery> call, Throwable t) {
//                Log.d("TAG_X", "onFailure: "+t.getMessage());
//                Log.d("TAG_X", "onFailure: "+call.toString());
//            }
//        });
        String toSearch = editText.getText().toString().trim();
        if(toSearch.length()!=0) {
            Log.d("TAG_X", "onClick: "+toSearch);
            viewModel.getRepo(toSearch);
        }
        ((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(sendButton.getWindowToken(),0);
    }



    @Override
    public void transitionImage(Movie movie, ImageView view) {
        Log.d("TAG_X", "transitionImage: "+movie.getTitle());
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.RETRIEVE_MOVIE_KEY,movie);
        bundle.putString("test","test success");
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                this,
//                view,
//                Constants.TRANSITION_NAME_BASE+movie.getId()
//        );
        Log.d("TAG_X", "transitionImage: "+view.getTransitionName());
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(fragment.getTag())
                .addSharedElement(view,Constants.TRANSITION_NAME_BASE+movie.getId())
                .add(R.id.frame_layout,fragment)
                .commit();
    }
}
