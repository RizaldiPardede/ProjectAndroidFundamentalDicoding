package com.example.projectandroidfundamental

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandroidfundamental.databinding.ActivityMainBinding
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var rv_listuser:RecyclerView
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rv_listuser = findViewById<RecyclerView>(R.id.rv_listuser)

        val layoutManager = LinearLayoutManager(this)
        binding.rvListuser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListuser.addItemDecoration(itemDecoration)

        getUser()
        checktheme()

    }

    //function ini digunakan untuk mengambil data action bar search
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                searching(query)
                searchView.clearFocus()
                return true
            }


            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    //selected item di action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favoritelist -> {
                val intent = Intent(this@MainActivity, ListFavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.setting -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return true
        }
    }

    //function ini digunakan sebagai default get data pada reyclerview
    private fun getUser() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getterSearch("arif")

        viewModel.getSearch.observe(this, Observer{
            val datauser = it?.items
            val adapter = adapterUser(datauser)
            binding.rvListuser.adapter = adapter
            OnClickAdapter(adapter)

        })
        viewModel.getIsLoading.observe(this, this::showLoading)




    }

    //function ini digunakan untuk memasukkan data pencarian ke adapter
    private fun searching(searchhint:String){
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getterSearch(searchhint)

        viewModel.getSearch.observe(this, Observer{
            val datauser = it?.items
            val adapter = adapterUser(datauser)
            binding.rvListuser.adapter = adapter
            OnClickAdapter(adapter)

        })
        viewModel.getIsLoading.observe(this, this::showLoading)


    }

    //OnClickAdapter di panggil pada saat terdapat click terhadap list
    private fun OnClickAdapter(adapter:adapterUser){
        val adapterU = adapter
        adapter.setOnItemClickCallback(object : adapterUser.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem?) {
                if (data != null) {
                    val intent = Intent(this@MainActivity,DetailActivity::class.java)
                    intent.putExtra(DetailActivity.USERNAME,data.login)
                    startActivity(intent)
                }
            }
        })
    }

    //untuk menampilkan dan menghilangkan progress bar
    private fun showLoading(Loading: Boolean) {
        if (Loading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    //fungsi ini digunakan untuk mengecek tema aplikasi sesuai dengan setting
    fun checktheme(){
        val pref = SettingPreferences.getInstance(dataStore)
        val MSettingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        MSettingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }
    }
}

