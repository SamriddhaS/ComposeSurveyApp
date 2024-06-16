package com.example.composesurveyapp.ui.landingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

public class LandingViewModel(val repository:UserRepo):ViewModel(){


    public fun signInAsGuest(
        signInAsGuestDone:()->Unit
    ){
        repository.signInAsGuest()
        signInAsGuestDone()
    }

}

class WelcomeViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LandingViewModel::class.java)) {
            return LandingViewModel(UserRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}