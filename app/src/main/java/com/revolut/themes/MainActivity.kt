package com.revolut.themes

import android.content.res.Configuration
import android.os.Bundle
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.google.android.material.color.DynamicColors


class MainActivity : AppCompatActivity() {

    private lateinit var themesStorage: ThemesStorage

    private lateinit var dayModeRadioButton: RadioButton
    private lateinit var nightModeRadioButton: RadioButton
    private lateinit var defaultColorThemeButton: RadioButton
    private lateinit var dynamicColorThemeButton: RadioButton
    private lateinit var orangeColorThemeButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dayModeRadioButton = findViewById(R.id.option_day)
        nightModeRadioButton = findViewById(R.id.option_night)
        defaultColorThemeButton = findViewById(R.id.option_default_theme)
        dynamicColorThemeButton = findViewById(R.id.option_dynamic_theme)
        orangeColorThemeButton = findViewById(R.id.option_orange_theme)

        themesStorage = ThemesStorage(this)

        setColorThemeToggles()
        observeColorThemeSelection()
        setThemeModeToggles()
        observeThemeModeSelection()
    }

    private fun setColorThemeToggles() {
        val currentTheme = requireNotNull(DynamicColorsActivityCallbacks.colorTheme)
        val defaultThemeSelected = currentTheme == ColorTheme.DEFAULT
        val dynamicThemeSelected = currentTheme == ColorTheme.DYNAMIC
        val orangeThemeSelected = currentTheme == ColorTheme.ORANGE

        defaultColorThemeButton.isChecked = defaultThemeSelected
        dynamicColorThemeButton.isChecked = dynamicThemeSelected
        orangeColorThemeButton.isChecked = orangeThemeSelected

        dynamicColorThemeButton.isVisible = DynamicColors.isDynamicColorAvailable()
    }

    private fun observeColorThemeSelection() {
        val onCheckedChanged = OnCheckedChangeListener { view, isChecked ->
            if (!isChecked) return@OnCheckedChangeListener
            when (view) {
                defaultColorThemeButton -> setColorTheme(ColorTheme.DEFAULT)
                dynamicColorThemeButton -> setColorTheme(ColorTheme.DYNAMIC)
                orangeColorThemeButton -> setColorTheme(ColorTheme.ORANGE)
            }
        }

        dynamicColorThemeButton.setOnCheckedChangeListener(onCheckedChanged)
        defaultColorThemeButton.setOnCheckedChangeListener(onCheckedChanged)
        orangeColorThemeButton.setOnCheckedChangeListener(onCheckedChanged)
    }


    private fun setColorTheme(colorTheme: ColorTheme) {
        if (DynamicColorsActivityCallbacks.colorTheme == colorTheme) return
        themesStorage.setColorTheme(colorTheme)
        DynamicColorsActivityCallbacks.colorTheme = colorTheme
        ActivitiesController.recreateActivities()
    }

    private fun setThemeModeToggles() {
        val darkModeApplied =
            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
                    Configuration.UI_MODE_NIGHT_YES
        if (darkModeApplied) {
            dayModeRadioButton.isChecked = false
            nightModeRadioButton.isChecked = true
        } else {
            dayModeRadioButton.isChecked = true
            nightModeRadioButton.isChecked = false
        }
    }

    private fun observeThemeModeSelection() {
        dayModeRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                themesStorage.setDarkModeApplied(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        nightModeRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                themesStorage.setDarkModeApplied(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}