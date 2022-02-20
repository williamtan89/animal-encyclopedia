package com.williamtan.animalencyclopedia.promoted.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.databinding.ListItemPromotedBreedBinding
import com.williamtan.animalencyclopedia.promoted.model.PromotedBreed
import com.williamtan.common.enumtype.AnimalType

class PromotedBreedAdapter(
    private val onBreedClick: (AnimalType, String) -> Unit
) : ListAdapter<PromotedBreed, PromotedBreedViewHolder>(PromotedBreedItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PromotedBreedViewHolder(
        ListItemPromotedBreedBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: PromotedBreedViewHolder, position: Int) {
        holder.bind(getItem(position), onBreedClick)
    }

    object PromotedBreedItemDiff : DiffUtil.ItemCallback<PromotedBreed>() {
        override fun areItemsTheSame(oldItem: PromotedBreed, newItem: PromotedBreed): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PromotedBreed, newItem: PromotedBreed): Boolean =
            oldItem == newItem
    }
}

class PromotedBreedViewHolder(
    private val binding: ListItemPromotedBreedBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PromotedBreed, onBreedClick: (AnimalType, String) -> Unit) {
        binding.tvBreedName.text = item.name

        Glide.with(binding.root)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_placeholder_48)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivBreed)

        binding.cvRoot.setOnClickListener {
            onBreedClick(item.animalType, item.id)
        }
    }
}