package com.belajar.storyapp.utils

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.belajar.storyapp.data.api.response.ListStoryItem

class QuotePagingSource : PagingSource<Int, LiveData<List<ListStoryItem>>>() {
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

    companion object {
        fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }
}