package com.example.projectandroidfundamental.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun FavoriteDao(): FavoriteDao
    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteUserDatabase::class.java, "favorite_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteUserDatabase
        }
    }
}