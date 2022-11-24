package com.setyo.common.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.setyo.common.R
import com.setyo.common.databinding.ItemReviewBinding
import com.setyo.common.domain.model.ReviewData

class ReviewAdapter : ListAdapter<ReviewData, ReviewAdapter.ViewHolder>
    (object : DiffUtil.ItemCallback<ReviewData>() {
    override fun areItemsTheSame(oldItem: ReviewData, newItem: ReviewData): Boolean = newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: ReviewData, newItem: ReviewData): Boolean = oldItem == newItem

}){
    inner class ViewHolder(private val binding: ItemReviewBinding)  : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val data = getItem(position)

            binding.tvName.text = data.author
            binding.tvContent.text = data.content
            binding.tvScore.text = String.format("%.1f", data.authorDetails?.rating)

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/original/${data.authorDetails?.avatarPath}")
                .into(binding.ivImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  {
        val views = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        val binding = ItemReviewBinding.bind(views)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

}