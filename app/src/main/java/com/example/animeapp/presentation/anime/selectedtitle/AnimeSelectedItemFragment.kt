package com.example.animeapp.presentation.anime.selectedtitle

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.example.animeapp.R
import com.example.animeapp.data.AnimeTitleData
import com.example.animeapp.databinding.FragmentAnimeSelectedBinding
import com.example.animeapp.presentation.core.ui.BaseFragment
import com.example.animeapp.presentation.core.ui.FragmentAdapter

class AnimeSelectedItemFragment :
    BaseFragment<FragmentAnimeSelectedBinding>(R.layout.fragment_anime_selected) {
    private var currentlyTouching: Boolean = false
    private var currentlyScrolling: Boolean = false
    private var scrollPerformed: Boolean = false

    private lateinit var animeTitleData: AnimeTitleData
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var anotherBinding: FragmentAnimeSelectedBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAnimeSelectedBinding.bind(view)
        anotherBinding = binding;
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

        val animeTitleID = titleData?.getParcelable<AnimeTitleData>("animeTitle")?.id
            ?: throw IllegalArgumentException("AnimeTitle is required for fragment AnimeSelectedItemFragment")
        binding.pager.adapter = FragmentAdapter(this, animeTitleID)
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