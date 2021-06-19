package com.example.githubuser.view.activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.model.User
import com.example.githubuser.utils.DataCallback
import com.example.githubuser.viewmodel.MainViewModel
import com.example.githubuser.vo.Resource

class MainActivity : AppCompatActivity() , DataCallback {

    private lateinit var binding : ActivityMainBinding
    private lateinit var listUserAdapter: ListUserAdapter
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listUserAdapter = ListUserAdapter(this)

        binding.imageSetting.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
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
                    Toast.makeText(this@MainActivity,"Something Wrong",Toast.LENGTH_SHORT).show()
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