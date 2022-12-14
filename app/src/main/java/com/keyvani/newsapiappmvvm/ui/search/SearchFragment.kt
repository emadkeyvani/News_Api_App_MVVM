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
import com.keyvani.newsapiappmvvm.adapter.NewsAdapter
import com.keyvani.newsapiappmvvm.databinding.FragmentSearchBinding
import com.keyvani.newsapiappmvvm.utils.Constants.SEARCH_DELAY_TIME
import com.keyvani.newsapiappmvvm.utils.initRecycler
import com.keyvani.newsapiappmvvm.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        //InitViews
        binding.apply {
            var job : Job? = null
            edtSearch.addTextChangedListener {
                job?.cancel()
                job= MainScope().launch {
                    delay(SEARCH_DELAY_TIME)
                    it?.let {
                        val search = it.toString()
                        if (search.isNotEmpty()) {
                            viewModel.loadSearchNews(search)
                        }
                    }

                }

            }
            // Get News list
            viewModel.breakingNews.observe(viewLifecycleOwner) {
                searchAdapter.differ.submitList(it.articles)
                //RecyclerView
                rvSearchedNews.initRecycler(LinearLayoutManager(requireContext()), searchAdapter)

            }
            searchAdapter.setOnItemClickListener {
                val bundle = Bundle().apply {
                    putSerializable("detail", it)
                }
                findNavController().navigate(
                    R.id.ToDetailsFragment, bundle
                )
            }
            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    pbSearch.visibility = View.VISIBLE
                } else {
                    pbSearch.visibility = View.GONE

                }
            }
            //Empty items
            viewModel.empty.observe(viewLifecycleOwner) {
                if (it) {
                    conEmptyLayout.visibility = View.VISIBLE
                    rvSearchedNews.visibility = View.GONE
                } else {
                    conEmptyLayout.visibility = View.GONE
                    rvSearchedNews.visibility = View.VISIBLE
                }
            }

        }
    }


}