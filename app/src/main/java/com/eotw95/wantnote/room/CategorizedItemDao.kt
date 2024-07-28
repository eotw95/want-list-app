package com.eotw95.wantnote.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategorizedItemDao {
    @Query("SELECT * FROM categorized_item")
    fun getAll(): Flow<List<CategorizedItem>>

    @Insert
    fun insert(items: CategorizedItem)
}