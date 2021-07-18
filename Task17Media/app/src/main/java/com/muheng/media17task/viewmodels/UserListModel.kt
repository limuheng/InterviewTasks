package com.muheng.media17task.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.muheng.media17task.data.GithubUser
import com.muheng.media17task.datasource.GithubUserDataFactory
import java.util.concurrent.Executors

class UserListModel: ViewModel() {

    private val Tag = UserListModel::class.java.simpleName

    private val executor = Executors.newFixedThreadPool(5)
    private var data: LiveData<PagedList<GithubUser>>? = null
    private var pagedListConfig: PagedList.Config = PagedList.Config.Builder()
                                                        .setEnablePlaceholders(false)
                                                        .setInitialLoadSizeHint(10)
                                                        .setPageSize(30).build()

    init {
        data = LivePagedListBuilder(GithubUserDataFactory(""), pagedListConfig)
            .setFetchExecutor(executor).build()
    }

    fun getUsers(): LiveData<PagedList<GithubUser>>? {
        return data
    }

    fun setQueryString(q: String) {
        if (q.isNotBlank()) {
            data = LivePagedListBuilder(GithubUserDataFactory(q), pagedListConfig)
                .setFetchExecutor(executor).build()
        }
    }

}