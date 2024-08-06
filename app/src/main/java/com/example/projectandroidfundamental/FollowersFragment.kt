package com.example.projectandroidfundamental

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandroidfundamental.databinding.ActivityMainBinding
import com.example.projectandroidfundamental.databinding.FragmentFollowBinding




private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FollowersFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var rv_followers: RecyclerView
    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getterfollowers(DetailActivity.ParsingData)

        viewModel.getFollowers.observe(viewLifecycleOwner, Observer{
            if (it.size != 0) {
                val adapter = adapterFollow(it)
                binding.rvFollowers.adapter = adapter
                binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
                binding.rvFollowers.setHasFixedSize(true)


            }

        })
        viewModel.getIsLoading.observe(viewLifecycleOwner, this::showLoading)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


    }
    private fun showLoading(Loading: Boolean) {
        if (Loading) {
            binding.progressBarFollowers.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowers.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root


    }



    companion object {


    }
}