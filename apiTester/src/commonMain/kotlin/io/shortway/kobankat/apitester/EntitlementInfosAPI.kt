package io.shortway.kobankat.apitester

import io.shortway.kobankat.EntitlementInfo
import io.shortway.kobankat.EntitlementInfos
import io.shortway.kobankat.VerificationResult
import io.shortway.kobankat.active
import io.shortway.kobankat.all
import io.shortway.kobankat.get
import io.shortway.kobankat.verification

@Suppress("unused", "UNUSED_VARIABLE")
private class EntitlementInfosAPI {
    fun check(infos: EntitlementInfos) {
        val active: Map<String, EntitlementInfo> = infos.active
        val all: Map<String, EntitlementInfo> = infos.all
        val i: EntitlementInfo? = infos[""]
        val verification: VerificationResult = infos.verification
    }
}
