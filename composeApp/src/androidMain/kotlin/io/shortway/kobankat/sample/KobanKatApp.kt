package io.shortway.kobankat.sample

import android.app.Application
import io.shortway.kobankat.PurchasesFactory

class KobanKatApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PurchasesFactory.setApplication(this)
    }

}