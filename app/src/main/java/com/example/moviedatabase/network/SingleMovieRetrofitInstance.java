package com.example.moviedatabase.network;

import com.example.moviedatabase.model.movie.RepoResultMovie;
import com.example.moviedatabase.util.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleMovieRetrofitInstance {

    private SingleMovieRetrofit movieRetrofit;

    public SingleMovieRetrofitInstance(){
        movieRetrofit = (new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()).create(SingleMovieRetrofit.class);
    }



    public Call<RepoResultMovie> getMovie(int id){
        return movieRetrofit.getMovie(id,Constants.MY_API_KEY);
    }
}
