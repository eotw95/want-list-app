package com.eotw95.wantnote

import androidx.room.Transaction
import com.eotw95.wantnote.room.TabInfo
import com.eotw95.wantnote.room.TabInfoDao
import com.eotw95.wantnote.room.WantDao
import com.eotw95.wantnote.room.WantItem
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class WantRepository @Inject constructor(
    private val wantDao: WantDao,
    private val tabInfoDao: TabInfoDao
) {

    /**
     * WantDao
     */
    fun getAll(): Flow<List<WantItem>> = wantDao.getAll()

    suspend fun getItemById(id: Int): WantItem = wantDao.getItemById(id)

    suspend fun insert(item: WantItem) = wantDao.insert(item)

    suspend fun update(item: WantItem) = wantDao.update(item)

    suspend fun delete(item: WantItem) {
        wantDao.delete(item)
        File(item.imagePath).delete()
    }

    /**
     * TabInfoDao
     */
    fun getTabInfos(): Flow<List<TabInfo>> {
        println("getTabInfos: ${tabInfoDao.getAll()}")
        return tabInfoDao.getAll()
    }

    suspend fun insertTabInfo(info: TabInfo) = tabInfoDao.insert(info)

    suspend fun deleteTabInfo(info: TabInfo) = tabInfoDao.delete(info)

    suspend fun deleteAll() = tabInfoDao.deleteAll()

    suspend fun reorder(new: List<TabInfo>) = tabInfoDao.updateOrder(new)
}