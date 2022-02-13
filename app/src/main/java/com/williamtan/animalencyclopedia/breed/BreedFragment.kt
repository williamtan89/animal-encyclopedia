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
import com.williamtan.animalencyclopedia.adapter.BreedAdapter
import com.williamtan.animalencyclopedia.databinding.FragmentBreedBinding
import com.williamtan.animalencyclopedia.util.ConvertUtil
import com.williamtan.animalencyclopedia.view.GridItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BreedFragment : Fragment() {
    private val viewModel: BreedViewModel by viewModels()
    private val args: BreedFragmentArgs by navArgs()
    
    private lateinit var binding: FragmentBreedBinding
    private lateinit var adapter: BreedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedBinding.inflate(inflater)

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

        adapter = BreedAdapter()

        binding.rvBreed.apply {
            adapter = this@BreedFragment.adapter
            context?.let { addItemDecoration(GridItemDecoration(ConvertUtil.dpToPx(it, 8))) }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (
                        !recyclerView.canScrollVertically(1) &&
                        newState == RecyclerView.SCROLL_STATE_IDLE
                    ) {
                        if (viewModel.searchQuery.value.isNullOrBlank()) {
                            lifecycleScope.launch {
                                viewModel.loadBreeds(args.animalType)
                            }
                        }
                    }
                }
            })
        }

        binding.svBreed.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    viewModel.updateSearchQuery(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    viewModel.updateSearchQuery(newText)
                }

                return true
            }
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is BreedViewModel.ScreenState.Empty -> {
                            binding.layoutEmptyState.isVisible = true
                            binding.layoutErrorState.isVisible = false
                        }

                        is BreedViewModel.ScreenState.Error -> {
                            binding.layoutEmptyState.isVisible = false
                            binding.layoutErrorState.isVisible = true
                        }

                        else -> {
                            binding.layoutEmptyState.isVisible = false
                            binding.layoutErrorState.isVisible = false
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.breedEntityData.collect {
                    adapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchQuery.onEach {
                    viewModel.loadBreeds(args.animalType)
                }.debounce(250).shareIn(this, SharingStarted.Eagerly)
            }
        }
    }
}