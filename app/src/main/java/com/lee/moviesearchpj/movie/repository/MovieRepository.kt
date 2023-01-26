package com.lee.moviesearchpj.movie.repository

import com.lee.moviesearchpj.movie.network.MovieService

class MovieRepository(private val movieService: MovieService) {
    // 검색어, 시작 번호
    suspend fun getMovieInfo(query: String, start:Int)
            = movieService.getMovieInfo (query,10,start)
}