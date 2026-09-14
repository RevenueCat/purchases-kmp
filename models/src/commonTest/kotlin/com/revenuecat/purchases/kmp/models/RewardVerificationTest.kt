package com.revenuecat.purchases.kmp.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RewardVerificationTest {

    @Test
    fun `RewardVerificationToken can be constructed`() {
        val token = RewardVerificationToken(
            customData = "{\"api_key\":\"test\"}",
            clientTransactionId = "txn-123",
            appUserID = "user-456",
        )

        assertNotNull(token)
        assertEquals("{\"api_key\":\"test\"}", token.customData)
        assertEquals("txn-123", token.clientTransactionId)
        assertEquals("user-456", token.appUserID)
    }

    @Test
    fun `VerifiedReward VirtualCurrency holds code and amount`() {
        val reward = VerifiedReward.VirtualCurrency(code = "coins", amount = 100)

        assertEquals("coins", reward.code)
        assertEquals(100, reward.amount)
    }

    @Test
    fun `VerifiedReward Entitlement derives expiresAt from millis`() {
        val reward = VerifiedReward.Entitlement(identifier = "premium", expiresAtMillis = 1_700_000_000_000L)

        assertEquals("premium", reward.identifier)
        assertEquals(1_700_000_000_000L, reward.expiresAtMillis)
        assertEquals(1_700_000_000_000L, reward.expiresAt.toEpochMilliseconds())
    }

    @Test
    fun `RewardVerificationResult can represent a verified reward with more rewards`() {
        val primary = VerifiedReward.VirtualCurrency(code = "coins", amount = 100)
        val bonus = VerifiedReward.VirtualCurrency(code = "gems", amount = 5)
        val result = RewardVerificationResult(
            verifiedReward = primary,
            moreRewards = listOf(bonus),
            failed = false,
        )

        assertEquals(primary, result.verifiedReward)
        assertEquals(listOf(bonus), result.moreRewards)
        assertTrue(!result.failed)
    }

    @Test
    fun `RewardVerificationResult can represent a failure`() {
        val result = RewardVerificationResult(
            verifiedReward = null,
            moreRewards = emptyList(),
            failed = true,
        )

        assertEquals(null, result.verifiedReward)
        assertEquals(emptyList(), result.moreRewards)
        assertTrue(result.failed)
    }
}
