package com.williamtan.animalencyclopedia.view

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class SimpleItemDecoration(
    context: Context,
    orientation: Int
) : DividerItemDecoration(context, orientation) {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        val newRect = when (position) {
            // first
            0 -> Rect(20, 0, 10, 0)

            // last
            itemCount - 1 -> Rect(10, 0, 20, 0)
            
            else -> Rect(10, 0, 10, 0)
        }

        outRect.set(newRect)
        setDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}