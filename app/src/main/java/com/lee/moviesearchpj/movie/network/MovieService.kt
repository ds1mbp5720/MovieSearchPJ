package com.lee.moviesearchpj.movie.network

import com.lee.moviesearchpj.BuildConfig
import com.lee.moviesearchpj.movie.data.MovieData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val MOVIE_ADDRESS = "https://openapi.naver.com/"

interface MovieService{

    @Headers("X-Naver-Client-Id:${BuildConfig.NAVER_CLIENT_ID}",
        "X-Naver-Client-Secret:${BuildConfig.NAVER_CLIENT_SECRET}")
    @GET("v1/search/movie.json")
    suspend fun getMovieInfo(
        @Query("query") query: String, // 검색어, 영화 제목
        @Query("display") display: Int, // 표시 결과값 수
        @Query("start") start: Int,  // 검색 시작 위치
    ): Response<MovieData>

    companion object {
        var retrofitService: MovieService? = null
        fun getInstance() : MovieService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(MOVIE_ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(MovieService::class.java)
            }
            return retrofitService!!
        }
    }
}