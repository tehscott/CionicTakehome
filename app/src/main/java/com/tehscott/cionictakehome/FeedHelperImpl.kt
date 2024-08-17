package com.tehscott.cionictakehome

import com.tehscott.cionictakehome.interfaces.FeedHelper
import com.tehscott.cionictakehome.models.local.FeedItem
import com.tehscott.cionictakehome.network.FeedService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FeedHelperImpl @Inject constructor(
    private val feedService: FeedService
) : FeedHelper {
    override suspend fun getFeed(): Flow<List<FeedItem>?> {
        var feed: List<FeedItem>? = null

        runCatching {
            feedService.getFeed().execute().let { response ->
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        runCatching {
                            feed = responseBody.map { feedItem ->
                                FeedItem(
                                    userId = feedItem.userId,
                                    id = feedItem.id,
                                    title = feedItem.title,
                                    body = feedItem.body,
                                )
                            }
                        }
                    }
                }
            }
        }.getOrNull()

        return flowOf(feed)
    }
}