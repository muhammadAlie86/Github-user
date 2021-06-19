package com.example.githubuser.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.api.Constant.Companion.PART
import com.example.githubuser.databinding.FragmentFollowerBinding
import com.example.githubuser.model.User
import com.example.githubuser.utils.DataCallback
import com.example.githubuser.view.activity.DetailActivity
import com.example.githubuser.view.activity.DetailActivity.Companion.EXTRA_DATA
import com.example.githubuser.viewmodel.FollowerViewModel
import com.example.githubuser.vo.Resource

class FollowerFragment : Fragment(),DataCallback{

    private lateinit var  binding : FragmentFollowerBinding
    private lateinit var listUserAdapter : ListUserAdapter
    private val viewModel : FollowerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                            Toast.makeText(context,"Something Wrong", Toast.LENGTH_SHORT).show()
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