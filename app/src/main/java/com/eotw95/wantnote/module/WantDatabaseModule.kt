package com.eotw95.wantnote.module

import android.content.Context
import com.eotw95.wantnote.room.TabInfoDao
import com.eotw95.wantnote.room.WantDao
import com.eotw95.wantnote.room.WantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WantDatabaseModule {
    @Provides
    @Singleton
    fun provideWantDatabase(@ApplicationContext context: Context): WantDatabase {
        return WantDatabase.getInstance(context)
    }
    @Provides
    @Singleton
    fun provideWantDao(db: WantDatabase): WantDao = db.wantDao()
    @Provides
    @Singleton
    fun provideTabInfoDao(db: WantDatabase): TabInfoDao = db.tabInfoDao()
}