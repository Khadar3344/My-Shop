package com.khadar3344.myshop.ui.home.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CategoryTabRow(
    pagerState: PagerState,
    categories: List<String>,
    onTabSelected:(Int) -> Unit
) {

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { onTabSelected(index) },
                content =  {
                    Text(
                        text = category,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
                    )
                }
            )
        }
    }
}