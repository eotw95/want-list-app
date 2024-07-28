package com.eotw95.wantnote.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eotw95.wantnote.common.util.Converter

@Database(
    entities = [WantItem::class, TabInfo::class, CategorizedItem::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(Converter::class)
abstract class WantDatabase(): RoomDatabase() {
    companion object {
        private var instanse: WantDatabase? = null

        fun getInstance(context: Context): WantDatabase {
            return instanse ?: synchronized(this) {
                val tmpInstanse = Room.databaseBuilder(
                    context,
                    WantDatabase::class.java,
                    "WantDatabase"
                ).build()
                instanse = tmpInstanse

                return instanse as WantDatabase
            }
        }
    }
    abstract fun wantDao(): WantDao
    abstract fun tabInfoDao(): TabInfoDao
    abstract fun categorizedItemDao(): CategorizedItemDao
}