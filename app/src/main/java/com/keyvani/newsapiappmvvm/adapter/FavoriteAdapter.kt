package com.keyvani.newsapiappmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.keyvani.newsapiappmvvm.databinding.ItemNewsBinding
import com.keyvani.newsapiappmvvm.models.NewsEntity
import javax.inject.Inject


class FavoriteAdapter @Inject constructor() : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    //Binding
    private lateinit var binding: ItemNewsBinding
    private var newsList = emptyList<NewsEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(newsList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = newsList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: NewsEntity) {
            binding.apply {
                tvTitre.text = item.title
                ivNewsPoster.load(item.urlToImage) {
                    crossfade(true)
                    crossfade(800)
                }
                tvNewsDetails.text = item.description
                // tvReference.text = item.source.name
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

    private var onItemClickListener: ((NewsEntity) -> Unit)? = null

    fun setonItemClickListener(listener: (NewsEntity) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<NewsEntity>) {
        val moviesDiffUtil = MoviesDiffUtils(newsList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        newsList = data
        diffUtils.dispatchUpdatesTo(this)

    }

    class MoviesDiffUtils(
        private val oldItem: List<NewsEntity>, private val newItem: List<NewsEntity>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }


    }

}


