@file:OptIn(ExperimentalFoundationApi::class)
package com.abcbank.carousel.presentation.compose.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abcbank.carousel.domain.model.ListItem

@Composable
fun ItemList(
    items: List<ListItem>,
    listState: LazyListState = rememberLazyListState(),
    topContent: (@Composable () -> Unit)? = null,
    stickySearchContent: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        if (topContent != null) {
            item {
                topContent()
            }
        }
        if (stickySearchContent != null) {
            stickyHeader {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    stickySearchContent()
                }
            }
        }
        items(
            items = items,
            key = { it.id }
        ) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = item.thumbnailRes),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
