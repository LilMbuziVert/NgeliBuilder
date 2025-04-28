package com.example.ngelibuilder.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.util.Date

@Entity(
    tableName = "user_progress",
    foreignKeys = [
        ForeignKey(
            entity = SwahiliWord::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class UserProgress(
    @PrimaryKey val id: Int? = null,
    val wordId: Int,
    val answeredCorrectly: Boolean,
    val timestamp: Date
)