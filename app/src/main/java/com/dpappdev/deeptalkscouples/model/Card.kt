package com.dpappdev.deeptalkscouples.model

data class Card(
    val id: Int,
    val question: String
)

// Sample card data
object CardData {
    val sampleCards = listOf(
        Card(1, "What's a dream you've never told anyone about?"),
        Card(2, "What moment in your life made you feel most alive?"),
        Card(3, "If you could relive one day together, which would it be?"),
        Card(4, "What's something you're afraid to ask me?"),
        Card(5, "What does love mean to you right now?"),
        Card(6, "What's a fear you'd like to overcome together?"),
        Card(7, "When do you feel most connected to me?"),
        Card(8, "What's a secret talent you wish you had?"),
        Card(9, "What's your favorite memory of us?"),
        Card(10, "What makes you feel most appreciated?")
    )
}
