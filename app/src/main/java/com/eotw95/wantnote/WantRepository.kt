package com.eotw95.wantnote

import com.eotw95.wantnote.room.WantDao
import com.eotw95.wantnote.room.WantItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WantRepository @Inject constructor(private val dao: WantDao) {

    fun getAll(): Flow<List<WantItem>> = dao.getAll()

    suspend fun getItemById(id: Int): WantItem = dao.getItemById(id)

    suspend fun insert(item: WantItem) = dao.insert(item)

    suspend fun update(item: WantItem) = dao.update(item)

    suspend fun delete(item: WantItem) = dao.delete(item)
}