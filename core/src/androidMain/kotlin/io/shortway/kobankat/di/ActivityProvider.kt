package io.shortway.kobankat.di

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle

// Not leaking, `current` is cleared appropriately.
@SuppressLint("StaticFieldLeak")
internal object ActivityProvider: Application.ActivityLifecycleCallbacks {
    var current: Activity? = null
        private set

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        current = activity
    }

    override fun onActivityStarted(activity: Activity) {
        current = activity
    }

    override fun onActivityResumed(activity: Activity) {
        current = activity
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity == current) current = null
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity == current) current = null
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // Noop
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity == current) current = null
    }
}

internal fun ActivityProvider.currentOrThrow(): Activity =
    current ?: error("There's no current Activity.")