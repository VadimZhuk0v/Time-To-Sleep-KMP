package com.vadmax.timetosleep.ui.main.ui

import com.vadmax.timetosleep.data.TimeUIModel

sealed interface MainScreenState {

    data object Idle : MainScreenState

    data class Time(val initialTime: TimeUIModel) : MainScreenState

}