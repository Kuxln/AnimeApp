package com.example.animeapp.presentation.anime

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.animeapp.R
import com.example.animeapp.data.AnimeTitle
import com.example.animeapp.databinding.FragmentSelectedItemBinding
import com.example.animeapp.presentation.core.BaseFragment

class AnimeSelectedItemFragment :
    BaseFragment<FragmentSelectedItemBinding>(R.layout.fragment_selected_item) {
    private lateinit var animeTitle: AnimeTitle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentSelectedItemBinding.bind(view)

        val titleData = this.arguments
        animeTitle = titleData?.getParcelable("animeTitle")
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
        with(fragmentBinding) {
            animeCardViewTitle.text = animeTitle.canonicalTitle
            animeCardViewSubTitle.text = animeTitle.description
            animeCardViewRating.text = animeTitle.averageRating
            animeCardViewViews.text = userCountMetadata
            animeCardViewAmountOfTimeTextView.text = amountOfTimeMD
            animeCardReleaseDateTextView.text = releaseDateMD
            Glide.with(animeCardViewMainImageView.context)
                .load(animeTitle.posterImage)
                .placeholder(R.drawable.anime)
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
                .into(animeCardViewMainImageView)

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(animeTitle: AnimeTitle): AnimeSelectedItemFragment {
            val animeSelectedItemFragment = AnimeSelectedItemFragment()
            val animeTitleBundle = Bundle()
            animeTitleBundle.putParcelable("animeTitle", animeTitle)
            animeSelectedItemFragment.arguments = animeTitleBundle
            return animeSelectedItemFragment
        }
    }
}