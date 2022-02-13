package com.williamtan.animalencyclopedia.breeddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.databinding.FragmentBreedDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BreedDetailFragment : Fragment() {
    private val viewModel: BreedDetailViewModel by viewModels()
    private val args: BreedDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentBreedDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedDetailBinding.inflate(inflater)

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
    }
}