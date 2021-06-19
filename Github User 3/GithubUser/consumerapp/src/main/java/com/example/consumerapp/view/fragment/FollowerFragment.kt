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
import com.example.consumerapp.databinding.FragmentFollowerBinding
import com.example.consumerapp.model.entity.User
import com.example.consumerapp.utils.Constant.Companion.PART
import com.example.consumerapp.utils.DataCallback
import com.example.consumerapp.view.activity.DetailActivity
import com.example.consumerapp.view.activity.DetailActivity.Companion.EXTRA_DATA
import com.example.consumerapp.viewmodel.FollowerViewModel
import com.example.consumerapp.viewmodel.ViewModelFactory
import com.example.consumerapp.vo.Resource

class FollowerFragment : Fragment(), DataCallback {

    private lateinit var  binding : FragmentFollowerBinding
    private lateinit var listUserAdapter : ListUserAdapter
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel : FollowerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(FollowerViewModel::class.java)
        listUserAdapter = ListUserAdapter(this)

        setRecyclerView()
        showFollower()

    }

    private fun setRecyclerView(){

        with(binding.rvFollower) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = listUserAdapter
        }
    }

    private fun showFollower(){

       val dataFollower = activity?.intent?.getParcelableExtra<User>(EXTRA_DATA)
        if (dataFollower != null) {
            viewModel.getFollower(dataFollower.name,PART)
                viewModel.listFollower.observe(viewLifecycleOwner, { follower ->
                    when (follower) {
                        is Resource.Success ->{
                            binding.progressBar.visibility = View.VISIBLE
                            follower.data?.let { listUserAdapter.setUser(it) }
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