package com.abcbank.carousel.presentation.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcbank.carousel.databinding.BottomSheetStatisticsBinding
import com.abcbank.carousel.domain.model.PageStatistics
import com.abcbank.carousel.presentation.xml.adapter.StatisticsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StatisticsBottomSheet(
    private val statistics: List<PageStatistics>
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetStatisticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewStatistics.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = StatisticsAdapter().also { it.submitList(statistics) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "statistics_sheet"
    }
}
