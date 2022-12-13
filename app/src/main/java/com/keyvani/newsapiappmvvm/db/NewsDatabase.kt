package com.keyvani.newsapiappmvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keyvani.newsapiappmvvm.models.NewsEntity

@Database(entities = [NewsEntity::class], version = 3, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao() :NewsDao
}