package com.tehscott.cionictakehome.di

import com.tehscott.cionictakehome.FeedHelperImpl
import com.tehscott.cionictakehome.interfaces.FeedHelper
import com.tehscott.cionictakehome.network.FeedService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebServiceModule {
    @Provides
    @Singleton
    fun getFeedWebService(): FeedService =
        Retrofit.Builder()
            .baseUrl(FeedService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FeedService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedModule {
    @Binds
    abstract fun bindFeedHelper(
        feedHelperImpl: FeedHelperImpl
    ): FeedHelper
}