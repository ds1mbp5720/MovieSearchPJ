package com.lee.moviesearchpj.movie.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lee.moviesearchpj.movie.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory (private var repository: MovieRepository, private var application: Application): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            MovieViewModel(repository, application) as T
        }else{
            throw IllegalArgumentException("해당 뷰모델을 찾을수 없습니다.")
        }
    }
}