package com.example.composesurveyapp.ui.landingScreen

import androidx.compose.runtime.Immutable

object UserRepo {

    private var _user:User = User.NoUserLoggedIn
    val user: User
        get() = _user
    fun signInAsGuest(){
        _user = User.GuestUser

    }
}

sealed class User {
    @Immutable
    data class LoggedInUser(val email: String) : User()
    object GuestUser : User()
    object NoUserLoggedIn : User()
}