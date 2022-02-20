package com.williamtan.animalencyclopedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.databinding.ListItemPromotedBreedsBinding
import com.williamtan.animalencyclopedia.promoted.PromotedBreeds
import com.williamtan.animalencyclopedia.view.SimpleItemDecoration
import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType

typealias PromotedBreedsListAdapter = ListAdapter<PromotedBreeds, PromotedBreedsViewHolder>

class PromotedBreedsAdapter(
    private val onAnimalTypeClick: (AnimalType) -> Unit,
    private val onPromotedBreedClick: (AnimalType, String) -> Unit
) : PromotedBreedsListAdapter(PromotedBreedsItemDiff) {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PromotedBreedsViewHolder(
            ListItemPromotedBreedsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PromotedBreedsViewHolder, position: Int) {
        holder.bind(getItem(position), onAnimalTypeClick, onPromotedBreedClick, viewPool)
    }

    object PromotedBreedsItemDiff : DiffUtil.ItemCallback<PromotedBreeds>() {
        override fun areItemsTheSame(
            oldItem: PromotedBreeds,
            newItem: PromotedBreeds
        ): Boolean = oldItem.animalType == newItem.animalType

        override fun areContentsTheSame(
            oldItem: PromotedBreeds,
            newItem: PromotedBreeds
        ): Boolean = oldItem.promotedBreedsItemState == newItem.promotedBreedsItemState
    }

    sealed class PromotedBreedsItemState {
        object Loading : PromotedBreedsItemState()
        object Error : PromotedBreedsItemState()
        data class Ready(val data: List<BreedEntity>) : PromotedBreedsItemState()
    }
}

class PromotedBreedsViewHolder(private val binding: ListItemPromotedBreedsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: PromotedBreeds,
        onAnimalTypeClick: (AnimalType) -> Unit,
        onPromotedBreedClick: (AnimalType, String) -> Unit,
        viewPool: RecyclerView.RecycledViewPool
    ) {
        binding.tvAnimalType.text = item.animalType.name
        binding.tvAnimalType.setOnClickListener { onAnimalTypeClick(item.animalType) }

        // initialize adapter if not init yet
        binding.rvRecentBreeds.apply {
            if (adapter == null) {
                adapter = BreedAdapter(onPromotedBreedClick)

                val spacing =
                    context.resources.getDimensionPixelSize(R.dimen.list_item_spacing_16dp)
                addItemDecoration(
                    SimpleItemDecoration(
                        context,
                        spacing,
                        DividerItemDecoration.HORIZONTAL
                    )
                )
                setRecycledViewPool(viewPool)
            }
        }

        // update promoted breed list ui state
        val resources = binding.root.context.resources
        when (val state = item.promotedBreedsItemState) {
            is PromotedBreedsAdapter.PromotedBreedsItemState.Loading -> {
                binding.layoutState.isVisible = true
                binding.pbLoading.isVisible = true
                binding.tvState.text = resources.getString(R.string.promoted_breeds_loading_state)
            }
            is PromotedBreedsAdapter.PromotedBreedsItemState.Ready -> {
                binding.pbLoading.isVisible = false

                if (state.data.isEmpty()) {
                    binding.layoutState.isVisible = true
                    binding.tvState.text = resources.getString(R.string.promoted_breeds_empty_state)

                } else {
                    binding.layoutState.isVisible = false
                    (binding.rvRecentBreeds.adapter as? BreedAdapter)?.submitList(state.data)
                }
            }
            is PromotedBreedsAdapter.PromotedBreedsItemState.Error -> {
                binding.layoutState.isVisible = true
                binding.pbLoading.isVisible = false
                binding.tvState.text = resources.getString(R.string.promoted_breeds_error_state)
            }
        }
    }
}