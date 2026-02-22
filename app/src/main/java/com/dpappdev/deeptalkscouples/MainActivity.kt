package com.dpappdev.deeptalkscouples

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
        enableEdgeToEdge()
        setContent {
            DeepTalksCouplesTheme {
                CardDeckScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
