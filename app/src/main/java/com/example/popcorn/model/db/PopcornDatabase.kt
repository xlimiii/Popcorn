package com.example.popcorn.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favourite::class], version = 2, exportSchema = false)
abstract class PopcornDatabase : RoomDatabase() {
    abstract fun favouriteDao() : FavouriteDao

    companion object{
        @Volatile
        private var INSTANCE : PopcornDatabase? = null
        fun getDatabase(context: Context) : PopcornDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            else synchronized(this)
            {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PopcornDatabase::class.java,
                        "popcorn_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}