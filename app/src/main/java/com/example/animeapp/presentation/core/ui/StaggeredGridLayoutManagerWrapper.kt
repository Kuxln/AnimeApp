package com.example.animeapp.presentation.core.ui

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.IndexOutOfBoundsException

class StaggeredGridLayoutManagerWrapper(
    spanCount: Int,
    orientation: Int
) : StaggeredGridLayoutManager(spanCount, orientation) {
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("TAG", "IOOBE in recyclerView")
        }
    }
}