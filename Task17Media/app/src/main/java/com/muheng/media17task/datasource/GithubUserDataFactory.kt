package com.muheng.media17task.datasource

import androidx.paging.DataSource
import com.muheng.media17task.data.GithubUser

class GithubUserDataFactory(val q: String): DataSource.Factory<String, GithubUser>() {

    private lateinit var dataSource: GithubUserDataSource

    override fun create(): DataSource<String, GithubUser> {
        dataSource = GithubUserDataSource(q)
        return dataSource
    }

}