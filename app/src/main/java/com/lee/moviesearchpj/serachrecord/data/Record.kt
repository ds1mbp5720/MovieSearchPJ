package com.lee.moviesearchpj.serachrecord.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "record_tbl")
class Record {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "recordId")
    var id: Int = 0

    @ColumnInfo(name = "recordText")
    var recordText: String? = null

    constructor(){}
    @Ignore
    constructor(recordText: String){
        this.recordText = recordText
    }
}