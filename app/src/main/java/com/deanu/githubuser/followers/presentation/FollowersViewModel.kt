package com.deanu.githubuser.followers.presentation

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
class FollowersViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _followersList = MutableLiveData<List<User>>()
    val followersList: LiveData<List<User>> = _followersList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getFollowersList(username: String) {
        viewModelScope.launch {
            withContext(dispatchersProvider.io()) {
                try {
                    val followersList = userRepository.getUserFollowers(username)
                    _followersList.postValue(followersList)
                } catch (exception: Exception) {
                    _errorMessage.postValue(exception.message)
                }
            }
        }
    }
}