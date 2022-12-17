package com.keyvani.newsapiappmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.keyvani.newsapiappmvvm.R
import com.keyvani.newsapiappmvvm.databinding.ItemNewsBinding
import com.keyvani.newsapiappmvvm.models.Article
import javax.inject.Inject

class NewsAdapter @Inject constructor() : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    //Binding
    private lateinit var binding: ItemNewsBinding

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }


    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(differ.currentList[position])
        holder.setIsRecyclable(false)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: Article) {
            binding.apply {
                tvTitre.text = item.title
                ivNewsPoster.load(item.urlToImage) {
                    crossfade(true)
                    crossfade(800)
                }
                tvNewsDetails.text = item.description
                tvReference.text = item.source?.name
                tvPublishedDate.text = item.publishedAt
                //Click
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }

            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}