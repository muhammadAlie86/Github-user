package com.example.githubuser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.model.User
import com.example.githubuser.model.UserData
import com.example.githubuser.utils.DataCallback

class MainActivity : AppCompatActivity() , DataCallback {
    private lateinit var binding : ActivityMainBinding
    private lateinit var listUserAdapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listUserAdapter = ListUserAdapter(this)


       // binding.progressBar.visibility = View.VISIBLE

        init()
        setupRecyclerView()
    }
    private fun init(){
        val dataGithubUser = UserData.generateDataUser()
        //binding.progressBar.visibility = View.GONE
        listUserAdapter.setUser(dataGithubUser)
        listUserAdapter.notifyDataSetChanged()
    }
    private fun setupRecyclerView(){

        with(binding.rvUser) {
            layoutManager = LinearLayoutManager(context)
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