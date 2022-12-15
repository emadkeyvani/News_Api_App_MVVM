package com.keyvani.newsapiappmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyvani.newsapiappmvvm.models.Article
import com.keyvani.newsapiappmvvm.repository.DbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(private val repository: DbRepository) : ViewModel() {


    fun saveNews(entity: Article) = viewModelScope.launch {
        repository.upsertNews(entity)
    }

    fun getSavedNews() = repository.getAllNews()


    fun deleteNews(entity: Article) = viewModelScope.launch {
        repository.deleteNews(entity)
    }


}