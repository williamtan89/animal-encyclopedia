package com.williamtan.animalencyclopedia.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.williamtan.animalencyclopedia.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPagerAdapter: HomeViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerAdapter = HomeViewPagerAdapter(this)

        binding.vpHome.apply {
            adapter = viewPagerAdapter
            isUserInputEnabled = false
        }

        TabLayoutMediator(binding.tbHome, binding.vpHome) { tab, position ->
            val tabText = when (position) {
                0 -> "Home"
                1 -> "Favorite"
                else -> throw Exception("Unknown tab position")
            }

            tab.text = tabText
        }.attach()
    }
}