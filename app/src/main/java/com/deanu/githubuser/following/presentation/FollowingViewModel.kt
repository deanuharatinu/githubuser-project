package com.deanu.githubuser.following.presentation

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
class FollowingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _followingList = MutableLiveData<List<User>>()
    val followingList: LiveData<List<User>> = _followingList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getFollowingList(username: String) {
        viewModelScope.launch {
            withContext(dispatchersProvider.io()) {
                try {
                    val followingList = userRepository.getUserFollowing(username)
                    _followingList.postValue(followingList)
                } catch (exception: Exception) {
                    _errorMessage.postValue(exception.message)
                }
            }
        }
    }
}