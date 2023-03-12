package com.revolut.themes

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.google.android.material.color.DynamicColors

object DynamicColorsActivityCallbacks : Application.ActivityLifecycleCallbacks {

    var colorTheme: ColorTheme? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        when (colorTheme) {
            ColorTheme.DYNAMIC -> DynamicColors.applyToActivityIfAvailable(activity)
            ColorTheme.ORANGE -> {
                activity.theme.applyStyle(R.style.ColorThemeOverlay_Orange, true)
            }
            ColorTheme.DEFAULT -> {
                activity.theme.applyStyle(R.style.AppTheme, true)
            }
            null -> {}
        }
    }

    override fun onActivityStarted(activity: Activity) = Unit
    override fun onActivityResumed(activity: Activity) = Unit
    override fun onActivityPaused(activity: Activity) = Unit
    override fun onActivityStopped(activity: Activity) = Unit
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
    override fun onActivityDestroyed(activity: Activity) = Unit
}