package com.example.consumerapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.R
import com.example.consumerapp.adapter.ListUserAdapter
import com.example.consumerapp.databinding.FragmentFollowingBinding
import com.example.consumerapp.model.entity.User
import com.example.consumerapp.utils.Constant.Companion.PART
import com.example.consumerapp.utils.DataCallback
import com.example.consumerapp.view.activity.DetailActivity
import com.example.consumerapp.view.activity.DetailActivity.Companion.EXTRA_DATA
import com.example.consumerapp.viewmodel.FollowingViewModel
import com.example.consumerapp.viewmodel.ViewModelFactory
import com.example.consumerapp.vo.Resource


class FollowingFragment : Fragment() , DataCallback {

    private lateinit var binding : FragmentFollowingBinding
    private lateinit var listUserAdapter : ListUserAdapter
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: FollowingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowingBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelFactory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(FollowingViewModel::class.java)
        listUserAdapter = ListUserAdapter(this)
        showFollowing()
        setRecyclerView()
    }

    private fun setRecyclerView(){

        with(binding.rvFollowing) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = listUserAdapter
        }
    }
    private fun showFollowing(){

        val dataFollowing = activity?.intent?.getParcelableExtra<User>(EXTRA_DATA)
        if (dataFollowing != null) {
            viewModel.getFollowing(dataFollowing.name,PART)
            viewModel.listFollowing.observe(viewLifecycleOwner, { following ->
                when (following) {
                    is Resource.Success ->{
                        binding.progressBar.visibility = View.VISIBLE
                        following.data?.let { listUserAdapter.setUser(it) }
                        binding.progressBar.visibility = View.GONE
                    }
                    is Resource.Error ->
                        Toast.makeText(context,getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun setData(user: User) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(EXTRA_DATA,user)
        startActivity(intent)
    }
}