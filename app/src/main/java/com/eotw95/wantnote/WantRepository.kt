package com.eotw95.wantnote

import com.eotw95.wantnote.room.CategorizedItem
import com.eotw95.wantnote.room.CategorizedItemDao
import com.eotw95.wantnote.room.TabInfo
import com.eotw95.wantnote.room.TabInfoDao
import com.eotw95.wantnote.room.WantDao
import com.eotw95.wantnote.room.WantItem
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class WantRepository @Inject constructor(
    private val wantDao: WantDao,
    private val tabInfoDao: TabInfoDao,
    private val categorizedItemDao: CategorizedItemDao,
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
    fun getTabInfos(): Flow<List<TabInfo>> = tabInfoDao.getAll()

    suspend fun insertTabInfo(tab: TabInfo) = tabInfoDao.insert(tab)

    suspend fun deleteTabInfo(tab: TabInfo) = tabInfoDao.delete(tab)

    suspend fun deleteAll() = tabInfoDao.deleteAll()

    suspend fun reorder(new: List<TabInfo>) = tabInfoDao.updateOrder(new)

    suspend fun updateTabInfo(new: TabInfo) = tabInfoDao.update(new)

    /**
     * CategorizedItemsDao
     */
    fun getCategorizedItems(): Flow<List<CategorizedItem>> = categorizedItemDao.getAll()

    fun insertCategorizedItem(items: CategorizedItem) = categorizedItemDao.insert(items)
}