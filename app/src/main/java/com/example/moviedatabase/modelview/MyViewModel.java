package com.example.moviedatabase.modelview;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedatabase.model.query_result.Movie;
import com.example.moviedatabase.model.query_result.RepoResultQuery;
import com.example.moviedatabase.network.MovieDBRetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyViewModel extends AndroidViewModel {

    private MutableLiveData<RepoResultQuery> liveData;
    private MovieDBRetrofitInstance instance = new MovieDBRetrofitInstance();

    public MyViewModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
    }

    public void getRepo(String toSearch){

        instance.getMovies(toSearch).enqueue(new Callback<RepoResultQuery>() {
            @Override
            public void onResponse(Call<RepoResultQuery> call, Response<RepoResultQuery> response) {

                String name = "";
                if (response.body().getMovies().size() != 0) {
                    name = response.body().getMovies().get(0).getTitle();
                }
                liveData.setValue(response.body());
                Log.d("TAG_X", "onResponse: "+name);
            }

            @Override
            public void onFailure(Call<RepoResultQuery> call, Throwable t) {
                Log.d("TAG_X", "onFailure: "+t.getMessage());
                Log.d("TAG_X", "onFailure: "+call.toString());
            }
        });
    }


    public MutableLiveData<RepoResultQuery> getLiveData(){
        return liveData;
    }


}
