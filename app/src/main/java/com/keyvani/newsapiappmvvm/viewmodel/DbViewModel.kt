package com.keyvani.newsapiappmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyvani.newsapiappmvvm.models.Article
import com.keyvani.newsapiappmvvm.repository.DbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(private val repository: DbRepository) : ViewModel() {

    //Database
    val isFavorite = MutableLiveData<Boolean>()
    val favoriteList = MutableLiveData<List<Article>>()
    val empty = MutableLiveData<Boolean>()


    suspend fun existNews(id: Int) = viewModelScope.launch {
        repository.existNews(id)
    }

    fun favoriteNews(entity: Article) = viewModelScope.launch {
        isFavorite.postValue(true)
        repository.insertNews(entity)
        /*val exists = repository.existNews(id)
        if(exists){
            isFavorite.postValue(false)
            repository.deleteNews(entity)
        }else{

            repository.insertNews(entity)
        }*/
    }

    fun loadFavoriteList() = viewModelScope.launch {
        val list = repository.getAllNews()
        if (list.isNotEmpty()) {
            favoriteList.postValue(list)
            empty.postValue(false)

        } else {
            empty.postValue(true)
        }
    }

    fun deleteFavorite(entity: Article) = viewModelScope.launch {
        repository.deleteNews(entity)
    }

}