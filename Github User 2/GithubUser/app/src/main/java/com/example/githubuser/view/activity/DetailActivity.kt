package com.example.githubuser.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.PagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.databinding.ItemListDetailBinding
import com.example.githubuser.model.User
import com.example.githubuser.viewmodel.DetailViewModel
import com.example.githubuser.vo.Resource
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ItemListDetailBinding
    private lateinit var activityDetailBinding: ActivityDetailBinding
    private val viewModel : DetailViewModel by viewModels()

    companion object{
        const val EXTRA_DATA = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        detailBinding = activityDetailBinding.listDetailUser

        detailBinding.imageBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setupViewPager()
        showDetailUser()
    }

  private fun showDetailUser(){
      val detailUserData = intent.getParcelableExtra<User>(EXTRA_DATA)
        if (detailUserData != null) {
            viewModel.getDetailUser(detailUserData.name)
            viewModel.detailUser.observe(this,{ user ->
                when (user) {
                    is Resource.Success -> {
                        if (user.data != null) {
                            Glide.with(this)
                                    .load(detailUserData.avatarUrl)
                                    .into(detailBinding.detailImage)

                            detailBinding.detailName.text = user.data.username

                            if (user.data.repo.toString().isEmpty()){
                                detailBinding.detailGithub.visibility = View.GONE
                            }else{
                                detailBinding.detailRepository.text = user.data.repo.toString()
                                detailBinding.detailRepository.visibility = View.VISIBLE
                            }
                            if (user.data.followers.toString().isEmpty()){
                                detailBinding.detailFollower.visibility = View.GONE
                            }else{
                                detailBinding.detailFollower.text = user.data.followers.toString()
                                detailBinding.detailFollower.visibility = View.VISIBLE
                            }
                            if (user.data.following.toString().isEmpty()){
                                detailBinding.detailFollowing.visibility = View.GONE
                            }else{
                                detailBinding.detailFollowing.text = user.data.following.toString()
                                detailBinding.detailFollowing.visibility = View.VISIBLE
                            }
                            if (user.data.company.isNullOrEmpty()){
                                detailBinding.detailCompany.visibility = View.GONE
                            }else{
                                detailBinding.detailCompany.text = user.data.company
                                detailBinding.detailCompany.visibility = View.VISIBLE
                            }
                            if (user.data.location.isNullOrEmpty()){
                                detailBinding.detailLocation.visibility = View.GONE
                            }else{
                                detailBinding.detailLocation.text = user.data.location
                                detailBinding.detailLocation.visibility = View.VISIBLE
                            }
                            if (user.data.github.isEmpty()){
                                detailBinding.detailGithub.visibility = View.GONE
                            }else{
                                detailBinding.detailGithub.text = user.data.github
                                detailBinding.detailGithub.visibility = View.VISIBLE
                            }

                            detailBinding.detailImage.visibility = View.VISIBLE
                            detailBinding.detailName.visibility = View.VISIBLE
                            detailBinding.detailRepository.visibility = View.VISIBLE
                            detailBinding.detailFollower.visibility = View.VISIBLE
                            detailBinding.detailFollowing.visibility = View.VISIBLE
                            detailBinding.detailCompany.visibility = View.VISIBLE
                            detailBinding.detailLocation.visibility = View.VISIBLE
                            detailBinding.detailGithub.visibility = View.VISIBLE
                            activityDetailBinding.progressBar.visibility = View.INVISIBLE

                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun setupViewPager(){
        detailBinding.viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(detailBinding.tabs,detailBinding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.follower)
                1 -> tab.text = resources.getString(R.string.following)
            }
        }.attach()
    }

}