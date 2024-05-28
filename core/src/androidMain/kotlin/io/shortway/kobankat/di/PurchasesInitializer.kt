package io.shortway.kobankat.di

import android.app.Application
import android.content.Context
import androidx.startup.Initializer

/**
 * This class is only used to capture the [Application] instance and save a reference to it. The
 * `Application` instance is used for 2 things:
 * 1. To call static Purchases methods that need a Context, such as
 * [canMakePayments][com.revenuecat.purchases.Purchases.canMakePayments].
 * 2. To keep track of the current Activity, which is needed to make purchases.
 */
internal class PurchasesInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        ActivityProvider.application = context.applicationContext as Application
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
