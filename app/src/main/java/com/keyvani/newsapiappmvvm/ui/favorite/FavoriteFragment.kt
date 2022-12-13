package com.keyvani.newsapiappmvvm.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.keyvani.newsapiappmvvm.R
import com.keyvani.newsapiappmvvm.adapter.NewsAdapter
import com.keyvani.newsapiappmvvm.databinding.FragmentFavoriteBinding
import com.keyvani.newsapiappmvvm.models.NewsEntity
import com.keyvani.newsapiappmvvm.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var entity: NewsEntity

    //Other
    private val viewModel: NewsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //InitViews
        binding.apply {

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


}