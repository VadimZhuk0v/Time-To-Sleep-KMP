package com.vadmax.timetosleep.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.server.rpc.runServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            runServer()
        }
    }
}