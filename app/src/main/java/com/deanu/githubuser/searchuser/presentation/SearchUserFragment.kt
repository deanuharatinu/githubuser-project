package com.deanu.githubuser.searchuser.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.deanu.githubuser.R
import com.deanu.githubuser.databinding.FragmentSearchUserBinding
import com.deanu.githubuser.detailuser.presentation.UserDetailFragment.Companion.SEARCH
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
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        setupSearchView()

        viewModel.userList.observe(viewLifecycleOwner) { userList ->
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
                    SearchUserFragmentDirections.actionSearchUserFragmentToUserDetailFragment(
                        it,
                        SEARCH
                    )
                view.findNavController().navigate(action)
                viewModel.onCardNavigated()
                viewModel.clearUserList()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage.getContentIfNotHandled()?.let { message ->
                Snackbar.make(
                    view,
                    message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
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

            override fun onQueryTextChange(query: String?): Boolean {
                if (query?.isEmpty() == true) {
                    binding.searchView.clearFocus()
                    viewModel.clearUserList()
                }
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_user -> {
                val action =
                    SearchUserFragmentDirections.actionSearchUserFragmentToFavoriteUserFragment()
                view?.findNavController()?.navigate(action)
                true
            }
            R.id.app_setting -> {
                viewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
                    if (!isDarkMode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    viewModel.setAppTheme(!isDarkMode)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}