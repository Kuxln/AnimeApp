package com.example.animeapp.presentation.core.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class PaddingItemDecoration(
    private val sizeVertical: Int,
    private val sizeHorizontal: Int,
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top += sizeVertical
        outRect.bottom += sizeVertical
        outRect.left += sizeHorizontal
        outRect.right += sizeHorizontal
    }
}
