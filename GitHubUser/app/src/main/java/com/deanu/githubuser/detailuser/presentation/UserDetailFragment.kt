package com.deanu.githubuser.detailuser.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.deanu.githubuser.R
import com.deanu.githubuser.databinding.FragmentUserDetailBinding
import com.deanu.githubuser.detailuser.usecase.SectionsPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserDetailViewModel by viewModels()
    private val args: UserDetailFragmentArgs by navArgs()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.user_detail_title)

        viewModel.getUserDetail(args.username)

        viewModel.userDetail.observe(viewLifecycleOwner) { userDetail ->
            Glide.with(this)
                .load(userDetail.avatarUrl)
                .circleCrop()
                .into(binding.ivAvatar)
            binding.tvUsername.text = userDetail.username
            binding.tvFullName.text = getString(R.string.username, userDetail.name)
            binding.tvGitUrl.text = userDetail.githubUrl
            binding.tvFollowers.text = getString(R.string.followers_numbers, userDetail.followers)
            binding.tvFollowing.text = getString(R.string.following_numbers, userDetail.following)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingScreen(isLoading)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Snackbar.make(
                binding.root,
                errorMessage,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        showFollowersAndFollowing()

        return binding.root
    }

    private fun showFollowersAndFollowing() {
        val adapter = SectionsPagerAdapter(requireActivity(), args.username)
        val viewPager = binding.viewPager
        viewPager.adapter = adapter

        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun loadingScreen(isLoading: Boolean) {
        if (isLoading) {
            binding.contentLayout.visibility = View.INVISIBLE
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.contentLayout.visibility = View.VISIBLE
            binding.progressCircular.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}