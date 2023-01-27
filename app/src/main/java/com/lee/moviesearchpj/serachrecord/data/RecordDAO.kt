package com.lee.moviesearchpj.serachrecord.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDAO {
    @Insert
    fun insertRecord(record: Record)

    @Query("SELECT * FROM record_tbl WHERE recordId = :id")
    fun findRecord(id: Int): List<Record>

    @Query("DELETE FROM record_tbl WHERE recordId = :id")
    fun deleteRecord(id: Int)

    @Query("SELECT * FROM record_tbl")
    fun getAllRecord(): LiveData<List<Record>>
}