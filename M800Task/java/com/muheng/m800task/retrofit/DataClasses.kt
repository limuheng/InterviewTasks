package com.muheng.m800task.retrofit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Muheng Li on 2018/8/15.
 */

data class ResponseResult(
        val resultCount: Int = 0,
        val results: List<Item>
)

@Parcelize
data class Item(
        val wrapperType: String = "",
        val kind: String = "",
        val artistId: String = "",
        val collectionId: String = "",
        val trackId: String = "",
        val artistName: String = "",
        val collectionName: String = "",
        val trackName: String = "",
        val previewUrl: String = "",
        val artworkUrl100: String = "",
        val trackPrice: String = "",
        val currency: String = ""
) : Parcelable
