package com.lee.moviesearchpj.movie.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lee.moviesearchpj.movie.data.MovieData
import com.lee.moviesearchpj.movie.repository.MovieRepository
import com.lee.moviesearchpj.serachrecord.data.Record
import com.lee.moviesearchpj.serachrecord.repository.RecordRepository
import kotlinx.coroutines.*


class MovieViewModel(private val repository: MovieRepository, private val application: Application):ViewModel(){
    var movieList = MutableLiveData<MovieData>() // rest api 저장
    private lateinit var itemList:MovieData
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    private val recordRepository: RecordRepository = RecordRepository(application)
    private var job : Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("코루틴내 예외: ${throwable.localizedMessage}")
    }
    // api 정보 수신 함수
    fun getAllMovieFromViewModel(text: String, start:Int){
        job = CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            isLoading.postValue(true)
            val response = repository.getMovieInfo(text,start) // 검색어, 시작 번호
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    movieList.postValue(response.body())
                    itemList = response.body()!!
                    isLoading.postValue(false)
                }else{
                    onError("에러내용:  $response")
                }
            }
        }
    }
    // api 정보 수신 함수
    fun addAllMovieFromViewModel(text: String, start:Int){
        job = CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            isLoading.postValue(true)
            val response = repository.getMovieInfo(text,start) // 검색어, 시작 번호
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.let {
                        itemList.items.addAll(response.body()!!.items)
                        movieList.value = itemList
                    }
                    isLoading.postValue(false)
                }else{
                    onError("에러내용:  $response")
                }
            }
        }
    }
    // room 검색기록 저장 함수
    fun insertRecord(searchText: Record){
        recordRepository.insertRecord(searchText)
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