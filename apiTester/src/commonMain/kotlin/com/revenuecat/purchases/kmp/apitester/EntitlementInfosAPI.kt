package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.EntitlementInfo
import com.revenuecat.purchases.kmp.EntitlementInfos
import com.revenuecat.purchases.kmp.VerificationResult
import com.revenuecat.purchases.kmp.active
import com.revenuecat.purchases.kmp.all
import com.revenuecat.purchases.kmp.get
import com.revenuecat.purchases.kmp.verification

@Suppress("unused", "UNUSED_VARIABLE")
private class EntitlementInfosAPI {
    fun check(infos: EntitlementInfos) {
        val active: Map<String, EntitlementInfo> = infos.active
        val all: Map<String, EntitlementInfo> = infos.all
        val i: EntitlementInfo? = infos[""]
        // FIXME re-enable in SDK-3530
        //  val verification: VerificationResult = verification
    }
}
