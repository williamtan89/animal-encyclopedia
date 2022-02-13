package com.williamtan.animalencyclopedia.breeddetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.databinding.CommonScreenStateBinding
import com.williamtan.animalencyclopedia.databinding.FragmentBreedDetailBinding
import com.williamtan.common.entity.BreedEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreedDetailFragment : Fragment() {
    companion object {
        private const val MAXIMUM_ENERGY_LEVEL = 5.0f
    }

    private val viewModel: BreedDetailViewModel by viewModels()
    private lateinit var binding: FragmentBreedDetailBinding
    private lateinit var stateBinding: CommonScreenStateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedDetailBinding.inflate(inflater)
        stateBinding = CommonScreenStateBinding.bind(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbBreed.apply {
            setNavigationIcon(R.drawable.ic_back_24)
            setNavigationOnClickListener {
                view.findNavController().popBackStack()
            }
        }

        // start collecting uistate flow
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is BreedDetailViewModel.ScreenState.Empty -> {
                            stateBinding.layoutEmptyState.isVisible = true
                            stateBinding.layoutErrorState.isVisible = false
                            stateBinding.layoutLoadingState.isVisible = false
                        }

                        is BreedDetailViewModel.ScreenState.Error -> {
                            stateBinding.layoutEmptyState.isVisible = false
                            stateBinding.layoutErrorState.isVisible = true
                            stateBinding.layoutLoadingState.isVisible = false
                        }

                        is BreedDetailViewModel.ScreenState.Loading -> {
                            stateBinding.layoutEmptyState.isVisible = false
                            stateBinding.layoutErrorState.isVisible = false
                            stateBinding.layoutLoadingState.isVisible = true
                        }

                        is BreedDetailViewModel.ScreenState.Success -> {
                            stateBinding.layoutEmptyState.isVisible = false
                            stateBinding.layoutErrorState.isVisible = false
                            stateBinding.layoutLoadingState.isVisible = false

                            updateUi(it.breed)
                        }
                    }
                }
            }
        }
    }

    private fun updateUi(breed: BreedEntity) {
        binding.tvBreedId.text = resources.getString(R.string.breed_detail_id, breed.id.uppercase())
        binding.tvBreedName.text = breed.name
        binding.tvBreedDesc.text = breed.description

        // clear existing chips, if any
        binding.cgTemperament.removeAllViews()
        breed.temperamentList.forEach { temperament ->
            val newChip = Chip(context).apply {
                text = temperament
                setEnsureMinTouchTargetSize(false)
                isClickable = false
            }

            binding.cgTemperament.addView(newChip)
        }

        binding.tvEnergyLevel.text = resources.getString(
            R.string.breed_detail_energy_level,
            breed.energyLevel
        )
        binding.pbEnergyLevel.progress = (breed.energyLevel / MAXIMUM_ENERGY_LEVEL * 100).toInt()

        Glide.with(this)
            .load(breed.imageUrl)
            .placeholder(R.drawable.ic_placeholder_48)
            .into(binding.ivToolbar)

        if (!breed.isFavorite) {
            binding.btnFavorite.text = resources.getString(R.string.add_to_favorite)
        } else {
            binding.btnFavorite.text = resources.getString(R.string.remove_from_favorite)
        }

        binding.btnFavorite.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.onFavoriteButtonClick(breed)
            }
        }

        binding.btnReadMore.isVisible = !breed.wikipediaUrl.isNullOrBlank()
        binding.btnReadMore.setOnClickListener {
            val defaultBrowser =
                Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
            defaultBrowser.data = Uri.parse(breed.wikipediaUrl)
            startActivity(defaultBrowser)
        }
    }
}