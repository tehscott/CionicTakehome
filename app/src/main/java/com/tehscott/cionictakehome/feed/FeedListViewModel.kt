package com.tehscott.cionictakehome.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tehscott.cionictakehome.FeedRepository
import com.tehscott.cionictakehome.models.local.FeedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedListViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {
    val feed: MutableLiveData<List<FeedItem>?> by lazy {
        MutableLiveData<List<FeedItem>?>()
    }

    fun fetchFeed(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) = CoroutineScope(dispatcher).launch {
        feedRepository.getFeed()
            .onEach { value ->
                val filtered = value.orEmpty().filter {
                    it.title.contains(DEFAULT_FILTER) || it.body.contains(DEFAULT_FILTER)
                }
                feed.postValue(filtered)
            }
            .collect()
    }

    companion object {
        private const val DEFAULT_FILTER = "optio"
    }
}