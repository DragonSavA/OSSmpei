package ru.acediat.feature_feed

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.acediat.feature_feed.databinding.ItemPostBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class NewsViewHolder(
        private val binding : ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }
}