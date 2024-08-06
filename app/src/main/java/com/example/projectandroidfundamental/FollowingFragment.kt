package com.example.projectandroidfundamental

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandroidfundamental.databinding.FragmentFollowBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {
    private lateinit var rv_following: RecyclerView
    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getterfollowing(DetailActivity.ParsingData)

        viewModel.getFollowing.observe(viewLifecycleOwner, Observer{
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun showLoading(Loading: Boolean) {
        if (Loading) {
            binding.progressBarFollowers.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowers.visibility = View.GONE
        }
    }

    companion object {

    }
}