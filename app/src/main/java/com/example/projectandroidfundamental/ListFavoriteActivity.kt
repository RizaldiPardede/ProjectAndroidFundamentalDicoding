package com.example.projectandroidfundamental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandroidfundamental.databinding.ActivityListFavoriteBinding
import com.example.projectandroidfundamental.databinding.ActivityMainBinding
import com.example.projectandroidfundamental.model.DetailViewModel
import com.example.projectandroidfundamental.model.ViewModelFactory

class ListFavoriteActivity : AppCompatActivity() {
    private lateinit var rv_listuserfavorite: RecyclerView

    private lateinit var binding: ActivityListFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rv_listuserfavorite = findViewById<RecyclerView>(R.id.rv_listuserfavorite)

        val layoutManager = LinearLayoutManager(this)
        binding.rvListuserfavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListuserfavorite.addItemDecoration(itemDecoration)
        val MDetailViewModel: DetailViewModel by viewModels<DetailViewModel>() {
            ViewModelFactory.getInstance(application)
        }

        MDetailViewModel.getAllFavorite().observe(this){
            if (it!=null){
                val datauserfavorite = arrayListOf<ItemsItem>()
                binding.statusfavoritekosong.visibility=View.GONE
                it.map {
                    datauserfavorite.add(ItemsItem(login = it.username, avatarUrl = it.avatar_url))
                }
                val adapter = adapterUser(datauserfavorite)
                binding.rvListuserfavorite.adapter = adapter
                OnClickAdapter(adapter)
            }
            else{
                binding.statusfavoritekosong.visibility=View.VISIBLE
            }

        }
    }

    //fungsi ini untuk di panggil untuk memeriksa onclik pada adapter
    private fun OnClickAdapter(adapter: adapterUser) {
        val adapterU = adapter
        adapter.setOnItemClickCallback(object : adapterUser.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem?) {
                if (data != null) {
                    val intent = Intent(this@ListFavoriteActivity,DetailActivity::class.java)
                    intent.putExtra(DetailActivity.USERNAME,data.login)
                    startActivity(intent)
                }
            }
        })
    }
}