package com.example.moviedatabase.network;

import com.example.moviedatabase.model.query_result.RepoResultQuery;
import com.example.moviedatabase.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDBRetrofitInstance {

    private MoviesDBRetrofit moviesDBRetrofitInstance;

    public MovieDBRetrofitInstance() {
        moviesDBRetrofitInstance = getInstanceOfMoviesDBRetrofit(getInstance());
    }

    MoviesDBRetrofit getInstanceOfMoviesDBRetrofit(Retrofit retrofit){
        return retrofit.create(MoviesDBRetrofit.class);
    }

    private Retrofit getInstance(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Call<RepoResultQuery> getMovies(String toSearch){
        return moviesDBRetrofitInstance.getMovies(Constants.MY_API_KEY,toSearch);
    }

}
