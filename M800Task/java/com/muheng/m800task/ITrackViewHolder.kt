package com.muheng.m800task

import com.muheng.m800task.retrofit.Item

/**
 * Created by Muheng Li on 2018/8/16.
 */
interface ITrackViewHolder {
    fun onTrackDisplayed(item: Item)
    fun onTrackClosed()
}