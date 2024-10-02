package com.vadmax.timetosleep.domain.di

import com.vadmax.timetosleep.domain.usecases.local.GetConnectQRData
import com.vadmax.timetosleep.domain.usecases.local.GetConnectQRDataImpl
import com.vadmax.timetosleep.domain.usecases.local.GetLocalIPAddress
import com.vadmax.timetosleep.domain.usecases.local.GetLocalIPAddressImpl
import com.vadmax.timetosleep.domain.usecases.remote.GetPhoneConnected
import com.vadmax.timetosleep.domain.usecases.remote.GetPhoneConnectedImpl
import com.vadmax.timetosleep.domain.usecases.remote.GetSelectedTime
import com.vadmax.timetosleep.domain.usecases.remote.GetSelectedTimeImpl
import com.vadmax.timetosleep.domain.usecases.remote.GetTimerEnabled
import com.vadmax.timetosleep.domain.usecases.remote.GetTimerEnabledImpl
import com.vadmax.timetosleep.domain.usecases.remote.SetPhoneConnected
import com.vadmax.timetosleep.domain.usecases.remote.SetPhoneConnectedImpl
import com.vadmax.timetosleep.domain.usecases.remote.SetSelectedTime
import com.vadmax.timetosleep.domain.usecases.remote.SetSelectedTimeImpl
import com.vadmax.timetosleep.domain.usecases.remote.SetTimerEnable
import com.vadmax.timetosleep.domain.usecases.remote.SetTimerEnableImpl
import org.koin.dsl.module

internal val useCasesModule = module {
    factory<GetTimerEnabled> { GetTimerEnabledImpl(get()) }
    factory<SetTimerEnable> { SetTimerEnableImpl(get()) }
    factory<GetSelectedTime> { GetSelectedTimeImpl(get()) }
    factory<SetSelectedTime> { SetSelectedTimeImpl(get()) }
    factory<GetLocalIPAddress> { GetLocalIPAddressImpl() }
    factory<GetConnectQRData> { GetConnectQRDataImpl(get()) }
    factory<GetPhoneConnected> { GetPhoneConnectedImpl(get()) }
    factory<SetPhoneConnected> { SetPhoneConnectedImpl(get()) }
}