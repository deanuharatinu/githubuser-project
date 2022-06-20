package com.deanu.githubuser.searchuser.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.common.domain.repository.AppSettingRepository
import com.deanu.githubuser.common.domain.repository.UserRepository
import com.deanu.githubuser.common.utils.DispatchersProvider
import com.deanu.githubuser.common.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val appSettingRepository: AppSettingRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

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
                    _errorMessage.postValue(Event(exception.message.toString()))
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
        _errorMessage.postValue(Event("User not found"))
    }

    val isDarkMode: LiveData<Boolean> = appSettingRepository.getAppMode()

    fun setAppTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            appSettingRepository.setAppMode(isDarkMode)
        }
    }

    companion object {
        const val TAG = "SearchUserViewModel"
    }
}