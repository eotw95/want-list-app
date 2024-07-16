package com.eotw95.wantnote.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TabInfoDao {
    @Query("SELECT * FROM tabInfos")
    fun getAll(): Flow<List<TabInfo>>

    @Query("DELETE FROM tabInfos")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(info: TabInfo)

    @Delete
    suspend fun delete(info: TabInfo)

    @Update
    suspend fun update(info: TabInfo)

    @Transaction
    suspend fun updateOrder(new: List<TabInfo>) {
        new.forEach { update(it) }
    }
}