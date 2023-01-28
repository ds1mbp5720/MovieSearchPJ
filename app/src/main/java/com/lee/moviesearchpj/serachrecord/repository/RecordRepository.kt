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
    // id 기준 삭제 함수
    fun deleteRecordByID(id: Int){
        coroutineScope.launch(Dispatchers.IO){
            asyncDeleteByID(id)
        }
    }
    private fun asyncDeleteByID(id:Int){
        recordDao?.deleteRecordByID(id)
    }
    // 이름 기준 삭제 함수
    fun deleteRecordByName(text: String){
        coroutineScope.launch(Dispatchers.IO){
            asyncDeleteByName(text)
        }
    }
    private fun asyncDeleteByName(text:String){
        recordDao?.deleteRecordByName(text)
    }
}