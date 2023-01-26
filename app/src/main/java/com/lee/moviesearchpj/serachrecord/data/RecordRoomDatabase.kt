package com.lee.moviesearchpj.serachrecord.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [(Record::class)], exportSchema = false, version = 1)
abstract class RecordRoomDatabase: RoomDatabase() {
    abstract fun recordDao(): RecordDAO
    companion object {
        private lateinit var INSTANCE: RecordRoomDatabase
        internal  fun getDatabase(context: Context): RecordRoomDatabase{
            if (!this::INSTANCE.isInitialized){
                synchronized(RecordRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RecordRoomDatabase::class.java,
                        "record_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}