package com.abcbank.carousel.presentation.xml

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.abcbank.carousel.R
import com.abcbank.carousel.databinding.ActivityMainXmlBinding
import com.abcbank.carousel.presentation.ABCMainViewModel
import com.abcbank.carousel.presentation.compose.state.CarouselScreenState
import com.abcbank.carousel.presentation.xml.adapter.ImageCarouselAdapter
import com.abcbank.carousel.presentation.xml.adapter.ListItemAdapter
import kotlinx.coroutines.launch

class ABCMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainXmlBinding
    private val viewModel: ABCMainViewModel by viewModels()

    private val listAdapter = ListItemAdapter()
    private val imageAdapter = ImageCarouselAdapter()

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            viewModel.onPageChanged(position)
            updatePageIndicators(position)
            binding.recyclerViewItems.scrollToPosition(0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainXmlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupCarousel()
        setupSearch()
        setupFab()
        observeState()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewItems.apply {
            layoutManager = LinearLayoutManager(this@ABCMainActivity)
            adapter = listAdapter
        }
    }

    private fun setupCarousel() {
        binding.viewPagerCarousel.adapter = imageAdapter
        binding.viewPagerCarousel.registerOnPageChangeCallback(pageChangeCallback)
    }

    private fun setupSearch() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                viewModel.onSearchQueryChanged(s?.toString().orEmpty())
            }
        })
    }

    private fun setupFab() {
        binding.fabStatistics.setOnClickListener {
            StatisticsBottomSheet(viewModel.getStatistics()).show(
                supportFragmentManager,
                StatisticsBottomSheet.TAG
            )
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect(::renderState)
            }
        }
    }

    private fun renderState(state: CarouselScreenState) {
        val images = state.pages.map { it.imageRes }
        if (imageAdapter.currentList != images) imageAdapter.submitList(images)

        if (binding.layoutPageIndicators.childCount != state.pages.size) {
            createIndicators(state.pages.size)
        }

        updatePageIndicators(state.currentPage)

        if (binding.viewPagerCarousel.currentItem != state.currentPage) {
            binding.viewPagerCarousel.setCurrentItem(state.currentPage, false)
        }

        listAdapter.submitList(state.filteredItems)
    }

    private fun createIndicators(count: Int) {
        val dotSize = resources.getDimensionPixelSize(R.dimen.indicator_dot_size)
        val dotMargin = resources.getDimensionPixelSize(R.dimen.indicator_dot_margin)

        binding.layoutPageIndicators.removeAllViews()
        repeat(count) {
            val dot = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(dotSize, dotSize).apply {
                    marginStart = dotMargin
                    marginEnd = dotMargin
                }
                background = indicatorDrawable(false)
            }
            binding.layoutPageIndicators.addView(dot)
        }
    }

    private fun updatePageIndicators(selectedIndex: Int) {
        binding.layoutPageIndicators.children.forEachIndexed { index, view ->
            view.background = indicatorDrawable(index == selectedIndex)
        }
    }

    private fun indicatorDrawable(selected: Boolean) = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setColor(ContextCompat.getColor(
            this@ABCMainActivity,
            if (selected) R.color.primaryBlue else R.color.textSecondary
        ))
    }

    override fun onDestroy() {
        binding.viewPagerCarousel.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onDestroy()
    }
}