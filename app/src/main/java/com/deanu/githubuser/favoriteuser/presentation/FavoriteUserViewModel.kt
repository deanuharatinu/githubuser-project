package com.deanu.githubuser.favoriteuser.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deanu.githubuser.common.domain.model.UserDetail
import com.deanu.githubuser.common.domain.repository.UserRepository
import com.deanu.githubuser.common.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _userList = MutableLiveData<List<UserDetail>>()
    val userList: LiveData<List<UserDetail>> = _userList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _userIdToDelete = MutableLiveData<Int>()
    val userIdToDelete: LiveData<Int> = _userIdToDelete

    private val _navigateToUserDetail = MutableLiveData<String?>()
    val navigateToUserDetail: LiveData<String?> = _navigateToUserDetail

    fun getFavoriteUsers() {
        _isLoading.value = true
        viewModelScope.launch {
            withContext(dispatchersProvider.io()) {
                _userList.postValue(userRepository.getFavoriteUserList())
            }
            _isLoading.value = false
        }
    }

    fun deleteFavoriteUser(userIdToDelete: Int) {
        viewModelScope.launch {
            withContext(dispatchersProvider.io()) {
                userRepository.deleteFavoriteUser(userIdToDelete)
            }
        }
    }

    fun onUserDelete(userId: Int) {
        _userIdToDelete.value = userId
    }

    fun onCardClicked(username: String) {
        _navigateToUserDetail.value = username
    }

    fun onCardNavigated() {
        _navigateToUserDetail.value = null
    }

    companion object {
        const val TAG = "FavoriteUserViewModel"
    }
}