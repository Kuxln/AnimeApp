package com.example.animeapp.presentation.core.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getColor
import com.example.animeapp.R

class AnimeSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : SearchView(context, attrs, defStyle) {
    init {
        findViewById<TextView>(androidx.appcompat.R.id.search_src_text)?.setTextColor(Color.WHITE)
        findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)?.setColorFilter(
            getColor(
                context,
                R.color.white
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        )
        findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)?.setColorFilter(
            getColor(
                context,
                R.color.white
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        )

    }
}