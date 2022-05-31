package com.deanu.githubuser.searchuser.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.deanu.githubuser.R
import com.deanu.githubuser.databinding.FragmentSearchUserBinding
import com.deanu.githubuser.searchuser.usecase.AdapterUser
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserFragment : Fragment() {
    private var _binding: FragmentSearchUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchUserBinding.inflate(layoutInflater)
        val view = binding.root
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.search_title)

        setupSearchView()

        viewModel.storeItemList.observe(viewLifecycleOwner) { userList ->
            val adapter =
                AdapterUser(userList) { username: String -> viewModel.onCardClicked(username) }
            binding.rvSearchList.adapter = adapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressCircular.visibility = View.VISIBLE
            } else {
                binding.progressCircular.visibility = View.GONE
            }
        }

        viewModel.navigateToUserDetail.observe(viewLifecycleOwner) { userId ->
            userId?.let {
                val action =
                    SearchUserFragmentDirections.actionSearchUserFragmentToUserDetailFragment(it)
                view.findNavController().navigate(action)
                viewModel.onCardNavigated()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Snackbar.make(
                view,
                errorMessage,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return view
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchUser(it)
                } ?: return false
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Nothing
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}