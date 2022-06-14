package com.example.yellowtimer.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yellowtimer.R

@Database(entities = [TaskEntity::class,UserEntity::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {

    companion object {
        private val LOCK = Any()
        private var sInstance: AppDB? = null
        fun getInstance(context: Context): AppDB? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    Log.d("LOG_TAG", "Creating new database instance")
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDB::class.java,
                        context.getString(R.string.databaseName)
                    )
                        .build()
                }
            }
            Log.d("LOG_TAG", "Getting the database instance")
            return sInstance
        }
    }

    abstract fun appDao(): AppDao?
}