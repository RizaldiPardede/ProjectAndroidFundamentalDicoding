package com.example.projectandroidfundamental

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.projectandroidfundamental.Api.ApiConfig
import com.example.projectandroidfundamental.database.FavoriteUser
import com.example.projectandroidfundamental.databinding.ActivityDetailBinding
import com.example.projectandroidfundamental.databinding.ActivityMainBinding
import com.example.projectandroidfundamental.model.DetailViewModel
import com.example.projectandroidfundamental.model.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {
    private lateinit var bindingD: ActivityDetailBinding
    private lateinit var viewModel: MainViewModel
    private var isfavorite =false
    private var favoriteUser: FavoriteUser? =null

//    private lateinit var MDetailViewModel: DetailViewModel
    val MDetailViewModel: DetailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)


    }
    private lateinit var avatar_url:String

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
        var ParsingData = String()
        const val USERNAME = "extra_username"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingD = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bindingD.root)
        favoriteUser = FavoriteUser()

        val username = intent.getStringExtra(USERNAME) //variable username diambil dari data activity main yang digunakan sebagai getter data

        if (username != null) {
            setUpView(username)


            MDetailViewModel.getFavorite(username).observe(this) { favuser ->

                if (favuser != null) {

                    favoriteUser = favuser
                    bindingD.buttonfavorite.changeiconcolor(R.color.red)
                    isfavorite = true
                } else {

                    bindingD.buttonfavorite.changeiconcolor(R.color.white)
                    isfavorite = false
                }
            }
        }
        ParsingData =username.toString()
        tablayout()


        bindingD.buttonfavorite.setOnClickListener {

            if (isfavorite) {

                MDetailViewModel.delete(favoriteUser!!)
            } else {
                favoriteUser?.let {
                    it.username = username
                    it.avatar_url = avatar_url
                }
                MDetailViewModel.insert(favoriteUser!!)
            }

        }






    }




    //function tablayout digunakan untuk menampilkan tablayout followers dan following
    private fun tablayout(){
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    //function ini berfungsi untuk setup setiap view
    private fun setUpView(username:String){

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getterDetail(username)

        viewModel.getDetail.observe(this, Observer{
            bindingD.usernamedetail.text=username
            bindingD.namedetail.text = it.name
            avatar_url = it.avatarUrl.toString()
            Glide.with(this@DetailActivity)
                .load(it.avatarUrl)
                .error(R.drawable.ic_launcher_background)
                .into(bindingD.avatarDetail)
            bindingD.followers.text = "${it.followers} Followers"
            bindingD.following.text = "${it.following} Following"


        })
        viewModel.getIsLoading.observe(this, this::showLoading)





    }

    //untuk menampilkan dan menghilangkan progress bar
    private fun showLoading(loading: Boolean) {
        if (loading) {
            bindingD.progressBarDetail.visibility = View.VISIBLE
        } else {
            bindingD.progressBarDetail.visibility = View.GONE
        }

    }


}
fun FloatingActionButton.changeiconcolor(@ColorRes color:Int){
    imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this.context,color))
}