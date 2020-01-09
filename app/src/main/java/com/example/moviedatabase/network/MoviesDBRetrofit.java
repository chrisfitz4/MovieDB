package com.example.moviedatabase.network;

import com.example.moviedatabase.model.query_result.RepoResultQuery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesDBRetrofit {

 //   https://api.themoviedb.org/3/search/movie
 //   ?api_key=5520ed41729e7e1df80b88f1ba50d622
 //   &language=en-US
 //   &query=fight
 //   &page=1
 //   &include_adult=false
    @GET("/3/search/movie")
    Call<RepoResultQuery> getMovies(
            @Query("api_key") String apiKey,
            @Query("query") String keyWords);

}
