package com.tehscott.cionictakehome.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tehscott.cionictakehome.R
import com.tehscott.cionictakehome.databinding.FeedListItemBinding
import com.tehscott.cionictakehome.models.local.FeedItem

class FeedListAdapter(
    private val onItemClicked: (FeedItem) -> Unit
) : ListAdapter<FeedItem, FeedListAdapter.ViewHolder>(DiffUtilItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FeedListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].let { feedItem ->
            val resources = holder.root.resources
            holder.root.setBackgroundColor(
                resources.getColor(
                    if (position % 2 == 0) R.color.blue else R.color.white, null
                )
            )
            holder.root.setOnClickListener {
                onItemClicked(feedItem)
            }
            holder.title.text = feedItem.title
            holder.body.text = feedItem.body
        }
    }

    override fun getItemCount(): Int = currentList.size

    inner class ViewHolder(binding: FeedListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val root: ConstraintLayout = binding.root
        val title: TextView = binding.title
        val body: TextView = binding.body
    }

    object DiffUtilItemCallback : DiffUtil.ItemCallback<FeedItem>() {
        override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
            oldItem == newItem
    }
}