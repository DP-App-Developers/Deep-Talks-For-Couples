package com.dpappdev.deeptalkscouples

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.dpappdev.deeptalkscouples.ui.screens.CardDeckScreen
import com.dpappdev.deeptalkscouples.ui.theme.DeepTalksCouplesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Lock to portrait orientation for narrow devices (phones)
        // Tablets (sw600dp+) can rotate freely
        val configuration = resources.configuration
        val screenWidthDp = configuration.screenWidthDp
        
        if (screenWidthDp < 600) {
            // Narrow device (phone) - lock to portrait
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        
        enableEdgeToEdge()
        setContent {
            DeepTalksCouplesTheme {
                CardDeckScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
