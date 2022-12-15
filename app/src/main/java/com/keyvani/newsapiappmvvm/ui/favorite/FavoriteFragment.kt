package com.keyvani.newsapiappmvvm.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.keyvani.newsapiappmvvm.R
import com.keyvani.newsapiappmvvm.adapters.NewsAdapter
import com.keyvani.newsapiappmvvm.databinding.FragmentFavoriteBinding
import com.keyvani.newsapiappmvvm.models.Article
import com.keyvani.newsapiappmvvm.viewmodel.DbViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var entity: Article


    //Other
    private val viewModel: DbViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

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
            viewModel.getSavedNews().observe(viewLifecycleOwner) {
                newsAdapter.differ.submitList(it)
            }

            //Swipe delete

            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val article = newsAdapter.differ.currentList[position]
                    viewModel.deleteNews(article)
                    Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            viewModel.saveNews(article)
                        }

                    }.show()
                }
            }

            ItemTouchHelper(itemTouchHelperCallback).apply {
                attachToRecyclerView(rvFavoriteNews)
            }


        }


    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.apply {
            rvFavoriteNews.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = newsAdapter
            }
        }

    }


}