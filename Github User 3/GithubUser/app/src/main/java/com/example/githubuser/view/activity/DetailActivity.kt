package com.example.githubuser.view.activity

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.adapter.PagerAdapter
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.GITHUB
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.ID
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.IMG_FAVORITE
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.USERNAME
import com.example.githubuser.database.UserHelper
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.databinding.ItemListDetailBinding
import com.example.githubuser.model.entity.User
import com.example.githubuser.viewmodel.DetailViewModel
import com.example.githubuser.viewmodel.ViewModelFactory
import com.example.githubuser.vo.Resource
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ItemListDetailBinding
    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel : DetailViewModel
    private lateinit var userHelper: UserHelper

    companion object{
        const val EXTRA_DATA = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        detailBinding = activityDetailBinding.listDetailUser
        viewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        userHelper = UserHelper.getInstance(this)
        userHelper.open()


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
                                .apply(
                                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                                        .error(R.drawable.ic_image_broken)
                                )
                                .into(detailBinding.detailImage)

                            detailBinding.detailName.text = user.data.username

                            if (user.data.repo.toString().isEmpty()) {
                                detailBinding.detailGithub.visibility = View.GONE
                            } else {
                                detailBinding.detailRepository.text = user.data.repo.toString()
                                detailBinding.detailRepository.visibility = View.VISIBLE
                            }
                            if (user.data.followers.toString().isEmpty()) {
                                detailBinding.detailFollower.visibility = View.GONE
                            } else {
                                detailBinding.detailFollower.text = user.data.followers.toString()
                                detailBinding.detailFollower.visibility = View.VISIBLE
                            }
                            if (user.data.following.toString().isEmpty()) {
                                detailBinding.detailFollowing.visibility = View.GONE
                            } else {
                                detailBinding.detailFollowing.text = user.data.following.toString()
                                detailBinding.detailFollowing.visibility = View.VISIBLE
                            }
                            if (user.data.company.isNullOrEmpty()) {
                                detailBinding.detailCompany.visibility = View.GONE
                            } else {
                                detailBinding.detailCompany.text = user.data.company
                                detailBinding.detailCompany.visibility = View.VISIBLE
                            }
                            if (user.data.location.isNullOrEmpty()) {
                                detailBinding.detailLocation.visibility = View.GONE
                            } else {
                                detailBinding.detailLocation.text = user.data.location
                                detailBinding.detailLocation.visibility = View.VISIBLE
                            }
                            if (user.data.github.isEmpty()) {
                                detailBinding.detailGithub.visibility = View.GONE
                            } else {
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
                        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                    }
                }
            })
            if (userHelper.checked(detailUserData.id.toString())){
                hideProgress()
            }
            detailBinding.imgFavorite.setOnClickListener {
                contentResolver.delete(Uri.parse(CONTENT_URI.toString() + getString(R.string.slash) + detailUserData.id),null,null)
                showProgress()

                Toast.makeText(this, getString(R.string.delete_message), Toast.LENGTH_SHORT).show()
            }
            detailBinding.imgUnFavorite.setOnClickListener {
                val cv = ContentValues()

                cv.put(ID,detailUserData.id)
                cv.put(USERNAME,detailUserData.name)
                cv.put(IMG_FAVORITE,detailUserData.avatarUrl)
                cv.put(GITHUB,detailUserData.avatarUrl)

                contentResolver.insert(CONTENT_URI,cv)
                hideProgress()

                Toast.makeText(this, getString(R.string.success_message), Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun showProgress(){
        detailBinding.imgUnFavorite.visibility = View.VISIBLE
        detailBinding.imgFavorite.visibility = View.GONE
    }
    private fun hideProgress(){

        detailBinding.imgUnFavorite.visibility = View.GONE
        detailBinding.imgFavorite.visibility = View.VISIBLE
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