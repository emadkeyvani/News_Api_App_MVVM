package com.keyvani.newsapiappmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyvani.newsapiappmvvm.models.NewsEntity
import com.keyvani.newsapiappmvvm.models.NewsResponse
import com.keyvani.newsapiappmvvm.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val breakingNews: MutableLiveData<NewsResponse> = MutableLiveData()
    val loading = MutableLiveData<Boolean>()



    fun loadBreakingNews(countryCode: String, pageNumber: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getBreakingNews(countryCode, pageNumber)
        if (response.isSuccessful) {
            breakingNews.postValue(response.body())

        }
        loading.postValue(false)
    }




}