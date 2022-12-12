package com.keyvani.newsapiappmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyvani.newsapiappmvvm.models.NewsResponse
import com.keyvani.newsapiappmvvm.repository.NewsRepository
import com.keyvani.newsapiappmvvm.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository):ViewModel(){

    val breakingNewsList = MutableLiveData<MutableList<NewsResponse.Article>>()
    val loading = MutableLiveData<Boolean>()

    fun loadBreakingNews(countryCode: String,pageNumber: Int,apikey: String) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getBreakingNews("us",1, "ecf3da52e8da449bb5effb0b1b940725")
        if(response.isSuccessful){
            breakingNewsList.postValue(response.body())
        }
        loading.postValue(false)
    }
}