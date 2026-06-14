package com.dpappdev.deeptalkscouples.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dpappdev.deeptalkscouples.ads.RewardedAdManager
import com.dpappdev.deeptalkscouples.model.CardData
import com.dpappdev.deeptalkscouples.ui.components.SwipeableCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

private const val FREE_SWIPES_PER_SESSION = 10

@Composable
fun CardDeckScreen(
    modifier: Modifier = Modifier,
    rewardedAdManager: RewardedAdManager
) {
    val cards = remember { CardData.sampleCards }
    val scope = rememberCoroutineScope()

    fun getRandomCardId() = cards[Random.nextInt(cards.size)].id

    var currentCardId by rememberSaveable { mutableStateOf(getRandomCardId()) }
    var nextCardId by rememberSaveable { mutableStateOf(getRandomCardId()) }
    var currentCardKey by rememberSaveable { mutableStateOf(0) }
    var nextCardKey by rememberSaveable { mutableStateOf(1) }

    val currentCard = remember(currentCardId) { cards.first { it.id == currentCardId } }
    val nextCard = remember(nextCardId) { cards.first { it.id == nextCardId } }

    // Counts down from FREE_SWIPES_PER_SESSION; when it hits 0 the paywall triggers
    var swipesRemaining by rememberSaveable { mutableStateOf(FREE_SWIPES_PER_SESSION) }
    var showPaywall by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFF5F7), Color(0xFFFFE5EC))
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Swipe to explore",
                fontSize = 16.sp,
                color = Color(0xFF666666),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                key(nextCardKey) {
                    SwipeableCard(
                        questionResId = nextCard.questionResId,
                        isTopCard = false
                    )
                }

                key(currentCardKey) {
                    SwipeableCard(
                        questionResId = currentCard.questionResId,
                        isTopCard = true,
                        onSwiped = {
                            scope.launch {
                                delay(100)
                                currentCardId = nextCardId
                                currentCardKey = nextCardKey
                                nextCardId = getRandomCardId()
                                nextCardKey++

                                if (swipesRemaining > 0) swipesRemaining--
                                if (swipesRemaining <= 0 && rewardedAdManager.isAdReady) {
                                    showPaywall = true
                                }
                            }
                        }
                    )
                }
            }
        }

        if (showPaywall) {
            PaywallDialog(
                onWatchAd = {
                    rewardedAdManager.showAd {
                        swipesRemaining = FREE_SWIPES_PER_SESSION
                        showPaywall = false
                    }
                }
            )
        }
    }
}

@Composable
private fun PaywallDialog(onWatchAd: () -> Unit) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "💕", fontSize = 48.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Keep the Conversation Going",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Watch a short ad to unlock\n10 more cards",
                    fontSize = 15.sp,
                    color = Color(0xFF888888),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onWatchAd,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6B9D)
                    )
                ) {
                    Text(
                        text = "Watch Ad & Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}
