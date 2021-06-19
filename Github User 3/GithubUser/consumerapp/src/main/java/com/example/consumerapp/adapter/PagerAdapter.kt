package com.example.consumerapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.consumerapp.view.fragment.FollowerFragment
import com.example.consumerapp.view.fragment.FollowingFragment


class PagerAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when(position){
            0 -> FollowerFragment()
            1 -> FollowingFragment()
            else -> Fragment()
        }
}
