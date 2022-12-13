package com.keyvani.newsapiappmvvm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyvani.newsapiappmvvm.R
import com.keyvani.newsapiappmvvm.adapter.NewsAdapter
import com.keyvani.newsapiappmvvm.databinding.FragmentHomeBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Api call
        viewModel.loadBreakingNews("us")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {

            //Get breaking news
            viewModel.breakingNews.observe(viewLifecycleOwner) {
                newsAdapter.differ.submitList(it.articles)
                //Recycler
                rvBreakingNews.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = newsAdapter
                }
            }
            newsAdapter.setOnItemClickListener {
                val bundle = Bundle().apply {
                    putSerializable("detail",it)
                }
                findNavController().navigate(
                    R.id.ToDetailsFragment, bundle
                )
            }
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    pbHome.visibility = View.VISIBLE
                    rvBreakingNews.visibility = View.GONE

                } else {
                    pbHome.visibility = View.GONE
                    rvBreakingNews.visibility = View.VISIBLE

                }
            }

        }

    }


}