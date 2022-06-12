package com.deanu.githubuser.favoriteuser.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deanu.githubuser.R
import com.deanu.githubuser.databinding.FragmentFavoriteUserBinding
import com.deanu.githubuser.favoriteuser.usecase.AdapterFavoriteUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteUserFragment : Fragment() {
    private var _binding: FragmentFavoriteUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteUserBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.favorite_user_title)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getFavoriteUsers()
        val adapter =
            AdapterFavoriteUser { userId: Int -> viewModel.onUserDelete(userId) }
        binding.rvFavoriteUser.adapter = adapter

        viewModel.userList.observe(viewLifecycleOwner) { favoriteUserList ->
            favoriteUserList?.let { adapter.submitList(favoriteUserList) }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressCircular.visibility = View.VISIBLE
            } else {
                binding.progressCircular.visibility = View.GONE
            }
        }

        viewModel.userIdToDelete.observe(viewLifecycleOwner) { userIdToDelete ->
            // TODO: delete user dari favorite list
//            viewModel.
        }

        return binding.root
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}