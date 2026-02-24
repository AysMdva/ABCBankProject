package com.abcbank.carousel.presentation.xml.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abcbank.carousel.R
import com.abcbank.carousel.databinding.ItemStatisticBinding
import com.abcbank.carousel.domain.model.PageStatistics
import com.abcbank.carousel.util.toDisplayText

class StatisticsAdapter : ListAdapter<PageStatistics, StatisticsAdapter.StatisticsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        val binding = ItemStatisticBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StatisticsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class StatisticsViewHolder(
        private val binding: ItemStatisticBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PageStatistics) = with(binding) {
            val rootContext = root.context
            textViewPageName.text = rootContext.getString(R.string.page_label, item.pageNumber)
            textViewItemCount.text = rootContext.getString(R.string.item_count, item.itemCount)
            textViewTopCharacters.text = item.topCharacters.toDisplayText()
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<PageStatistics>() {
        override fun areItemsTheSame(old: PageStatistics, new: PageStatistics) = old.pageNumber == new.pageNumber
        override fun areContentsTheSame(old: PageStatistics, new: PageStatistics) = old == new
    }
}