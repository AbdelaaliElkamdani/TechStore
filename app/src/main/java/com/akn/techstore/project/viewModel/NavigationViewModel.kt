package com.akn.techstore.project.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
         private set
    var showBottomBar by mutableStateOf(false)
        private set
    var isStarted by mutableStateOf(false)
        private set

    fun enableBottomBar() { showBottomBar = true }
    fun disableBottomBar() { showBottomBar = false }

    fun loggedIn() {
        isLoggedIn = true
    }
    fun loggedOut() { isLoggedIn = false }
    fun startApp() {
        isStarted = true
    }
}
