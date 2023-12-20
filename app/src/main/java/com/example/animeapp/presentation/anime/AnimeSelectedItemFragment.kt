package com.example.animeapp.presentation.anime

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.example.animeapp.R
import com.example.animeapp.data.AnimeTitleData
import com.example.animeapp.databinding.FragmentAnimeSelectedBinding
import com.example.animeapp.presentation.core.BaseFragment

class AnimeSelectedItemFragment :
    BaseFragment<FragmentAnimeSelectedBinding>(R.layout.fragment_anime_selected) {
    private lateinit var animeTitleData: AnimeTitleData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAnimeSelectedBinding.bind(view)

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

        binding.root.doOnLayout {
            val rootHeight = binding.root.measuredHeight;
            val tabsHeight = binding.pagerTabs.measuredHeight;
            binding.pager.updateLayoutParams {
                height = rootHeight - tabsHeight
            }
        }
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