package com.tehscott.cionictakehome.models.remote

data class FeedItemRemote(
    val userId: String,
    val id: String,
    val title: String,
    val body: String,
)