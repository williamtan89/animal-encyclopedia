package com.williamtan.animalencyclopedia.breed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.breed.model.Breed
import com.williamtan.animalencyclopedia.databinding.ListItemBreedBinding
import com.williamtan.common.enumtype.AnimalType

class BreedAdapter(
    private val onBreedClick: (AnimalType, String) -> Unit
) : ListAdapter<Breed, BreedViewHolder>(BreedItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BreedViewHolder(
        ListItemBreedBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(getItem(position), onBreedClick)
    }

    object BreedItemDiff : DiffUtil.ItemCallback<Breed>() {
        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean =
            oldItem == newItem
    }
}

class BreedViewHolder(
    private val binding: ListItemBreedBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Breed, onBreedClick: (AnimalType, String) -> Unit) {
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