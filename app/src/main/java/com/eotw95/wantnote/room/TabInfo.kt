package com.eotw95.wantnote.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabInfos")
data class TabInfo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var name: String,
    var order: Int
)