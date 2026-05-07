package com.revenuecat.purchases.kmp.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.Purchases

@Composable
fun SubscriberAttributesTestingScreen(
    navigateTo: (Screen) -> Unit
) {
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var lastCalledFunction by remember { mutableStateOf<String?>(null) }

    fun recordSuccess(functionName: String) {
        statusMessage = "Called successfully"
        lastCalledFunction = functionName
    }

    var emailValue by remember { mutableStateOf("") }
    var phoneValue by remember { mutableStateOf("") }
    var displayNameValue by remember { mutableStateOf("") }
    var pushTokenValue by remember { mutableStateOf("") }
    var mixpanelValue by remember { mutableStateOf("") }
    var onesignalIdValue by remember { mutableStateOf("") }
    var onesignalUserIdValue by remember { mutableStateOf("") }
    var postHogValue by remember { mutableStateOf("") }
    var airshipValue by remember { mutableStateOf("") }
    var firebaseValue by remember { mutableStateOf("") }
    var adjustValue by remember { mutableStateOf("") }
    var airbridgeValue by remember { mutableStateOf("") }
    var appsflyerValue by remember { mutableStateOf("") }
    var fbAnonValue by remember { mutableStateOf("") }
    var mparticleValue by remember { mutableStateOf("") }
    var cleverTapValue by remember { mutableStateOf("") }
    var mediaSourceValue by remember { mutableStateOf("") }
    var campaignValue by remember { mutableStateOf("") }
    var adGroupValue by remember { mutableStateOf("") }
    var adValue by remember { mutableStateOf("") }
    var keywordValue by remember { mutableStateOf("") }
    var creativeValue by remember { mutableStateOf("") }
    var customKey by remember { mutableStateOf("") }
    var customValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Subscriber Attributes Testing",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Set or clear subscriber attributes",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(24.dp))

        // ── USER PROFILE ──────────────────────────────────────
        SectionHeader("User Profile")

        AttributeRow(
            label = "Email",
            value = emailValue,
            onValueChange = { emailValue = it },
            onSet = {
                Purchases.sharedInstance.setEmail(emailValue.ifBlank { null })
                recordSuccess("setEmail(${emailValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setEmail(null)
                recordSuccess("setEmail(null)")
            }
        )

        AttributeRow(
            label = "Phone Number",
            value = phoneValue,
            onValueChange = { phoneValue = it },
            onSet = {
                Purchases.sharedInstance.setPhoneNumber(phoneValue.ifBlank { null })
                recordSuccess("setPhoneNumber(${phoneValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setPhoneNumber(null)
                recordSuccess("setPhoneNumber(null)")
            }
        )

        AttributeRow(
            label = "Display Name",
            value = displayNameValue,
            onValueChange = { displayNameValue = it },
            onSet = {
                Purchases.sharedInstance.setDisplayName(displayNameValue.ifBlank { null })
                recordSuccess("setDisplayName(${displayNameValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setDisplayName(null)
                recordSuccess("setDisplayName(null)")
            }
        )

        AttributeRow(
            label = "Push Token",
            value = pushTokenValue,
            onValueChange = { pushTokenValue = it },
            onSet = {
                Purchases.sharedInstance.setPushToken(pushTokenValue.ifBlank { null })
                recordSuccess("setPushToken(${pushTokenValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setPushToken(null)
                recordSuccess("setPushToken(null)")
            }
        )

        // ── THIRD-PARTY INTEGRATIONS ──────────────────────────
        SectionHeader("Third-party Integrations")

        AttributeRow(
            label = "Mixpanel Distinct ID",
            value = mixpanelValue,
            onValueChange = { mixpanelValue = it },
            onSet = {
                Purchases.sharedInstance.setMixpanelDistinctID(mixpanelValue.ifBlank { null })
                recordSuccess("setMixpanelDistinctID(${mixpanelValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setMixpanelDistinctID(null)
                recordSuccess("setMixpanelDistinctID(null)")
            }
        )

        AttributeRow(
            label = "OneSignal ID (legacy)",
            value = onesignalIdValue,
            onValueChange = { onesignalIdValue = it },
            onSet = {
                Purchases.sharedInstance.setOnesignalID(onesignalIdValue.ifBlank { null })
                recordSuccess("setOnesignalID(${onesignalIdValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setOnesignalID(null)
                recordSuccess("setOnesignalID(null)")
            }
        )

        AttributeRow(
            label = "OneSignal User ID",
            value = onesignalUserIdValue,
            onValueChange = { onesignalUserIdValue = it },
            onSet = {
                Purchases.sharedInstance.setOnesignalUserID(onesignalUserIdValue.ifBlank { null })
                recordSuccess("setOnesignalUserID(${onesignalUserIdValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setOnesignalUserID(null)
                recordSuccess("setOnesignalUserID(null)")
            }
        )

        AttributeRow(
            label = "PostHog User ID",
            value = postHogValue,
            onValueChange = { postHogValue = it },
            onSet = {
                Purchases.sharedInstance.setPostHogUserID(postHogValue.ifBlank { null })
                recordSuccess("setPostHogUserID(${postHogValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setPostHogUserID(null)
                recordSuccess("setPostHogUserID(null)")
            }
        )

        AttributeRow(
            label = "Airship Channel ID",
            value = airshipValue,
            onValueChange = { airshipValue = it },
            onSet = {
                Purchases.sharedInstance.setAirshipChannelID(airshipValue.ifBlank { null })
                recordSuccess("setAirshipChannelID(${airshipValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setAirshipChannelID(null)
                recordSuccess("setAirshipChannelID(null)")
            }
        )

        AttributeRow(
            label = "Firebase App Instance ID",
            value = firebaseValue,
            onValueChange = { firebaseValue = it },
            onSet = {
                Purchases.sharedInstance.setFirebaseAppInstanceID(firebaseValue.ifBlank { null })
                recordSuccess("setFirebaseAppInstanceID(${firebaseValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setFirebaseAppInstanceID(null)
                recordSuccess("setFirebaseAppInstanceID(null)")
            }
        )

        AttributeRow(
            label = "Adjust ID",
            value = adjustValue,
            onValueChange = { adjustValue = it },
            onSet = {
                Purchases.sharedInstance.setAdjustID(adjustValue.ifBlank { null })
                recordSuccess("setAdjustID(${adjustValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setAdjustID(null)
                recordSuccess("setAdjustID(null)")
            }
        )

        AttributeRow(
            label = "Airbridge Device ID",
            value = airbridgeValue,
            onValueChange = { airbridgeValue = it },
            onSet = {
                Purchases.sharedInstance.setAirbridgeDeviceID(airbridgeValue.ifBlank { null })
                recordSuccess("setAirbridgeDeviceID(${airbridgeValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setAirbridgeDeviceID(null)
                recordSuccess("setAirbridgeDeviceID(null)")
            }
        )

        AttributeRow(
            label = "Appsflyer ID",
            value = appsflyerValue,
            onValueChange = { appsflyerValue = it },
            onSet = {
                Purchases.sharedInstance.setAppsflyerID(appsflyerValue.ifBlank { null })
                recordSuccess("setAppsflyerID(${appsflyerValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setAppsflyerID(null)
                recordSuccess("setAppsflyerID(null)")
            }
        )

        AttributeRow(
            label = "FB Anonymous ID",
            value = fbAnonValue,
            onValueChange = { fbAnonValue = it },
            onSet = {
                Purchases.sharedInstance.setFBAnonymousID(fbAnonValue.ifBlank { null })
                recordSuccess("setFBAnonymousID(${fbAnonValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setFBAnonymousID(null)
                recordSuccess("setFBAnonymousID(null)")
            }
        )

        AttributeRow(
            label = "Mparticle ID",
            value = mparticleValue,
            onValueChange = { mparticleValue = it },
            onSet = {
                Purchases.sharedInstance.setMparticleID(mparticleValue.ifBlank { null })
                recordSuccess("setMparticleID(${mparticleValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setMparticleID(null)
                recordSuccess("setMparticleID(null)")
            }
        )

        AttributeRow(
            label = "CleverTap ID",
            value = cleverTapValue,
            onValueChange = { cleverTapValue = it },
            onSet = {
                Purchases.sharedInstance.setCleverTapID(cleverTapValue.ifBlank { null })
                recordSuccess("setCleverTapID(${cleverTapValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setCleverTapID(null)
                recordSuccess("setCleverTapID(null)")
            }
        )

        // ── AD ATTRIBUTION ────────────────────────────────────
        SectionHeader("Ad Attribution")

        AttributeRow(
            label = "Media Source",
            value = mediaSourceValue,
            onValueChange = { mediaSourceValue = it },
            onSet = {
                Purchases.sharedInstance.setMediaSource(mediaSourceValue.ifBlank { null })
                recordSuccess("setMediaSource(${mediaSourceValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setMediaSource(null)
                recordSuccess("setMediaSource(null)")
            }
        )

        AttributeRow(
            label = "Campaign",
            value = campaignValue,
            onValueChange = { campaignValue = it },
            onSet = {
                Purchases.sharedInstance.setCampaign(campaignValue.ifBlank { null })
                recordSuccess("setCampaign(${campaignValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setCampaign(null)
                recordSuccess("setCampaign(null)")
            }
        )

        AttributeRow(
            label = "Ad Group",
            value = adGroupValue,
            onValueChange = { adGroupValue = it },
            onSet = {
                Purchases.sharedInstance.setAdGroup(adGroupValue.ifBlank { null })
                recordSuccess("setAdGroup(${adGroupValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setAdGroup(null)
                recordSuccess("setAdGroup(null)")
            }
        )

        AttributeRow(
            label = "Ad",
            value = adValue,
            onValueChange = { adValue = it },
            onSet = {
                Purchases.sharedInstance.setAd(adValue.ifBlank { null })
                recordSuccess("setAd(${adValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setAd(null)
                recordSuccess("setAd(null)")
            }
        )

        AttributeRow(
            label = "Keyword",
            value = keywordValue,
            onValueChange = { keywordValue = it },
            onSet = {
                Purchases.sharedInstance.setKeyword(keywordValue.ifBlank { null })
                recordSuccess("setKeyword(${keywordValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setKeyword(null)
                recordSuccess("setKeyword(null)")
            }
        )

        AttributeRow(
            label = "Creative",
            value = creativeValue,
            onValueChange = { creativeValue = it },
            onSet = {
                Purchases.sharedInstance.setCreative(creativeValue.ifBlank { null })
                recordSuccess("setCreative(${creativeValue.ifBlank { "null" }})")
            },
            onClear = {
                Purchases.sharedInstance.setCreative(null)
                recordSuccess("setCreative(null)")
            }
        )

        // ── DEVICE ────────────────────────────────────────────
        SectionHeader("Device")

        Button(
            onClick = {
                Purchases.sharedInstance.collectDeviceIdentifiers()
                recordSuccess("collectDeviceIdentifiers()")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Collect Device Identifiers")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── CUSTOM ATTRIBUTES ─────────────────────────────────
        SectionHeader("Custom Attributes")

        OutlinedTextField(
            value = customKey,
            onValueChange = { customKey = it },
            label = { Text("Key") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = customValue,
            onValueChange = { customValue = it },
            label = { Text("Value (blank = null)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    Purchases.sharedInstance.setAttributes(mapOf(customKey to customValue.ifBlank { null }))
                    recordSuccess("setAttributes(\"$customKey\" to ${customValue.ifBlank { "null" }})")
                },
                enabled = customKey.isNotBlank(),
                modifier = Modifier.weight(1f)
            ) { Text("Set") }
            Button(
                onClick = {
                    Purchases.sharedInstance.setAttributes(mapOf(customKey to null))
                    recordSuccess("setAttributes(\"$customKey\" to null)")
                },
                enabled = customKey.isNotBlank(),
                modifier = Modifier.weight(1f)
            ) { Text("Clear") }
        }

        Spacer(modifier = Modifier.height(24.dp))

        statusMessage?.let { message ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Green.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = message,
                        color = Color.Green.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.body2
                    )
                    lastCalledFunction?.let { fn ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = fn,
                            fontFamily = FontFamily.Monospace,
                            color = Color.Gray,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { statusMessage = null; lastCalledFunction = null },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear Status")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            onClick = { navigateTo(Screen.Main) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go back")
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = title,
        style = MaterialTheme.typography.subtitle1,
        color = Color.Black.copy(alpha = 0.7f)
    )
    Divider(modifier = Modifier.padding(vertical = 4.dp))
}

@Composable
private fun AttributeRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onSet: () -> Unit,
    onClear: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onSet,
                modifier = Modifier.weight(1f)
            ) { Text("Set") }
            Button(
                onClick = onClear,
                modifier = Modifier.weight(1f)
            ) { Text("Clear") }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
