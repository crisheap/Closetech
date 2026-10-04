package com.altadail.closetech

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userDao: UserDao
) : ViewModel() {

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _allUsers.value = userDao.getAllUsers()
        }
    }

    fun addUser(name: String, age: Int) {
        viewModelScope.launch {
            val newUser = User(
                name = name,
                age = age
            )

            userDao.insertUser(newUser)
            loadUsers()
        }
    }
}