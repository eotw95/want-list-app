package com.eotw95.wantnote.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WantItem::class], version = 3, exportSchema = false)
//@TypeConverters(BitmapConverter::class)
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
}