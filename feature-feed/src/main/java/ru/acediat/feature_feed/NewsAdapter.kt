package ru.acediat.feature_feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.acediat.feature_feed.databinding.ItemPostBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun getItemCount(): Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
    }

    class NewsViewHolder(
        private val binding : ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }
}