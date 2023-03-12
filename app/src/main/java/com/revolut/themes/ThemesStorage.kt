package com.revolut.themes

import android.content.Context
import androidx.core.content.edit

class ThemesStorage(context: Context) {

    private val prefs by lazy {
        context.getSharedPreferences("themes_prefs", Context.MODE_PRIVATE)
    }

    fun isDarkModeApplied(): Boolean? {
        if (!prefs.contains(KEY_DARK_MODE_APPLIED)) return null
        return prefs.getBoolean(KEY_DARK_MODE_APPLIED, false)
    }

    fun setDarkModeApplied(value: Boolean) {
        prefs.edit { putBoolean(KEY_DARK_MODE_APPLIED, value) }
    }

    fun setColorTheme(value: ColorTheme) {
        prefs.edit { putString(KEY_COLOR_THEME, value.storageKey()) }
    }

    fun getColorTheme(): ColorTheme? =
        prefs.getString(KEY_COLOR_THEME, null)?.toColorTheme()

    private fun ColorTheme.storageKey() = when (this) {
        ColorTheme.DEFAULT -> VALUE_COLOR_THEME_DEFAULT
        ColorTheme.DYNAMIC -> VALUE_COLOR_THEME_DYNAMIC
        ColorTheme.ORANGE -> VALUE_COLOR_THEME_ORANGE
    }

    private fun String.toColorTheme() =
        when (this) {
            VALUE_COLOR_THEME_DEFAULT -> ColorTheme.DEFAULT
            VALUE_COLOR_THEME_DYNAMIC -> ColorTheme.DYNAMIC
            VALUE_COLOR_THEME_ORANGE -> ColorTheme.ORANGE
            else -> throw IllegalArgumentException("Unknown key: $this")
        }

    private companion object {
        const val KEY_DARK_MODE_APPLIED = "KEY_DARK_MODE_APPLIED"
        const val KEY_COLOR_THEME = "KEY_COLOR_THEME"

        const val VALUE_COLOR_THEME_DEFAULT = "VALUE_COLOR_THEME_DEFAULT"
        const val VALUE_COLOR_THEME_DYNAMIC = "VALUE_COLOR_THEME_DYNAMIC"
        const val VALUE_COLOR_THEME_ORANGE = "VALUE_COLOR_THEME_ORANGE"
    }

}