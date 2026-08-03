@file:OptIn(ExperimentalRevenueCatApi::class)

package com.revenuecat.purchases.kmp.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.RewardVerificationResult
import com.revenuecat.purchases.kmp.models.RewardVerificationToken
import com.revenuecat.purchases.kmp.models.VerifiedReward

private fun describeReward(reward: VerifiedReward): String =
    when (reward) {
        is VerifiedReward.VirtualCurrency -> "+${reward.amount} ${reward.code}"
        is VerifiedReward.Entitlement -> "entitlement \"${reward.identifier}\""
        VerifiedReward.NoReward -> "no reward"
        VerifiedReward.UnsupportedReward -> "unsupported reward"
    }

@Composable
fun RewardVerificationTestingScreen(
    navigateTo: (Screen) -> Unit
) {
    val controller = remember { RewardedAdController() }
    val presentingHost = rememberAdPresentingHost()
    var status by remember { mutableStateOf("Tap \"Load ad\" to begin.") }
    var messageColor by remember { mutableStateOf(Color.Gray) }
    var token by remember { mutableStateOf<RewardVerificationToken?>(null) }
    var adReady by remember { mutableStateOf(false) }

    fun loadAd() {
        adReady = false
        token = null
        status = "Loading ad…"
        messageColor = Color.Gray

        controller.loadAd(
            adUnitId = rewardedAdUnitId,
            presentingHost = presentingHost,
            onLoaded = { responseId ->
                val generatedToken = Purchases.sharedInstance.generateRewardVerificationToken(responseId)
                token = generatedToken
                controller.setServerSideVerificationOptions(
                    userId = generatedToken.appUserID,
                    customData = generatedToken.customData
                )
                adReady = true
                status = "Ad ready. impressionId: $responseId"
                messageColor = Color.Gray
            },
            onFailedToLoad = { error ->
                status = "Failed to load: $error"
                messageColor = Color.Red
            }
        )
    }

    fun describeResult(result: RewardVerificationResult): String {
        val reward = result.verifiedReward
        if (result.failed || reward == null) return "❌ verification failed"
        val more = if (result.moreRewards.isEmpty()) "" else " (+${result.moreRewards.size} more)"
        return "✅ ${describeReward(reward)}$more"
    }

    fun showAd() {
        val clientTransactionId = token?.clientTransactionId ?: return
        adReady = false
        controller.present(
            presentingHost = presentingHost,
            onUserEarnedReward = {
                status = "Verifying reward…"
                messageColor = Color.Gray
                Purchases.sharedInstance.pollRewardVerification(clientTransactionId) { result ->
                    status = describeResult(result)
                    messageColor = if (result.failed) Color.Red else Color.Green
                }
            },
            onDismissed = { loadAd() }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Reward Verification Testing",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Manual test of generateRewardVerificationToken() / pollRewardVerification() " +
                "against a real rewarded ad.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { loadAd() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Load ad")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { showAd() },
            enabled = adReady,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Watch ad to earn reward")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = messageColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = status,
                color = messageColor.copy(alpha = 0.8f),
                style = MaterialTheme.typography.body2
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = { navigateTo(Screen.Main) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}
