package com.tehscott.cionictakehome

import com.tehscott.cionictakehome.interfaces.FeedHelper
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val feedHelper: FeedHelper
) {
    suspend fun getFeed() = feedHelper.getFeed()
}