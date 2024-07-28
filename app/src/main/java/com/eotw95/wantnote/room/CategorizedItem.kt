package com.eotw95.wantnote.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorized_item")
data class CategorizedItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val items: List<WantItem>
)
