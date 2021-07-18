package com.muheng.trevitask

import android.graphics.Point
import android.widget.BaseAdapter

interface IGridViewPresenter {

    fun init(col: Int, row: Int, factory : (Int) -> Item)

    fun getCol(): Int

    fun getRow(): Int

    fun getSize(): Int

    fun getItem(pos: Int): Item

    fun getAdapter(): BaseAdapter?

    fun setSelectedPoint(x: Int, y: Int): Boolean

    fun getSelectedPoint(): Point?

    fun clearSelectedPoint()

}