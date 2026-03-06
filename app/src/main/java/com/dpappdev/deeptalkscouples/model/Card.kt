package com.dpappdev.deeptalkscouples.model

import androidx.annotation.StringRes
import com.dpappdev.deeptalkscouples.R

data class Card(
    val id: Int,
    @StringRes val questionResId: Int
)

// Sample card data
object CardData {
    val sampleCards = listOf(
        Card(1, R.string.question_1),
        Card(2, R.string.question_2),
        Card(3, R.string.question_3),
        Card(4, R.string.question_4),
        Card(5, R.string.question_5),
        Card(6, R.string.question_6),
        Card(7, R.string.question_7),
        Card(8, R.string.question_8),
        Card(9, R.string.question_9),
        Card(10, R.string.question_10)
    )
}
