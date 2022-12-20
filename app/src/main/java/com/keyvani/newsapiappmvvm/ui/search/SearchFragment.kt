package com.keyvani.newsapiappmvvm.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyvani.newsapiappmvvm.R
import com.keyvani.newsapiappmvvm.adapters.NewsAdapter
import com.keyvani.newsapiappmvvm.databinding.FragmentSearchBinding
import com.keyvani.newsapiappmvvm.utils.Constants.SEARCH_DELAY_TIME
import com.keyvani.newsapiappmvvm.utils.Resource
import com.keyvani.newsapiappmvvm.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var searchAdapter: NewsAdapter

    //Other
    private val viewModel: ApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        //InitViews
        binding.apply {

            var job: Job? = null
            edtSearch.addTextChangedListener {
                job?.cancel()
                job = MainScope().launch {
                    delay(SEARCH_DELAY_TIME)
                    it?.let {
                        val search = it.toString()
                        if (it.toString().isNotEmpty()) {
                            viewModel.searchNews(it.toString())
                        }
                    }

                }

            }
            viewModel.searchNews.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            searchAdapter.differ.submitList(newsResponse.articles)

                        }

                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.massage?.let { massage ->
                            Timber.i(massage)
                        }

                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }

                searchAdapter.setOnItemClickListener {
                    val bundle = Bundle().apply {
                        putSerializable("detail", it)
                    }
                    findNavController().navigate(
                        R.id.ToDetailsFragment, bundle
                    )
                }
            }
        }
    }


    private fun hideProgressBar() {
        binding.pbSearch.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.pbSearch.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        searchAdapter = NewsAdapter()
        binding.apply {
            rvSearchedNews.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = searchAdapter
            }
        }

    }
}