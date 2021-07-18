package com.muheng.m800task

import com.muheng.m800task.retrofit.Item

/**
 * Created by Muheng Li on 2018/8/15.
 */

interface IMainView {
    fun onSearchResult(results: List<Item>)
    fun onNoData()
    fun onError(message: String?)
    fun onItemClicked(item: Item)
}