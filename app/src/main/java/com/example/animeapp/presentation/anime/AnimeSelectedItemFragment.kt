package com.example.animeapp.presentation.anime

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.example.animeapp.R
import com.example.animeapp.data.AnimeTitleData
import com.example.animeapp.databinding.FragmentAnimeSelectedBinding
import com.example.animeapp.presentation.core.BaseFragment
import com.example.animeapp.presentation.core.ui.FragmentAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.round
import kotlin.math.roundToInt


class AnimeSelectedItemFragment :
    BaseFragment<FragmentAnimeSelectedBinding>(R.layout.fragment_anime_selected) {
    private lateinit var animeTitleData: AnimeTitleData
    private lateinit var adapter: FragmentAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAnimeSelectedBinding.bind(view)


        binding.root.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            val coordinatorViewHeight = binding.root.height
            val appBarHeight = binding.appBar.height
            val viewPagerHeight = coordinatorViewHeight - appBarHeight
            val layoutParams = LinearLayoutCompat.LayoutParams(binding.viewPager.layoutParams.width, viewPagerHeight)
            binding.viewPager.layoutParams = layoutParams
        }




        val titleData = this.arguments
        val animeTitle = titleData?.getParcelable<AnimeTitleData>("animeTitle")?.attributes
            ?: throw IllegalArgumentException("AnimeTitle is required for fragment AnimeSelectedItemFragment")

        val userCountMetadata = if (animeTitle.userCount != null) {
            "(${animeTitle.userCount} Views)"
        } else ""

        val episodeCountMetadata = if (animeTitle.episodeCount != null) {
            "${animeTitle.episodeCount} ep."
        } else ""


        val episodeLengthMetadata = if (animeTitle.episodeLength != null) {
            "• ${animeTitle.episodeLength} min"
        } else ""

        val animeStartDate = animeTitle.startDate.orEmpty()

        val endDateMetadata = if (animeTitle.endDate != null) {
            "-> ${animeTitle.endDate}"
        } else "-> ongoing"

        val amountOfTimeMD = "$episodeCountMetadata $episodeLengthMetadata"
        val releaseDateMD = "$animeStartDate $endDateMetadata"
        with(binding) {
            animeCardViewTitle.text = animeTitle.canonicalTitle
            animeCardViewSubTitle.text = animeTitle.description
//            animeCardViewRating.text = animeTitle.averageRating
//            animeCardViewViews.text = userCountMetadata
            animeCardViewAmountOfTimeTextView.text = amountOfTimeMD
            animeCardReleaseDateTextView.text = releaseDateMD
            Glide.with(requireContext())
                .load(animeTitle.posterImage?.original)
                .placeholder(R.drawable.anime)
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
                .into(animeCardViewMainImageView)
        }

        adapter = FragmentAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "profile"
                1 -> tab.text = "reels"
                2 -> tab.text = "profile"
            }
        }.attach()


    }

    override fun onBack(): Boolean {
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance(animeTitle: AnimeTitleData): AnimeSelectedItemFragment {
            val animeSelectedItemFragment = AnimeSelectedItemFragment()
            val animeTitleBundle = Bundle()
            animeTitleBundle.putParcelable("animeTitle", animeTitle)
            animeSelectedItemFragment.arguments = animeTitleBundle
            return animeSelectedItemFragment
        }
    }
}