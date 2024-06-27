package com.eotw95.wantnote.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wantItems")
data class WantItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val link: String,
    val description: String,
    val imagePath: String
)