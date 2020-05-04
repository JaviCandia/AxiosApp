package com.example.axios.Interface

import com.example.axios.Model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("marvel")
    fun getMovieList(): Call<MutableList<Movie>>
}