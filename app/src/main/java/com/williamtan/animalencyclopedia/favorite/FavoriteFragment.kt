package com.williamtan.animalencyclopedia.favorite

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
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.adapter.BreedAdapter
import com.williamtan.animalencyclopedia.databinding.CommonScreenStateBinding
import com.williamtan.animalencyclopedia.databinding.FragmentFavoriteBinding
import com.williamtan.animalencyclopedia.home.HomeFragmentDirections
import com.williamtan.animalencyclopedia.view.GridItemDecoration
import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var stateBinding: CommonScreenStateBinding
    private lateinit var adapter: BreedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        stateBinding = CommonScreenStateBinding.bind(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // start collecting uistate flow
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is FavoriteViewModel.ScreenState.Empty -> {
                            stateBinding.layoutScreenState.isVisible = true
                            stateBinding.tvState.text = resources.getString(R.string.empty_state)
                        }

                        is FavoriteViewModel.ScreenState.Error -> {
                            stateBinding.layoutScreenState.isVisible = true
                            stateBinding.tvState.text = resources.getString(R.string.error_state)
                        }

                        is FavoriteViewModel.ScreenState.Loading -> {
                            stateBinding.layoutScreenState.isVisible = true
                            stateBinding.tvState.text = resources.getString(R.string.loading_state)
                        }

                        is FavoriteViewModel.ScreenState.Success -> {
                            stateBinding.layoutScreenState.isVisible = false
                            updateUi(it.favoriteList)
                        }
                    }
                }
            }
        }
    }

    private fun updateUi(favoriteList: List<BreedEntity>) {
        // setup breed list
        adapter = BreedAdapter(onBreedClick)

        binding.rvFavorite.apply {
            adapter = this@FavoriteFragment.adapter

            context?.let {
                val spacing = context.resources.getDimensionPixelSize(R.dimen.list_item_spacing_8dp)
                addItemDecoration(GridItemDecoration(spacing))
            }

            (adapter as? BreedAdapter)?.submitList(favoriteList)
        }
    }

    private val onBreedClick: (AnimalType, String) -> Unit = { animalType, breedId ->
        val action = HomeFragmentDirections.homeToBreedDetailAction(animalType, breedId)
        view?.findNavController()?.navigate(action)
    }
}