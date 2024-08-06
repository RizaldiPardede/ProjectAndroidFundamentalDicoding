package com.example.projectandroidfundamental.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteDao = db.FavoriteDao()
    }
    fun getAllFavorite(): LiveData<List<FavoriteUser>> {
        return mFavoriteDao.getAllFavorite()
    }

    fun insert(favoriteuser: FavoriteUser) {
        executorService.execute { mFavoriteDao.insert(favoriteuser) }
    }
    fun delete(favoriteuser: FavoriteUser) {
        executorService.execute { mFavoriteDao.delete(favoriteuser) }
    }
    fun getFavorite(username:String) :LiveData<FavoriteUser> {
        return mFavoriteDao.getFavorite(username)}

}