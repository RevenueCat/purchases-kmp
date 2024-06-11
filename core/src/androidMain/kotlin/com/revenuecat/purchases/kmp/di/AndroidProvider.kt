package com.revenuecat.purchases.kmp.di

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.revenuecat.purchases.kmp.di.AndroidProvider.application

/**
 * Provides references to the Android framework objects: the [Application] and current [Activity].
 * [application] should be set once, in a ContentProvider or Initializer.
 *
 * @see PurchasesInitializer
 */
@SuppressLint("StaticFieldLeak") // Not leaking, `currentActivity` is cleared appropriately.
internal object AndroidProvider : Application.ActivityLifecycleCallbacks {
    var application: Application? = null
        set(value) {
            require(value != null) { "`application` should not be set to a null value." }
            require(field == null) { "`application` is already set." }
            value.registerActivityLifecycleCallbacks(this)
            field = value
        }

    var activity: Activity? = null
        private set

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        AndroidProvider.activity = activity
    }

    override fun onActivityStarted(activity: Activity) {
        AndroidProvider.activity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        AndroidProvider.activity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity == AndroidProvider.activity) AndroidProvider.activity = null
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity == AndroidProvider.activity) AndroidProvider.activity = null
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // Noop
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity == AndroidProvider.activity) AndroidProvider.activity = null
    }
}

internal fun AndroidProvider.requireApplication(): Application =
    application ?: error(
        "Purchases has no reference to the Application. This is a bug in the library."
    )

internal fun AndroidProvider.requireActivity(): Activity =
    activity ?: error("There's no current Activity.")
