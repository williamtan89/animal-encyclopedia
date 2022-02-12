package com.williamtan.animalencyclopedia.breed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.williamtan.animalencyclopedia.databinding.ListItemBreedBinding
import com.williamtan.common.entity.BreedEntity

class BreedAdapter : ListAdapter<BreedEntity, BreedViewHolder>(BreedItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BreedViewHolder(
        ListItemBreedBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object BreedItemDiff : DiffUtil.ItemCallback<BreedEntity>() {
        override fun areItemsTheSame(oldItem: BreedEntity, newItem: BreedEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BreedEntity, newItem: BreedEntity): Boolean =
            oldItem == newItem
    }
}

class BreedViewHolder(private val binding: ListItemBreedBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BreedEntity) {
        binding.tvBreedName.text = item.name

        Glide.with(binding.root)
            .load(item.imageUrl)
            .into(binding.ivBreed)
    }
}