package com.muheng.trevitask

import android.graphics.Point
import android.widget.BaseAdapter

const val DEFAULT_VAL = 3

interface IMainPresenter {

    fun setCol(col: Int = DEFAULT_VAL)
    fun getCol(): Int

    fun setRow(row: Int = DEFAULT_VAL)
    fun getRow(): Int

    fun createAdapter(): BaseAdapter?

    fun selectPoint(point: Point): Boolean

    fun genRandomPoint(): Point

}