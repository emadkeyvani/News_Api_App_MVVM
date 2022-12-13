package com.keyvani.newsapiappmvvm.di

import android.content.Context
import androidx.room.Room
import com.keyvani.newsapiappmvvm.db.NewsDatabase
import com.keyvani.newsapiappmvvm.models.Article
import com.keyvani.newsapiappmvvm.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, NewsDatabase::class.java, Constants.NEWS_TABLE
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: NewsDatabase) = db.newsDao()

    @Provides
    @Singleton
    fun provideEntity() = Article()
}