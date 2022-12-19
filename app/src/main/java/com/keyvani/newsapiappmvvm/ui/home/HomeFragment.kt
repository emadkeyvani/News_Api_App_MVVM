package com.keyvani.newsapiappmvvm.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.keyvani.newsapiappmvvm.R
import com.keyvani.newsapiappmvvm.adapters.NewsAdapter
import com.keyvani.newsapiappmvvm.databinding.FragmentHomeBinding
import com.keyvani.newsapiappmvvm.utils.Constants
import com.keyvani.newsapiappmvvm.utils.Constants.QUERY_PAGE_SIZE
import com.keyvani.newsapiappmvvm.utils.Resource
import com.keyvani.newsapiappmvvm.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var newsAdapter: NewsAdapter

    //Other
    private val viewModel: ApiViewModel by viewModels()

    val TAG = "HomeFragment"
    var doubleBackToExitPressedOnce = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        //InitViews
        binding.apply {

            //Get breaking news
            viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles.toList())
                            val totalPages = (newsResponse.totalResults?.div(QUERY_PAGE_SIZE))?.plus(2)
                            isLastPage = viewModel.breakingNewsPage == totalPages
                            if(isLastPage){
                                rvBreakingNews.setPadding(0,0,0,0)
                            }

                        }

                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.massage?.let { massage ->
                            Log.e(TAG, "An error:$massage")
                        }

                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }


            }
            newsAdapter.setOnItemClickListener {
                val bundle = Bundle().apply {
                    putSerializable("detail", it)
                }
                findNavController().navigate(
                    R.id.ToDetailsFragment, bundle
                )
            }


        }


    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.apply {
            rvBreakingNews.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = newsAdapter
                addOnScrollListener(this@HomeFragment.scrollListener)
            }
        }

    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getBreakingNews("us")
                isScrolling = false

            }
        }
    }


    private fun hideProgressBar() {
        binding.pbHome.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.pbHome.visibility = View.VISIBLE
        isLoading = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    activity?.finish()
                }
                doubleBackToExitPressedOnce = true
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
                Toast.makeText(requireContext(), "Double press to exit", Toast.LENGTH_SHORT).show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


}