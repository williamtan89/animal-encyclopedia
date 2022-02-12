package com.williamtan.animalencyclopedia.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.williamtan.animalencyclopedia.breed.BreedAdapter
import com.williamtan.animalencyclopedia.databinding.ListItemHomeBinding
import com.williamtan.animalencyclopedia.view.SimpleItemDecoration
import com.williamtan.common.entity.AnimalTypeWithPromotedBreedsEntity

class HomeAdapter : ListAdapter<AnimalTypeWithPromotedBreedsEntity, HomeViewHolder>(HomeItemDiff) {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeViewHolder(
        ListItemHomeBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position), viewPool)
    }

    object HomeItemDiff : DiffUtil.ItemCallback<AnimalTypeWithPromotedBreedsEntity>() {
        override fun areItemsTheSame(
            oldItem: AnimalTypeWithPromotedBreedsEntity,
            newItem: AnimalTypeWithPromotedBreedsEntity
        ): Boolean = oldItem.animalType == newItem.animalType

        override fun areContentsTheSame(
            oldItem: AnimalTypeWithPromotedBreedsEntity,
            newItem: AnimalTypeWithPromotedBreedsEntity
        ): Boolean = oldItem.promotedBreeds == newItem.promotedBreeds
    }
}

class HomeViewHolder(private val binding: ListItemHomeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AnimalTypeWithPromotedBreedsEntity, viewPool: RecyclerView.RecycledViewPool) {
        binding.tvAnimalType.text = item.animalType.name

        binding.rvRecentBreeds.apply {
            if (adapter == null) {
                adapter = BreedAdapter()

                addItemDecoration(SimpleItemDecoration(context, DividerItemDecoration.HORIZONTAL))
                setRecycledViewPool(viewPool)
            }

            (adapter as? BreedAdapter)?.submitList(item.promotedBreeds)
        }
    }
}