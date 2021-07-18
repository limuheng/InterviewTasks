package com.muheng.media17task.data

import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<GithubUser>
)

data class GithubUser(
    @SerializedName("login")
    val login: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("gravatar_id")
    val gravatarId: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,
    @SerializedName("organizations_url")
    val organizationsUrl: String,
    @SerializedName("repos_url")
    val reposUrl: String,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String,
    @SerializedName("type")
    val type: String = "",
    @SerializedName("score")
    val score: Float = 0.0f
)
