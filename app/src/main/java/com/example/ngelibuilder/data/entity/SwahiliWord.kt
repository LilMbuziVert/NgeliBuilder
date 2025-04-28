package com.example.ngelibuilder.data.entity

import android.icu.text.DisplayOptions.NounClass
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "swahili_words")
data class SwahiliWord (
    @PrimaryKey val id: Int? = null,
    val word: String,
    val nounClass: String,
    val meaning: String,
    val exampleSentence: String? = null,
    val difficultyLevel: Int = 1

)
