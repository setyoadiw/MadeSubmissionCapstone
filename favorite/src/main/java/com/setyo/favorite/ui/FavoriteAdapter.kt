package com.setyo.favorite.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.setyo.common.domain.model.MovieData
import com.setyo.favorite.databinding.ItemListFavoriteMovieBinding


class FavoriteAdapter(private val onItemClick: (MovieData) -> Unit) : ListAdapter<MovieData, FavoriteAdapter.ViewHolder>
    (object : DiffUtil.ItemCallback<MovieData>() {
    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean = newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean = oldItem == newItem

}){
    inner class ViewHolder(private val binding: ItemListFavoriteMovieBinding)  : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val data = getItem(position)

            binding.tvItemScore.text = String.format("%.1f", data.voteAverage)
            binding.tvItemTitle.text = data.originalTitle
            binding.tvItemSubtitle.text = data.overview

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/original/${data.posterPath}")
                .into(binding.ivItemImage)

            binding.root.setOnClickListener {
                onItemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  {
        val views = LayoutInflater.from(parent.context)
            .inflate(com.setyo.favorite.R.layout.item_list_favorite_movie, parent, false)
        val binding = ItemListFavoriteMovieBinding.bind(views)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

}