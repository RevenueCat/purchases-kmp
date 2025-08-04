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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.VirtualCurrencies

@Composable
fun VirtualCurrencyTestingScreen(
    navigateTo: (Screen) -> Unit
) {
    var virtualCurrencies by remember { mutableStateOf<Any?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    fun clearVirtualCurrencies() {
        virtualCurrencies = null
        error = null
    }

    fun fetchVirtualCurrencies() {
        loading = true
        clearVirtualCurrencies()
        
        Purchases.sharedInstance.getVirtualCurrencies(
            onError = { purchasesError: PurchasesError ->
                val errorMessage = purchasesError.message
                println("Error fetching virtual currencies: $purchasesError")
                error = errorMessage
                loading = false
            },
            onSuccess = { currencies: VirtualCurrencies ->
                virtualCurrencies = currencies
                loading = false
            }
        )
    }

    // Comment out these unimplemented functions as requested
    /*
    fun invalidateVirtualCurrenciesCache() {
        loading = true
        clearVirtualCurrencies()
        
        try {
            // await Purchases.sharedInstance.invalidateVirtualCurrenciesCache()
        } catch (err: Exception) {
            val errorMessage = err.message ?: "Unknown error"
            println("Error invalidating virtual currencies cache: $err")
            error = errorMessage
        } finally {
            loading = false
        }
    }

    fun fetchCachedVirtualCurrencies() {
        loading = true
        clearVirtualCurrencies()
        
        try {
            // val cachedVirtualCurrencies = Purchases.sharedInstance.getCachedVirtualCurrencies()
            // if (cachedVirtualCurrencies == null) {
            //     virtualCurrencies = "Cached virtual currencies are null."
            // } else {
            //     virtualCurrencies = cachedVirtualCurrencies
            // }
        } catch (err: Exception) {
            val errorMessage = err.message ?: "Unknown error"
            println("Error fetching cached virtual currencies: $err")
            error = errorMessage
        } finally {
            loading = false
        }
    }
    */

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Virtual Currency Screen",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Use this screen to fetch and display virtual currencies from RevenueCat.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { fetchVirtualCurrencies() },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (loading) "Loading..." else "Fetch Virtual Currencies")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Comment out these buttons since the methods are unimplemented
        /*
        Button(
            onClick = { invalidateVirtualCurrenciesCache() },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Invalidate Virtual Currencies Cache")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { fetchCachedVirtualCurrencies() },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Fetch Cached Virtual Currencies")
        }
        */

        Spacer(modifier = Modifier.height(20.dp))

        error?.let { errorMessage ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Red.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Red.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        virtualCurrencies?.let { currencies ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Gray.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Virtual Currencies:",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    when (currencies) {
                        is String -> {
                            Text(
                                text = currencies,
                                style = MaterialTheme.typography.body2
                            )
                        }
                        is VirtualCurrencies -> {
                            if (currencies.all.isEmpty()) {
                                Text(
                                    text = "Virtual currencies are empty.",
                                    style = MaterialTheme.typography.body2
                                )
                            } else {
                                Text(
                                    text = formatVirtualCurrencies(currencies),
                                    fontFamily = FontFamily.Monospace,
                                    style = MaterialTheme.typography.body2,
                                    color = Color.Black.copy(alpha = 0.7f)
                                )
                            }
                        }
                        else -> {
                            Text(
                                text = currencies.toString(),
                                fontFamily = FontFamily.Monospace,
                                style = MaterialTheme.typography.body2,
                                color = Color.Black.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (virtualCurrencies != null || error != null) {
            Button(
                onClick = { clearVirtualCurrencies() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
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

private fun formatVirtualCurrencies(currencies: VirtualCurrencies): String {
    if (currencies.all.isEmpty()) {
        return "{}"
    }
    
    val jsonBuilder = StringBuilder()
    jsonBuilder.append("{\n")
    
    currencies.all.entries.forEachIndexed { index, (key, currency) ->
        jsonBuilder.append("  \"$key\": {\n")
        jsonBuilder.append("    \"name\": \"${currency.name}\",\n")
        jsonBuilder.append("    \"code\": \"${currency.code}\",\n")
        jsonBuilder.append("    \"balance\": ${currency.balance},\n")
        jsonBuilder.append("    \"serverDescription\": ")
        if (currency.serverDescription != null) {
            jsonBuilder.append("\"${currency.serverDescription}\"")
        } else {
            jsonBuilder.append("null")
        }
        jsonBuilder.append("\n  }")
        
        if (index < currencies.all.size - 1) {
            jsonBuilder.append(",")
        }
        jsonBuilder.append("\n")
    }
    
    jsonBuilder.append("}")
    return jsonBuilder.toString()
}
