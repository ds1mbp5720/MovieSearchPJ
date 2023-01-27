package com.lee.moviesearchpj.serachrecord.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.lee.moviesearchpj.serachrecord.data.Record
import com.lee.moviesearchpj.serachrecord.data.RecordDAO
import com.lee.moviesearchpj.serachrecord.data.RecordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordRepository(application: Application) {
    private var recordDao: RecordDAO?
    private var coroutineScope = CoroutineScope(Dispatchers.IO)
    val allRecords: LiveData<List<Record>>?

    init {
        val db: RecordRoomDatabase? = RecordRoomDatabase.getDatabase(application)
        recordDao = db?.recordDao()
        allRecords = recordDao?.getAllRecord()
    }
    // data 입력 함수
    fun insertRecord(newRecord: Record){
        coroutineScope.launch(Dispatchers.IO){
            asyncInsert(newRecord)
        }
    }
    private fun asyncInsert(record: Record){
        recordDao?.insertRecord(record)
    }
    fun deleteRecord(id: Int){
        coroutineScope.launch(Dispatchers.IO){
            asyncDelete(id)
        }
    }
    private fun asyncDelete(id:Int){
        recordDao?.deleteRecord(id)
    }
}