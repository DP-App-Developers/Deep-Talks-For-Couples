package com.dpappdev.deeptalkscouples.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dpappdev.deeptalkscouples.model.Card
import com.dpappdev.deeptalkscouples.model.CardData
import com.dpappdev.deeptalkscouples.ui.components.SwipeableCard
import kotlin.random.Random

@Composable
fun CardDeckScreen(
    modifier: Modifier = Modifier
) {
    val cards = remember { CardData.sampleCards }
    
    // Function to get a random card ID from the deck
    fun getRandomCardId(): Int {
        return cards[Random.nextInt(cards.size)].id
    }
    
    // Store card IDs instead of Card objects (Ints are automatically saveable)
    var currentCardId by rememberSaveable { mutableStateOf(getRandomCardId()) }
    var nextCardId by rememberSaveable { mutableStateOf(getRandomCardId()) }
    var currentCardKey by rememberSaveable { mutableStateOf(0) }
    var nextCardKey by rememberSaveable { mutableStateOf(1) }
    
    // Get the actual Card objects from IDs
    val currentCard = remember(currentCardId) { cards.first { it.id == currentCardId } }
    val nextCard = remember(nextCardId) { cards.first { it.id == nextCardId } }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF5F7),
                        Color(0xFFFFE5EC)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
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

            // Card Stack
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                // Show next card (behind) - same position as top card
                key(nextCardKey) {
                    SwipeableCard(
                        questionResId = nextCard.questionResId,
                        isTopCard = false
                    )
                }

                // Show current card (on top) - same position
                key(currentCardKey) {
                    SwipeableCard(
                        questionResId = currentCard.questionResId,
                        isTopCard = true,
                        onSwiped = {
                            // Move next card to current, and get a new random next card
                            currentCardId = nextCardId
                            currentCardKey = nextCardKey
                            nextCardId = getRandomCardId()
                            nextCardKey++
                        }
                    )
                }
            }
        }
    }
}
