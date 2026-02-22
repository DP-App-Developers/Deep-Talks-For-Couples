package com.dpappdev.deeptalkscouples.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
    
    // Function to get a random card from the deck
    fun getRandomCard(): Card {
        return cards[Random.nextInt(cards.size)]
    }
    
    var currentCard by remember { mutableStateOf(getRandomCard()) }
    var nextCard by remember { mutableStateOf(getRandomCard()) }
    var currentCardKey by remember { mutableStateOf(0) }
    var nextCardKey by remember { mutableStateOf(1) }

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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Deep Talks",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6B9D)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Swipe to explore",
                    fontSize = 16.sp,
                    color = Color(0xFF666666),
                    fontWeight = FontWeight.Medium
                )
            }

            // Card Stack
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                // Show next card (behind) - same position as top card
                key(nextCardKey) {
                    SwipeableCard(
                        question = nextCard.question,
                        category = nextCard.category,
                        isTopCard = false
                    )
                }

                // Show current card (on top) - same position
                key(currentCardKey) {
                    SwipeableCard(
                        question = currentCard.question,
                        category = currentCard.category,
                        isTopCard = true,
                        onSwiped = {
                            // Move next card to current, and get a new random next card
                            currentCard = nextCard
                            currentCardKey = nextCardKey
                            nextCard = getRandomCard()
                            nextCardKey++
                        }
                    )
                }
            }
        }
    }
}
