package com.dpappdev.deeptalkscouples.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun SwipeableCard(
    question: String,
    category: String,
    modifier: Modifier = Modifier,
    onSwiped: () -> Unit = {},
    isTopCard: Boolean = true
) {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var isSwiping by remember { mutableStateOf(false) }

    val swipeThreshold = 300f

    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .aspectRatio(0.7f)
            .graphicsLayer {
                translationX = offsetX.value
                translationY = offsetY.value
                rotationZ = rotation.value
            }
            .pointerInput(Unit) {
                if (isTopCard && !isSwiping) {
                    detectDragGestures(
                        onDragEnd = {
                            scope.launch {
                                if (abs(offsetX.value) > swipeThreshold) {
                                    isSwiping = true
                                    // Swipe away animation
                                    val targetX = if (offsetX.value > 0) 1000f else -1000f
                                    launch {
                                        offsetX.animateTo(
                                            targetValue = targetX,
                                            animationSpec = tween(300)
                                        )
                                    }
                                    launch {
                                        rotation.animateTo(
                                            targetValue = if (offsetX.value > 0) 45f else -45f,
                                            animationSpec = tween(300)
                                        )
                                    }.invokeOnCompletion {
                                        // Call onSwiped after animation completes
                                        onSwiped()
                                    }
                                } else {
                                    // Snap back animation
                                    launch { offsetX.animateTo(0f, tween(300)) }
                                    launch { offsetY.animateTo(0f, tween(300)) }
                                    launch { rotation.animateTo(0f, tween(300)) }
                                }
                            }
                        },
                        onDrag = { change, dragAmount ->
                            if (!isSwiping) {
                                change.consume()
                                scope.launch {
                                    offsetX.snapTo(offsetX.value + dragAmount.x)
                                    offsetY.snapTo(offsetY.value + dragAmount.y)
                                    rotation.snapTo(offsetX.value / 20f)
                                }
                            }
                        }
                    )
                }
            },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isTopCard) 8.dp else 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF6B9D),
                            Color(0xFFC06C84)
                        )
                    )
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Deep Talks",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White.copy(alpha = 0.8f),
                    letterSpacing = 2.sp
                )
                
                Spacer(modifier = Modifier.height(36.dp))
                
                Text(
                    text = question,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp
                )
            }
        }
    }
}
