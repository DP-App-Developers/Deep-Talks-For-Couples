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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dpappdev.deeptalkscouples.model.CardData
import com.dpappdev.deeptalkscouples.ui.components.SwipeableCard

@Composable
fun CardDeckScreen(
    modifier: Modifier = Modifier
) {
    val cards = remember { CardData.sampleCards }
    var currentIndex by remember { mutableStateOf(0) }

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
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (currentIndex < cards.size) {
                    // Show next card (behind) - same position as top card
                    if (currentIndex + 1 < cards.size) {
                        val nextCard = cards[currentIndex + 1]
                        key(nextCard.id) {
                            SwipeableCard(
                                question = nextCard.question,
                                category = nextCard.category,
                                isTopCard = false
                            )
                        }
                    }

                    // Show current card (on top) - same position
                    val currentCard = cards[currentIndex]
                    key(currentCard.id) {
                        SwipeableCard(
                            question = currentCard.question,
                            category = currentCard.category,
                            isTopCard = true,
                            onSwiped = {
                                currentIndex++
                            }
                        )
                    }
                } else {
                    // All cards swiped
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "🎉",
                            fontSize = 64.sp
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "All cards explored!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF6B9D),
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Great conversations ahead",
                            fontSize = 16.sp,
                            color = Color(0xFF666666),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Footer - Card counter
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                if (currentIndex < cards.size) {
                    Text(
                        text = "${currentIndex + 1} / ${cards.size}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF999999)
                    )
                }
            }
        }
    }
}
