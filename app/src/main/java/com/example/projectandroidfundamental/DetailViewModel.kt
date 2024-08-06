package com.example.projectandroidfundamental.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectandroidfundamental.database.FavoriteRepository
import com.example.projectandroidfundamental.database.FavoriteUser

class DetailViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite(): LiveData<List<FavoriteUser>> {
        return mFavoriteRepository.getAllFavorite()}

    fun getFavorite(username: String):LiveData<FavoriteUser> {
        return mFavoriteRepository.getFavorite(username)}

    fun insert(Ufavorite: FavoriteUser) {
        mFavoriteRepository.insert(Ufavorite)
    }

    fun delete(Ufavorite: FavoriteUser) {
        mFavoriteRepository.delete(Ufavorite)
    }

}