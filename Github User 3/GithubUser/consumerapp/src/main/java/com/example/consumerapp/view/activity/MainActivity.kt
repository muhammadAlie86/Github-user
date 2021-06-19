package com.example.consumerapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.R
import com.example.consumerapp.adapter.ListUserAdapter
import com.example.consumerapp.databinding.ActivityMainBinding
import com.example.consumerapp.model.entity.User
import com.example.consumerapp.utils.DataCallback
import com.example.consumerapp.viewmodel.MainViewModel
import com.example.consumerapp.viewmodel.ViewModelFactory
import com.example.consumerapp.vo.Resource

class MainActivity : AppCompatActivity() , DataCallback {

    private lateinit var binding : ActivityMainBinding
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listUserAdapter = ListUserAdapter(this)
        viewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.imageSetting.setOnClickListener {
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }
        binding.imageFavorite.setOnClickListener {
            val intent = Intent(this,FavoriteActivity::class.java)
            startActivity(intent)
        }
        setSearchUser()
        setSearchObserver()
        setRecyclerView()
    }
    private fun setRecyclerView(){

        with(binding.rvUser) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = listUserAdapter
        }
    }
    private fun setSearchUser(){

        binding.searchView.isIconifiedByDefault = false
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.getSearchUser(query.toString())

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
        binding.rvUser.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.INVISIBLE
        binding.imageEmpty.visibility = View.INVISIBLE

    }
    private fun setSearchObserver(){
        viewModel.listUser.observe(this@MainActivity, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let { listUserAdapter.setUser(it) }
                }
                is Resource.Error -> {
                    Toast.makeText(this@MainActivity,getString(R.string.error_message),Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun setData(user: User) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA,user)
        startActivity(intent)
    }
}