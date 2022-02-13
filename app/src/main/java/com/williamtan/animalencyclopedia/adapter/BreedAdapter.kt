package com.williamtan.animalencyclopedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.williamtan.animalencyclopedia.databinding.ListItemBreedBinding
import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType

class BreedAdapter(
    private val onBreedClick: (AnimalType, String) -> Unit
) : ListAdapter<BreedEntity, BreedViewHolder>(BreedItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BreedViewHolder(
        ListItemBreedBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(getItem(position), onBreedClick)
    }

    object BreedItemDiff : DiffUtil.ItemCallback<BreedEntity>() {
        override fun areItemsTheSame(oldItem: BreedEntity, newItem: BreedEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BreedEntity, newItem: BreedEntity): Boolean =
            oldItem == newItem
    }
}

class BreedViewHolder(
    private val binding: ListItemBreedBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BreedEntity, onBreedClick: (AnimalType, String) -> Unit) {
        binding.tvBreedName.text = item.name

        Glide.with(binding.root)
            .load(item.imageUrl)
            .into(binding.ivBreed)

        binding.cvRoot.setOnClickListener {
            onBreedClick(item.animalType, item.id)
        }
    }
}