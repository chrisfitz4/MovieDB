package com.example.moviedatabase.network;

import com.example.moviedatabase.model.movie.RepoResultMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SingleMovieRetrofit {

    // https://api.themoviedb.org/3/movie/550?api_key=5520ed41729e7e1df80b88f1ba50d622
    @GET("/3/movie/{path}")
    Call<RepoResultMovie> getMovie(
            @Path("path") int movieId,
            @Query("api_key") String basePath
    );

}
