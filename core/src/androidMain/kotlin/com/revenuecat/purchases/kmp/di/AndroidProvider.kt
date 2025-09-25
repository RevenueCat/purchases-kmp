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
        // Noop
        //
        // If we were to set AndroidProvider.activity to null here, calling purchase() twice in
        // rapid succession would crash because the second invocation of purchase() would find a
        // null activity.
        //
        // If we leave AndroidProvider.activity set to the paused activity, the second invocation of
        // purchase() will return an OperationAlreadyInProgressError as expected, and doesn't crash.
        //
        // In the purchase flow, onActivityCreated will be called right after onActivityPaused with
        // the ProxyBillingActivity, which will then be set as the current activity.
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
        "Purchases has no reference to the Application. Please make sure you have not removed " +
                "the androidx.startup.InitializationProvider from your AndroidManifest.xml. If you " +
                "need to remove specific initializers, such as " +
                "androidx.work.WorkManagerInitializer, do so as follows:" +
                "\n\n" +
                """
        <provider
            android:name="androidx.startup.InitializationProvider"
            android:exported="false"
            android:authorities="${'$'}{applicationId}.androidx-startup"
            tools:node="merge">
        
            <meta-data
                android:name="androidx.work.WorkManagerInitializer"
                android:value="androidx.startup"
                tools:node="remove" />
        
        </provider>
        """.trimIndent() +
                "\n\n" +
                "Stack trace:"
    )

internal fun AndroidProvider.requireActivity(): Activity =
    activity ?: error("There's no current Activity.")
