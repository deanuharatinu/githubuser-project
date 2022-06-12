package com.deanu.githubuser.searchuser.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.common.domain.repository.UserRepository
import com.deanu.githubuser.common.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _navigateToUserDetail = MutableLiveData<String?>()
    val navigateToUserDetail: LiveData<String?> = _navigateToUserDetail

    fun searchUser(username: String) {
        _isLoading.value = true

        viewModelScope.launch {
            withContext(dispatchersProvider.io()) {
                try {
                    val userList = userRepository.getUserList(username)
                    if (userList.isNotEmpty()) {
                        successSearch(userList)
                    } else {
                        errorSearch()
                    }
                } catch (exception: Exception) {
                    Log.e(TAG, "searchUser: ${exception.message}")
                    _errorMessage.postValue(exception.message)
                }
            }
            _isLoading.value = false
        }
    }

    fun onCardClicked(username: String) {
        _navigateToUserDetail.value = username
    }

    fun onCardNavigated() {
        _navigateToUserDetail.value = null
    }

    private fun successSearch(userList: List<User>) {
        _userList.postValue(userList)
    }

    private fun errorSearch() {
        _errorMessage.postValue("User not found")
    }

    companion object {
        const val TAG = "SearchUserViewModel"
    }
}