package com.example.projectandroidfundamental

import android.app.Application
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectandroidfundamental.database.FavoriteRepository
import com.example.projectandroidfundamental.database.FavoriteUser
import com.example.projectandroidfundamental.model.DetailViewModel
import com.example.projectandroidfundamental.model.ViewModelFactory
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import kotlin.properties.ReadOnlyProperty

class DetailViewModelTest(application: Application): ViewModel()  {
    private val MDetailViewModel: DetailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    private fun <T> viewModels(factoryProducer: () -> ViewModelFactory): ReadOnlyProperty<DetailViewModelTest, T> {
        TODO("Not yet implemented")
    }


    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    private var dummygetallfavorite = mFavoriteRepository.getAllFavorite().value?.get(0)?.username
    @Before
    fun before() {

    }

    @Test
    fun getAllFavorite() {

        assertEquals(dummygetallfavorite,
            MDetailViewModel.getAllFavorite().value?.get(0)?.username  , mFavoriteRepository.getAllFavorite().value?.get(0)?.username)
        }
    }
