package com.lee.moviesearchpj.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory (private var repository: MovieRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            MovieViewModel(repository) as T
        }else{
            throw IllegalArgumentException("해당 뷰모델을 찾을수 없습니다.")
        }
    }
}