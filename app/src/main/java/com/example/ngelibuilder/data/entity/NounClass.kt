package com.example.ngelibuilder.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noun_classes")
data class NounClass (
    @PrimaryKey val classCode: String,
    val className: String,
    val description: String,
    val singularPrefix: String? = null,
    val pluralPrefix: String? = null

)