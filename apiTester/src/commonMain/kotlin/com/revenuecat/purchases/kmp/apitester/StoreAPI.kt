package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.Store

@Suppress("unused")
private class StoreAPI {
    fun check(store: Store) {
        when (store) {
            Store.APP_STORE,
            Store.MAC_APP_STORE,
            Store.PLAY_STORE,
            Store.STRIPE,
            Store.PROMOTIONAL,
            Store.UNKNOWN_STORE,
            Store.AMAZON,
            Store.RC_BILLING,
            Store.EXTERNAL,
            -> {
            }
        }.exhaustive
    }
}
