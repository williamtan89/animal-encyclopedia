package com.williamtan.animalencyclopedia.breed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.breed.adapter.BreedAdapter
import com.williamtan.animalencyclopedia.databinding.CommonScreenStateBinding
import com.williamtan.animalencyclopedia.databinding.FragmentBreedBinding
import com.williamtan.animalencyclopedia.view.GridItemDecoration
import com.williamtan.common.enumtype.AnimalType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BreedFragment : Fragment() {
    private val viewModel: BreedViewModel by viewModels()
    private val args: BreedFragmentArgs by navArgs()

    private lateinit var binding: FragmentBreedBinding
    private lateinit var stateBinding: CommonScreenStateBinding
    private lateinit var adapter: BreedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedBinding.inflate(inflater)
        stateBinding = CommonScreenStateBinding.bind(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup toolbar navigation
        binding.tbBreed.apply {
            setNavigationIcon(R.drawable.ic_back_24)
            setNavigationOnClickListener {
                view.findNavController().popBackStack()
            }
        }

        // setup breed list
        adapter = BreedAdapter(onBreedClick)

        binding.rvBreed.apply {
            adapter = this@BreedFragment.adapter

            context?.let {
                val spacing = context.resources.getDimensionPixelSize(R.dimen.list_item_spacing_8dp)
                addItemDecoration(GridItemDecoration(spacing))
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (
                        !recyclerView.canScrollVertically(1) &&
                        newState == RecyclerView.SCROLL_STATE_IDLE
                    ) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.loadBreeds(args.animalType)
                        }
                    }
                }
            })
        }

        // setup searchview
        binding.svBreed.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // don't have to do anything since onQueryTextChange will updates in the end
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (binding.svBreed.isIconified) {
                    return true
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.searchQuery.emit(newText ?: "")
                }

                return true
            }
        })

        // start collecting uistate flow
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is BreedViewModel.ScreenState.Empty -> {
                            stateBinding.layoutScreenState.isVisible = true
                            stateBinding.tvState.text = resources.getString(R.string.empty_state)
                        }

                        is BreedViewModel.ScreenState.Error -> {
                            stateBinding.layoutScreenState.isVisible = true
                            stateBinding.tvState.text = resources.getString(R.string.error_state)
                        }

                        is BreedViewModel.ScreenState.Loading -> {
                            stateBinding.layoutScreenState.isVisible = true
                            stateBinding.tvState.text = resources.getString(R.string.loading_state)
                            viewModel.breedData.value.isEmpty()
                        }

                        is BreedViewModel.ScreenState.Success -> {
                            stateBinding.layoutScreenState.isVisible = false
                        }
                    }
                }
            }
        }

        // start collecting breed list data fow
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.breedData.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    private val onBreedClick: (AnimalType, String) -> Unit = { animalType, breedId ->
        val action = BreedFragmentDirections.breedToBreedDetailAction(
            animalType = animalType,
            breedId = breedId
        )

        view?.findNavController()?.navigate(action)
    }
}