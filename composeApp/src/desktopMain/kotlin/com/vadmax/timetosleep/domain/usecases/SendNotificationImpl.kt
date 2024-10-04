package com.vadmax.timetosleep.domain.usecases

import java.awt.AWTException
import java.awt.SystemTray
import java.awt.TrayIcon

class SendNotificationImpl : SendNotification {
    override fun invoke(message: String) {
        if (!SystemTray.isSupported()) {
            println("System tray not supported!")
            return
        }

        val tray = SystemTray.getSystemTray()
        val trayIcon = tray.trayIcons.first()
        try {
            trayIcon.displayMessage("Title", message, TrayIcon.MessageType.INFO)
        } catch (e: AWTException) {
            println("TrayIcon could not be added.")
        }
    }
}