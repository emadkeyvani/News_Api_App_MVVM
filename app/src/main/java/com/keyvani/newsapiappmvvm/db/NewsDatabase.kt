package com.keyvani.newsapiappmvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.keyvani.newsapiappmvvm.models.Article

@Database(entities = [Article::class], version = 8, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao() :NewsDao
}