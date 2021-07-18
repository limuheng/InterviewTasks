package com.muheng.media17task.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.muheng.m800task.retrofit.RetrofitClient
import com.muheng.media17task.data.GithubUser
import com.muheng.media17task.data.PageLinks
import com.muheng.media17task.data.SearchUsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class GithubUserDataSource(val q: String): PageKeyedDataSource<String, GithubUser>() {

    private val Tag = GithubUserDataSource::class.java.simpleName

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, GithubUser>) {
        Log.d(Tag, "loadInitial : q=$q")
        try {
            RetrofitClient.get().searchUsers(q).enqueue(object : Callback<SearchUsersResponse> {
                override fun onFailure(call: Call<SearchUsersResponse>, t: Throwable) {
                    // TODO use livedata to keep rest api state
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<SearchUsersResponse>, response: Response<SearchUsersResponse>) {
                    val pageLink = PageLinks(response.headers())
                    val result = response.body()
                    result?.items?.let {
                        callback.onResult(it, null, pageLink.getNext())
                    } ?: run {
                        // TODO use livedata to keep rest api state
                        Log.e(Tag, "Response of search items is null!")
                    }
                }
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, GithubUser>) {
        Log.d(Tag, "loadAfter : params.key=${params.key}")
        try {
            RetrofitClient.get().loadMoreUsers(params.key).enqueue(object : Callback<SearchUsersResponse> {
                override fun onFailure(call: Call<SearchUsersResponse>, t: Throwable) {
                    // TODO use livedata to keep rest api state
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<SearchUsersResponse>, response: Response<SearchUsersResponse>) {
                    val pageLink = PageLinks(response.headers())
                    val result = response.body()
                    result?.items?.let {
                        callback.onResult(it, pageLink.getNext())
                    } ?: run {
                        // TODO use livedata to keep rest api state
                        Log.e(Tag, "Response of search items is null!")
                    }
                }
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, GithubUser>) {
        //TODO("not implemented")
    }

}