package com.sam.swiftsend.ui.fragments.auth_login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sam.swiftsend.data.repository.IAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginVM(
    application: Application,
    private val authRepository: IAuthRepository
): AndroidViewModel(application) {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun signInWithGoogle(){
        viewModelScope.launch {
            authRepository.signInWithGoogle()
        }
    }

    fun signInWithEmailAndPassword() {
        viewModelScope.launch {
            try {
                val authenticatedUser = authRepository
                    .signInWithEmailAndPassword(email.value, password.value)

                if (authenticatedUser == null) {
                    _errorMessage.update { "Sign in failed, please try later" }
                } else {
                    _errorMessage.update { "Logged in successfully" }
                }
            } catch (e: Exception){
                    _errorMessage.update { e.message ?: "An expected error occurred" }
                }

            }
        }
    }

//Links used(
// https://firebase.google.com/docs/auth/android/google-signin
// https://developers.google.com/identity/android-credential-manager
// https://developer.android.com/identity/sign-in/credential-manager-siwg)
  //Google cloud console
//https://console.cloud.google.com/apis/dashboard