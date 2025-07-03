package com.jmgg.habitus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmgg.habitus.data.repositories.UserRepository
import com.jmgg.habitus.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private val _currentUser = MutableStateFlow<User?>(null)
val currentUser: StateFlow<User?> = _currentUser

private val _authError = MutableStateFlow<String?>(null)
val authError: StateFlow<String?> = _authError

fun login(email: String, password: String) {
    viewModelScope.launch {
        val user = repository.loginUser(email, password)
        if (user != null) {
            _currentUser.value = user
            _authError.value = null
        } else {
            _authError.value = "Incorrect credentials"
        }
    }
}

fun register(user: User) {
    viewModelScope.launch {
        val success = repository.registerUser(user)
        if (success) {
            val newUser=repository.loginUser(user.email,user.password)
            _currentUser.value=newUser
            _authError.value = null
        } else {
            _authError.value = "Error signing up, please check that you have added the data correctly"
        }
    }
}


fun logout() {
    _currentUser.value = null
}

fun clearError() {
    _authError.value = null
}