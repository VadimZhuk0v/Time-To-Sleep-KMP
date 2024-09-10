package com.vadmax.timetosleep.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.repositories.TimerControlRepository
import com.vadmax.timetosleep.ui.main.ui.MainScreenState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val timerControlRepository: TimerControlRepository,
) : ViewModel() {

    val selectTime = timerControlRepository.scrollToTime
    val enabled = timerControlRepository.enabled

    private val _screenState = MutableStateFlow<MainScreenState>(MainScreenState.Idle)
    val screenState = _screenState.asStateFlow()

    init {
        setUpInitialTime()
    }

    fun setTime(time: TimeUIModel) {
        viewModelScope.launch {
            timerControlRepository.setTimeByUser(time)
        }
    }

    fun switchEnable() {
        viewModelScope.launch {
            timerControlRepository.switchEnable()
        }
    }

    private fun setUpInitialTime() {
        viewModelScope.launch {
            timerControlRepository.selectedTime.collectLatest {
                _screenState.value = MainScreenState.Time(it)
                cancel()
            }
        }
    }

}