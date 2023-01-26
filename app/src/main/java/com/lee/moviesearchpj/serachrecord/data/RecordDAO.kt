package com.lee.moviesearchpj.serachrecord.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDAO {
    @Insert
    fun insertRecord(record: Record)

    @Query("SELECT * FROM record_tbl WHERE recordText = :name")
    fun findRecord(name: String): List<Record>

    @Query("DELETE FROM record_tbl WHERE recordText = :name")
    fun deleteRecord(name: String)

    @Query("SELECT * FROM record_tbl")
    fun getAllRecord(): LiveData<List<Record>>
}