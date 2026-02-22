package com.dpappdev.deeptalkscouples.model

data class Card(
    val id: Int,
    val question: String,
    val category: String = "Deep Talk"
)

// Sample card data
object CardData {
    val sampleCards = listOf(
        Card(1, "What's a dream you've never told anyone about?", "Dreams"),
        Card(2, "What moment in your life made you feel most alive?", "Memories"),
        Card(3, "If you could relive one day together, which would it be?", "Relationship"),
        Card(4, "What's something you're afraid to ask me?", "Vulnerability"),
        Card(5, "What does love mean to you right now?", "Love"),
        Card(6, "What's a fear you'd like to overcome together?", "Growth"),
        Card(7, "When do you feel most connected to me?", "Connection"),
        Card(8, "What's a secret talent you wish you had?", "Aspirations"),
        Card(9, "What's your favorite memory of us?", "Memories"),
        Card(10, "What makes you feel most appreciated?", "Appreciation")
    )
}
