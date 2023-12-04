package com.example.animeapp.presentation.anime

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.animeapp.R
import com.example.animeapp.data.AnimeTitle
import com.example.animeapp.databinding.ExpandableToolbarBinding
import com.example.animeapp.presentation.core.BaseFragment

class AnimeSelectedItemFragment :
    BaseFragment<ExpandableToolbarBinding>(R.layout.expandable_toolbar) {
    private lateinit var animeTitle: AnimeTitle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ExpandableToolbarBinding.bind(view)

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
        with(binding) {
            animeCardViewTitle.text = animeTitle.canonicalTitle
            animeCardViewSubTitle.text = animeTitle.description
//            animeCardViewRating.text = animeTitle.averageRating
//            animeCardViewViews.text = userCountMetadata
            animeCardViewAmountOfTimeTextView.text = amountOfTimeMD
            animeCardReleaseDateTextView.text = releaseDateMD
            Glide.with(animeCardViewMainImageView.context)
                .load(animeTitle.posterImage)
                .placeholder(R.drawable.anime)
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
                .into(animeCardViewMainImageView)
//            Glide.with(animeCardViewMainImageView1.context)
//                .load(animeTitle.posterImage)
//                .placeholder(R.drawable.anime)
//                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
//                .into(animeCardViewMainImageView1)

        }
//        fragmentBinding.collapsingToolbarLayout.title = "Test Title"
//        fragmentBinding.collapsingToolbarLayout.setCollapsedTitleTextAppearance(com.example.animeapp.R.style.authorizationSubTitle)
//        fragmentBinding.collapsingToolbarLayout.setExpandedTitleTextAppearance(com.example.animeapp.R.style.authorizationSubTitle)
//        fragmentBinding.collapsingToolbarLayout.setContentScrimColor(Color.GREEN)
    }

    override fun onBack(): Boolean {
        return false
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