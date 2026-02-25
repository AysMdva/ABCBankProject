@file:OptIn(ExperimentalMaterial3Api::class)
package com.abcbank.carousel.presentation.compose.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abcbank.carousel.domain.model.PageStatistics
import com.abcbank.carousel.util.toDisplayText

@Composable
fun StatisticsBottomSheet(
    statistics: List<PageStatistics>,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            items(statistics, key = { it.pageNumber }) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "List ${item.pageNumber}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${item.itemCount} items",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = item.topCharacters.toDisplayText(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
