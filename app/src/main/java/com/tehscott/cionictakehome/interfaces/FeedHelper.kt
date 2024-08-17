package com.tehscott.cionictakehome.interfaces

import com.tehscott.cionictakehome.models.local.FeedItem
import kotlinx.coroutines.flow.Flow

interface FeedHelper {
    suspend fun getFeed(): Flow<List<FeedItem>?>
}