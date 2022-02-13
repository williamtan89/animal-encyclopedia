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
    private val spacing: Int,
    private val orientation: Int
) : DividerItemDecoration(context, orientation) {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        val isFirst = position == 0
        val isLast = position == itemCount - 1
        val isHorizontal = orientation == HORIZONTAL
        val isVertical = orientation == VERTICAL

        val newRect = when {
            isHorizontal && isFirst -> {
                Rect(spacing, 0, spacing / 2, 0)
            }
            isHorizontal && isLast -> {
                Rect(spacing / 2, 0, spacing, 0)
            }
            isHorizontal -> {
                Rect(spacing / 2, 0, spacing / 2, 0)
            }

            isVertical && isFirst -> {
                Rect(0, spacing, 0, spacing / 2)
            }
            isVertical && isLast -> {
                Rect(0, spacing / 2, 0, spacing)
            }
            isVertical -> {
                Rect(0, spacing / 2, 0, spacing / 2)
            }

            else -> throw Exception("Unsupported orientation")
        }

        outRect.set(newRect)
        setDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}