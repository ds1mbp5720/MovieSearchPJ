package com.lee.moviesearchpj.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lee.moviesearchpj.movie.data.MovieData
import kotlinx.coroutines.*


class MovieViewModel(private val repository: MovieRepository):ViewModel(){
    val movieList = MutableLiveData<MovieData>() // rest api 저장
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    private var job : Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("코루틴내 예외: ${throwable.localizedMessage}")
    }
    fun getAllMovieFromViewModel(text: String, start:Int){
        job = CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            isLoading.postValue(true)
            val response = repository.getMovieInfo(text,start) // 검색어, 시작 번호
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    movieList.postValue(response.body())
                    isLoading.postValue(false)
                }else{
                    onError("에러내용:  $response")
                }
            }
        }
    }

    private fun onError(message: String){
        errorMessage.postValue(message)
        isLoading.postValue(false)
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}