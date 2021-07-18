package com.muheng.media17task.network

import com.muheng.media17task.data.SearchUsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubApiService {

    companion object {
        const val API_BASE_RUL = "https://api.github.com"

        // Node
        const val SEARCH_USERS = "/search/users"

        // Parameter Keys
        const val QUERY = "q"
    }

    @GET(SEARCH_USERS)
    fun searchUsers(@Query(QUERY) q: String): Call<SearchUsersResponse>

    @GET
    fun loadMoreUsers(@Url url: String): Call<SearchUsersResponse>

}



