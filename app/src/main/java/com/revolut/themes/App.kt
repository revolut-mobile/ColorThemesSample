package com.revolut.themes

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    private lateinit var themesStorage: ThemesStorage

    override fun onCreate() {
        super.onCreate()
        themesStorage = ThemesStorage(this)

        setDayNightMode()
        setColorTheme()

        ActivitiesController.initialize(this)
        registerActivityLifecycleCallbacks(DynamicColorsActivityCallbacks)
    }

    private fun setDayNightMode() {
        val themeMode = when (themesStorage.isDarkModeApplied()) {
            null -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            true -> AppCompatDelegate.MODE_NIGHT_YES
            false -> AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    private fun setColorTheme() {
        DynamicColorsActivityCallbacks.colorTheme =
            themesStorage.getColorTheme() ?: ColorTheme.DEFAULT
    }

}