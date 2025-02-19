package com.revenuecat.purchases.kmp.sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    private var urlString by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        urlString = intent.dataString

        setContent {
            App(urlString = urlString)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        urlString = intent.dataString
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(urlString = null)
}
