package com.revolut.themes

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.core.app.ActivityCompat
import java.util.*

object ActivitiesController {

    private val activityRegistry = ActivityRegistry()

    fun initialize(application: Application) {
        activityRegistry.initialize(application)
    }

    fun recreateActivities() {
        activityRegistry.activities.forEach(ActivityCompat::recreate)
    }
}

internal class ActivityRegistry {

    private val _activities = Collections.newSetFromMap(WeakHashMap<Activity, Boolean>())
    val activities: Set<Activity> get() = _activities
    var initialized = false

    fun initialize(application: Application) {
        if (!initialized) {
            initialized = true
            application.registerActivityLifecycleCallbacks(ActivityCallbacks())
        }
    }

    private inner class ActivityCallbacks : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            _activities.add(activity)
        }

        override fun onActivityDestroyed(activity: Activity) {
            _activities.remove(activity)
        }

        override fun onActivityResumed(activity: Activity) = Unit
        override fun onActivityPaused(activity: Activity) = Unit
        override fun onActivityStarted(activity: Activity) = Unit
        override fun onActivityStopped(activity: Activity) = Unit
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
    }
}