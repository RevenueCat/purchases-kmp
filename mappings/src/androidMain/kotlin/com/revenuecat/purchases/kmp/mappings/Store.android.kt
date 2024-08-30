package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.Store
import com.revenuecat.purchases.Store as AndroidStore

public fun AndroidStore.toStore(): Store =
    when (this) {
        AndroidStore.APP_STORE -> Store.APP_STORE
        AndroidStore.MAC_APP_STORE -> Store.MAC_APP_STORE
        AndroidStore.PLAY_STORE -> Store.PLAY_STORE
        AndroidStore.STRIPE -> Store.STRIPE
        AndroidStore.PROMOTIONAL -> Store.PROMOTIONAL
        AndroidStore.UNKNOWN_STORE -> Store.UNKNOWN_STORE
        AndroidStore.AMAZON -> Store.AMAZON
        AndroidStore.RC_BILLING -> Store.RC_BILLING
        AndroidStore.EXTERNAL -> Store.EXTERNAL
    }

public fun Store.toAndroidStore(): AndroidStore =
    when (this) {
        Store.APP_STORE -> AndroidStore.APP_STORE
        Store.MAC_APP_STORE -> AndroidStore.MAC_APP_STORE
        Store.PLAY_STORE -> AndroidStore.PLAY_STORE
        Store.STRIPE -> AndroidStore.STRIPE
        Store.PROMOTIONAL -> AndroidStore.PROMOTIONAL
        Store.UNKNOWN_STORE -> AndroidStore.UNKNOWN_STORE
        Store.AMAZON -> AndroidStore.AMAZON
        Store.RC_BILLING -> AndroidStore.RC_BILLING
        Store.EXTERNAL -> AndroidStore.EXTERNAL
    }
