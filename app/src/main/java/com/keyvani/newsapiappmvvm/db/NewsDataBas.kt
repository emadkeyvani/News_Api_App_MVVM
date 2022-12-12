package com.keyvani.newsapiappmvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keyvani.newsapiappmvvm.models.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDataBas : RoomDatabase() {
    abstract fun newsDao() :NewsDao

}