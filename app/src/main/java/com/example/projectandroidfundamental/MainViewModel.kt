package com.example.projectandroidfundamental

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectandroidfundamental.Api.ApiConfig
import com.example.projectandroidfundamental.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class MainViewModel: ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
       }

    private val followers = MutableLiveData<ArrayList<ResponseFollow>>()
    private val following = MutableLiveData<ArrayList<ResponseFollow>>()
    private val detail = MutableLiveData<ResponseDetail>()
    private  val search = MutableLiveData<com.example.projectandroidfundamental.Response?>()
    private val loading = MutableLiveData<Boolean>()


    val getFollowers: LiveData<ArrayList<ResponseFollow>> = followers
    val getFollowing: LiveData<ArrayList<ResponseFollow>> = following
    val getDetail:LiveData<ResponseDetail> = detail
    val getSearch: MutableLiveData<com.example.projectandroidfundamental.Response?> = search
    val getIsLoading: LiveData<Boolean> = loading


    //function ini digunakan sebagai default get data pada reyclerview fragment followers
    fun getterfollowers(username:String){
        try {
            loading.value = true
            val client = ApiConfig.getApiService().getFollowers(username)
            client.enqueue(object : Callback<ArrayList<ResponseFollow>>{
                override fun onResponse(
                    call: Call<ArrayList<ResponseFollow>>,
                    response: Response<ArrayList<ResponseFollow>>
                ) {
                    loading.value=false
                    if (response.isSuccessful){
                        if (response.body()!=null){
                            followers.value=response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<ResponseFollow>>, t: Throwable) {
                    loading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })

        }
        catch (e: Exception){


        }


    }

    //function ini digunakan sebagai default get data pada reyclerview fragment following
    fun getterfollowing(username: String) {
        try {
            loading.value = true
            val client = ApiConfig.getApiService().getFollowing(username)
            client.enqueue(object : Callback<ArrayList<ResponseFollow>>{
                override fun onResponse(
                    call: Call<ArrayList<ResponseFollow>>,
                    response: Response<ArrayList<ResponseFollow>>
                ) {
                    loading.value=false
                    if (response.isSuccessful){
                        if (response.body()!=null){
                            following.value=response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<ResponseFollow>>, t: Throwable) {
                    loading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })

        }
        catch (e: Exception){


        }
    }

    //function ini digunakan sebagai default get data pada Activity Detail
    fun getterDetail(username: String) {
        try {
            loading.value = true
            val client = ApiConfig.getApiService().getDetailUser(username)
            client.enqueue(object : Callback<ResponseDetail>{
                override fun onResponse(
                    call: Call<ResponseDetail>,
                    response: Response<ResponseDetail>
                ) {
                    loading.value=false
                    if (response.isSuccessful){
                        if (response.body()!=null){
                            detail.value=response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                    loading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })

        }
        catch (e: Exception){


        }
    }

    //function ini digunakan sebagai get data search
    fun getterSearch(username: String){
        loading.value=true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : retrofit2.Callback<com.example.projectandroidfundamental.Response>{
            override fun onResponse(call: Call<com.example.projectandroidfundamental.Response>, response: retrofit2.Response<com.example.projectandroidfundamental.Response>) {
                loading.value=false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
                        search.value=responseBody
                    }
                }
            }

            override fun onFailure(call: Call<com.example.projectandroidfundamental.Response>, t: Throwable) {
                loading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

}




