package com.roy.githubuser.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.roy.githubuser.ui.DetailUserActivity
import com.roy.githubuser.ui.FollowsFragment

class SectionPagerAdapter(activity: DetailUserActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    var username: String? = null

    override fun createFragment(position: Int): Fragment {
        return FollowsFragment.newInstance(position + 1, username)
    }
}