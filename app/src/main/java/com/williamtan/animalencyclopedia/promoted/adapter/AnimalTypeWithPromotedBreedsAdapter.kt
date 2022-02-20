package com.williamtan.animalencyclopedia.promoted.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.databinding.ListItemAnimalTypeWithPromotedBreedsBinding
import com.williamtan.animalencyclopedia.promoted.model.AnimalTypeWithPromotedBreeds
import com.williamtan.animalencyclopedia.promoted.model.PromotedBreed
import com.williamtan.animalencyclopedia.view.SimpleItemDecoration
import com.williamtan.common.enumtype.AnimalType

class AnimalTypeWithPromotedBreedsAdapter(
    private val onAnimalTypeClick: (AnimalType) -> Unit,
    private val onPromotedBreedClick: (AnimalType, String) -> Unit
) : ListAdapter<AnimalTypeWithPromotedBreeds, PromotedBreedsViewHolder>(PromotedBreedsItemDiff) {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PromotedBreedsViewHolder(
            ListItemAnimalTypeWithPromotedBreedsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PromotedBreedsViewHolder, position: Int) {
        holder.bind(getItem(position), onAnimalTypeClick, onPromotedBreedClick, viewPool)
    }

    object PromotedBreedsItemDiff : DiffUtil.ItemCallback<AnimalTypeWithPromotedBreeds>() {
        override fun areItemsTheSame(
            oldItem: AnimalTypeWithPromotedBreeds,
            newItem: AnimalTypeWithPromotedBreeds
        ): Boolean = oldItem.animalType == newItem.animalType

        override fun areContentsTheSame(
            oldItem: AnimalTypeWithPromotedBreeds,
            newItem: AnimalTypeWithPromotedBreeds
        ): Boolean = oldItem.promotedBreedsItemState == newItem.promotedBreedsItemState
    }

    sealed class PromotedBreedsItemState {
        object Loading : PromotedBreedsItemState()
        object Error : PromotedBreedsItemState()
        data class Ready(val data: List<PromotedBreed>) : PromotedBreedsItemState()
    }
}

class PromotedBreedsViewHolder(private val binding: ListItemAnimalTypeWithPromotedBreedsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: AnimalTypeWithPromotedBreeds,
        onAnimalTypeClick: (AnimalType) -> Unit,
        onPromotedBreedClick: (AnimalType, String) -> Unit,
        viewPool: RecyclerView.RecycledViewPool
    ) {
        binding.tvAnimalType.text = item.animalType.name
        binding.tvAnimalType.setOnClickListener { onAnimalTypeClick(item.animalType) }

        // initialize adapter if not init yet
        binding.rvRecentBreeds.apply {
            if (adapter == null) {
                adapter = PromotedBreedAdapter(onPromotedBreedClick)

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
            is AnimalTypeWithPromotedBreedsAdapter.PromotedBreedsItemState.Loading -> {
                binding.layoutState.isVisible = true
                binding.pbLoading.isVisible = true
                binding.tvState.text = resources.getString(R.string.promoted_breeds_loading_state)
            }
            is AnimalTypeWithPromotedBreedsAdapter.PromotedBreedsItemState.Ready -> {
                binding.pbLoading.isVisible = false

                if (state.data.isEmpty()) {
                    binding.layoutState.isVisible = true
                    binding.tvState.text = resources.getString(R.string.promoted_breeds_empty_state)

                } else {
                    binding.layoutState.isVisible = false
                    (binding.rvRecentBreeds.adapter as? PromotedBreedAdapter)?.submitList(state.data)
                }
            }
            is AnimalTypeWithPromotedBreedsAdapter.PromotedBreedsItemState.Error -> {
                binding.layoutState.isVisible = true
                binding.pbLoading.isVisible = false
                binding.tvState.text = resources.getString(R.string.promoted_breeds_error_state)
            }
        }
    }
}