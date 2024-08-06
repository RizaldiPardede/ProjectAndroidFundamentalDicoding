package com.example.projectandroidfundamental.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(UserFavorite: FavoriteUser)

    @Delete
    fun delete(UserFavorite: FavoriteUser)

    @Query("SELECT * from FavoriteUser ORDER BY username ASC")
    fun getAllFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT * from FavoriteUser WHERE :uname = username")
    fun getFavorite(uname:String): LiveData<FavoriteUser>
}