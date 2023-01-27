package com.lee.moviesearchpj.serachrecord.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lee.moviesearchpj.serachrecord.data.Record
import com.lee.moviesearchpj.serachrecord.repository.RecordRepository

class RecordViewModel(application: Application): AndroidViewModel(application) {
    private val repository: RecordRepository = RecordRepository(application)
    private val allRecords: LiveData<List<Record>>? = repository.allRecords

    fun deleteRecord(id: Int){
        repository.deleteRecord(id)
    }
    fun getAllRecords(): LiveData<List<Record>>?{
        return allRecords
    }
}