package com.keyvani.newsapiappmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keyvani.newsapiappmvvm.models.NewsResponse
import com.keyvani.newsapiappmvvm.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    val breakingNews: MutableLiveData<NewsResponse> = MutableLiveData()
    val loading = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()
    var searchNewsPage = 1
    var breakingNewsPage = 1



    fun loadBreakingNews(countryCode: String) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getBreakingNews(countryCode, breakingNewsPage)
        if (response.isSuccessful) {
            breakingNews.postValue(response.body())

        }
        loading.postValue(false)
    }

    fun loadSearchNews(q:String) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.searchNews(q,searchNewsPage)
        if (response.isSuccessful){
            if(response.body()?.articles!!.isNotEmpty()){
                breakingNews.postValue(response.body())
                empty.postValue(false)
            }else{
                empty.postValue(true)
            }
        }
        loading.postValue(false)
    }


}