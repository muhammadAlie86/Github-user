package com.example.githubuser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.databinding.ItemListDetailBinding
import com.example.githubuser.model.User

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ItemListDetailBinding

    companion object{
        const val EXTRA_DATA = "extra_data"
        const val DETAIL_USER = "Detail User"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        detailBinding = activityDetailBinding.listDetailUser
        title = DETAIL_USER
        showDetailUser()
    }

   private fun showDetailUser(){
        val detailUserData = intent.getParcelableExtra<User>(EXTRA_DATA)

        if (detailUserData != null) {
            //detailBinding.progressBar.visibility = View.GONE

            detailBinding.detailName.text = detailUserData.name
            detailBinding.detailLocation.text = detailUserData.location
            detailBinding.detailCompany.text = detailUserData.company
            detailBinding.detailRepository.text = detailUserData.repository.toString()
            detailBinding.detailFollower.text = detailUserData.follower.toString()
            detailBinding.detailFollowing.text = detailUserData.following.toString()
            detailBinding.detailImage.setImageResource(detailUserData.image)
        }
    }
}