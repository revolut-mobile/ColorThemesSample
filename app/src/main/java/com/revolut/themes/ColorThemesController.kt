package com.revolut.themes

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.material.color.DynamicColors
import java.util.*

object ColorThemesController {

    private val activities = Collections.newSetFromMap(WeakHashMap<Activity, Boolean>())
    var initialized = false
    var colorTheme = ColorTheme.DEFAULT
        private set

    fun initialize(application: Application) {
        if (!initialized) {
            initialized = true
            application.registerActivityLifecycleCallbacks(ActivityCallbacks())
        }
    }

    fun applyColorTheme(colorTheme: ColorTheme) {
        this.colorTheme = colorTheme
        activities.forEach(ActivityCompat::recreate)
    }

    private class ActivityCallbacks : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            when (colorTheme) {
                ColorTheme.DYNAMIC -> DynamicColors.applyToActivityIfAvailable(activity)
                ColorTheme.ORANGE -> {
                    activity.theme.applyStyle(R.style.ColorThemeOverlay_Orange, true)
                }
                ColorTheme.DEFAULT -> {
                    //simply apply default style
                    activity.theme.applyStyle(R.style.AppTheme, true)
                }
            }
            activities.add(activity)
        }

        override fun onActivityDestroyed(activity: Activity) {
            activities.remove(activity)
        }

        override fun onActivityStarted(activity: Activity) = Unit
        override fun onActivityResumed(activity: Activity) = Unit
        override fun onActivityPaused(activity: Activity) = Unit
        override fun onActivityStopped(activity: Activity) = Unit
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
    }
}
