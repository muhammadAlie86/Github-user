package com.example.consumerapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.adapter.ListUserAdapter
import com.example.consumerapp.databinding.ActivityFavoriteBinding
import com.example.consumerapp.model.entity.User
import com.example.consumerapp.utils.DataCallback
import com.example.consumerapp.viewmodel.FavoriteViewModel


class FavoriteActivity : AppCompatActivity() , DataCallback {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var listUserAdapter : ListUserAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listUserAdapter = ListUserAdapter(this)

        binding.imageBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setQueryAll()
        setRecyclerView()
    }

    private fun setQueryAll(){
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getQueryAll(this)
        viewModel.queryAll.observe(this, { favorite ->
            if (!favorite.isNullOrEmpty()) {
                hideProgress()
                listUserAdapter.setUser(favorite)
                binding.rvFavoriteUser.visibility = View.VISIBLE
            }
            else{
                showEmptyProgress()
            }
        })
    }
    private fun showEmptyProgress(){
        binding.progressBar.visibility = View.GONE
        binding.tvNoData.visibility = View.VISIBLE
        binding.imgEmpty.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        binding.progressBar.visibility = View.GONE
        binding.tvNoData.visibility = View.GONE
        binding.imgEmpty.visibility = View.GONE

    }

    private fun setRecyclerView(){

        with(binding.rvFavoriteUser) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = listUserAdapter
        }
    }
    override fun setData(user: User) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA,user)
        startActivity(intent)

    }
}