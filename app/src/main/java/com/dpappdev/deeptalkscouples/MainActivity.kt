package com.dpappdev.deeptalkscouples

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.dpappdev.deeptalkscouples.ads.RewardedAdManager
import com.dpappdev.deeptalkscouples.ui.screens.CardDeckScreen
import com.dpappdev.deeptalkscouples.ui.theme.DeepTalksCouplesTheme
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {

    private lateinit var rewardedAdManager: RewardedAdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lock to portrait on phones; tablets can rotate freely
        if (resources.configuration.screenWidthDp < 600) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        // Create the manager first, then start loading only after the SDK is fully ready
        rewardedAdManager = RewardedAdManager(this)
        MobileAds.initialize(this) {
            rewardedAdManager.startLoading()
        }

        enableEdgeToEdge()
        setContent {
            DeepTalksCouplesTheme {
                CardDeckScreen(
                    modifier = Modifier.fillMaxSize(),
                    rewardedAdManager = rewardedAdManager
                )
            }
        }
    }
}
