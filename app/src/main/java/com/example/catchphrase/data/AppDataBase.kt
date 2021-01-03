package com.example.catchphrase.data

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Project::class, Phrase::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}