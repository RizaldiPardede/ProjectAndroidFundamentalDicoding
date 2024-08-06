package com.example.projectandroidfundamental.Api

import com.example.projectandroidfundamental.ItemsItem
import com.example.projectandroidfundamental.Response
import com.example.projectandroidfundamental.ResponseDetail
import com.example.projectandroidfundamental.ResponseFollow
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getUser(
        @Query("q") q: String
    ):Call<Response>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<ResponseDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ResponseFollow>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ResponseFollow>>



}