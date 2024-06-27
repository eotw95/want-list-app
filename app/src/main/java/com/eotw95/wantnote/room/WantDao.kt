package com.eotw95.wantnote.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WantDao {
    @Query("SELECT * FROM wantItems")
    fun getAll(): Flow<List<WantItem>>

    @Query("SELECT * FROM wantItems WHERE id = :id")
    suspend fun getItemById(id: Int): WantItem

    @Insert
    suspend fun insert(item: WantItem)

    @Update
    suspend fun update(item: WantItem)

    @Delete
    suspend fun delete(item: WantItem)
}