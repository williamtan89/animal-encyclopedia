package com.williamtan.animalencyclopedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.williamtan.animalencyclopedia.databinding.ListItemPromotedBreedsBinding
import com.williamtan.animalencyclopedia.view.SimpleItemDecoration
import com.williamtan.common.entity.PromotedBreedsEntity
import com.williamtan.common.enumtype.AnimalType

typealias PromotedBreedsListAdapter = ListAdapter<PromotedBreedsEntity, PromotedBreedsViewHolder>

class PromotedBreedsAdapter(
    private val onAnimalTypeClick: (AnimalType) -> Unit,
    private val onPromotedBreedClick: (AnimalType, String) -> Unit
) : PromotedBreedsListAdapter(HomeItemDiff) {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PromotedBreedsViewHolder(
            ListItemPromotedBreedsBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: PromotedBreedsViewHolder, position: Int) {
        holder.bind(getItem(position), onAnimalTypeClick, onPromotedBreedClick, viewPool)
    }

    object HomeItemDiff : DiffUtil.ItemCallback<PromotedBreedsEntity>() {
        override fun areItemsTheSame(
            oldItem: PromotedBreedsEntity,
            newItem: PromotedBreedsEntity
        ): Boolean = oldItem.animalType == newItem.animalType

        override fun areContentsTheSame(
            oldItem: PromotedBreedsEntity,
            newItem: PromotedBreedsEntity
        ): Boolean = oldItem.promotedBreeds == newItem.promotedBreeds
    }
}

class PromotedBreedsViewHolder(private val binding: ListItemPromotedBreedsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: PromotedBreedsEntity,
        onAnimalTypeClick: (AnimalType) -> Unit,
        onPromotedBreedClick: (AnimalType, String) -> Unit,
        viewPool: RecyclerView.RecycledViewPool
    ) {
        binding.tvAnimalType.text = item.animalType.name
        binding.tvAnimalType.setOnClickListener { onAnimalTypeClick(item.animalType) }

        binding.rvRecentBreeds.apply {
            if (adapter == null) {
                adapter = BreedAdapter(onPromotedBreedClick)

                addItemDecoration(SimpleItemDecoration(context, DividerItemDecoration.HORIZONTAL))
                setRecycledViewPool(viewPool)
            }

            (adapter as? BreedAdapter)?.submitList(item.promotedBreeds)
        }
    }
}