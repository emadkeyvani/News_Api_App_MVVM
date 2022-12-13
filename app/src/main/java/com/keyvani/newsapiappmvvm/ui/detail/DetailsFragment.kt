package com.keyvani.newsapiappmvvm.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.keyvani.newsapiappmvvm.databinding.FragmentDetailsBinding
import com.keyvani.newsapiappmvvm.models.Article
import com.keyvani.newsapiappmvvm.viewmodel.DbViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentDetailsBinding

    @Inject
    lateinit var entity: Article

    //Other
    private val viewModel: DbViewModel by viewModels()
    val args: DetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitView
        binding.apply {

            //WebView
            val detail = args.detail
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(detail.url)
            }

            fab.setOnClickListener {
                entity.title = detail.title
                entity.description = detail.description
                entity.publishedAt = detail.publishedAt
                entity.urlToImage = detail.urlToImage
                entity.source = detail.source
                entity.url=detail.url
                viewModel.favoriteNews(entity)
                Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
            }


        }


    }


}