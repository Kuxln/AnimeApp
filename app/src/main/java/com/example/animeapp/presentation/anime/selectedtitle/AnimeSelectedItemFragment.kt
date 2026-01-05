package com.example.animeapp.presentation.anime.selectedtitle

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentAnimeSelectedBinding
import com.example.animeapp.domain.entity.AnimeTitleEntity
import com.example.animeapp.presentation.core.ui.BaseFragment
import com.example.animeapp.presentation.core.ui.FragmentAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeSelectedItemFragment :
    BaseFragment<FragmentAnimeSelectedBinding>(R.layout.fragment_anime_selected) {
    private var currentlyTouching: Boolean = false
    private var currentlyScrolling: Boolean = false
    private var scrollPerformed: Boolean = false

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var anotherBinding: FragmentAnimeSelectedBinding

    private val viewModel: AnimeSelectedItemViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAnimeSelectedBinding.bind(view)
        anotherBinding = binding
        val id = this.arguments?.getParcelable<AnimeTitleEntity>("animeTitle")?.id
        viewModel.setId(id)

        viewModel.liveData.observe(this.viewLifecycleOwner) { state ->
            val attributes = state.title?.data?.attributes
            attributes?.let {
                val userCountMetadata =
                    if (attributes.userCount != null && attributes.userCount > 5000)
                        "(" + (attributes.userCount / 1000).toString() + "k+ Views)"
                    else if (attributes.userCount != null) "(${attributes.userCount} Views)"
                    else ""

                val episodeCountMetadata = if (attributes.episodeCount != null) {
                    "${attributes.episodeCount} ep."
                } else ""

                val animeRatingMetadata = attributes.averageRating.orEmpty()

                val episodeLengthMetadata = if (attributes.episodeLength != null) {
                    "• ${attributes.episodeLength} min"
                } else ""

                val animeStartDate = attributes.startDate.orEmpty()

                val endDateMetadata = if (attributes.endDate != null) {
                    "-> ${attributes.endDate}"
                } else "-> ongoing"

                val amountOfTimeMD = "$episodeCountMetadata $episodeLengthMetadata"
                val releaseDateMD = "$animeStartDate $endDateMetadata"
                with(binding) {
                    animeCardViewTitle.text = attributes.canonicalTitle
                    animeCardViewSubTitle.text = attributes.description
                    animeCardViewAmountOfTimeTextView.text = amountOfTimeMD
                    animeCardReleaseDateTextView.text = releaseDateMD
                    animeCardViewRating.text = animeRatingMetadata
                    animeCardViewViews.text = userCountMetadata
                    Glide.with(requireContext())
                        .load(attributes.posterImage?.original)
                        .placeholder(R.drawable.anime)
                        .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
                        .into(animeCardViewMainImageView)
                }
            }
            if (state.isLoading) {
                binding.scrollContent.visibility = View.INVISIBLE
                binding.appBar.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.scrollContent.visibility = View.VISIBLE
                binding.appBar.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        binding.root.doOnLayout {
            val rootHeight = binding.root.measuredHeight
            val tabsHeight = binding.pagerTabs.measuredHeight
            binding.pager.updateLayoutParams {
                height = rootHeight - tabsHeight
            }
        }

        binding.scrollContent.setOnScrollChangeListener { view, x, scrollY, oldX, oldScrollY ->
            currentlyScrolling = true
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed({
                scrollPerformed = true
                currentlyScrolling = false
                onScrolled()
            }, 50)
        }

        binding.scrollContent.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    currentlyTouching = false
                    onScrolled()
                }

                MotionEvent.ACTION_DOWN -> currentlyTouching = true
            }
            return@setOnTouchListener false
        }

        binding.pager.adapter = id?.let { FragmentAdapter(this, it) }
        TabLayoutMediator(
            binding.pagerTabs, binding.pager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Episodes"
                1 -> tab.text = "Characters"
                2 -> tab.text = "Reviews"
            }
        }.attach()
    }

    private fun onScrolled() {
        if (!scrollPerformed || currentlyTouching || currentlyScrolling || binding.scrollContent.scrollY == 0) {
            return
        }
        scrollPerformed = false
        System.err.println("On scrolled!!!! \"${binding.scrollContent.scrollY}\"")
        if ((anotherBinding.scrollContent.scrollY * 100) / anotherBinding.staticContent.height > 50) {
            binding.scrollContent.smoothScrollTo(0, anotherBinding.staticContent.height, 200)
        } else {
            binding.scrollContent.smoothScrollTo(0, 0, 200)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(animeTitle: AnimeTitleEntity): AnimeSelectedItemFragment {
            val animeSelectedItemFragment = AnimeSelectedItemFragment()
            val animeTitleBundle = Bundle()
            animeTitleBundle.putParcelable("animeTitle", animeTitle)
            animeSelectedItemFragment.arguments = animeTitleBundle
            return animeSelectedItemFragment
        }
    }
}
