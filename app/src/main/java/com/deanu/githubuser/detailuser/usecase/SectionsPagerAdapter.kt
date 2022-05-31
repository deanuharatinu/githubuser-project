package com.deanu.githubuser.detailuser.usecase

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.deanu.githubuser.followers.presentation.FollowersFragment
import com.deanu.githubuser.following.presentation.FollowingFragment

class SectionsPagerAdapter(
    fragment: FragmentActivity,
    private val username: String
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val bundle = Bundle()
        bundle.putString(FollowersFragment.USERNAME_KEY, username)
        when (position) {
            0 -> fragment = FollowersFragment().newInstance(bundle)
            1 -> fragment = FollowingFragment().newInstance(bundle)
        }
        return fragment as Fragment
    }
}