package com.williamtan.animalencyclopedia.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.williamtan.animalencyclopedia.promoted.PromotedFragment

class HomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PromotedFragment()
            1 -> PromotedFragment()
            else -> throw Exception("Unknown fragment position!")
        }
    }
}
