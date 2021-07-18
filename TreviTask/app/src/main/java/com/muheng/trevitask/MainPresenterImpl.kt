package com.muheng.trevitask

import android.content.Context
import android.graphics.Point
import android.widget.BaseAdapter
import kotlin.random.Random

class MainPresenterImpl(private val context: Context): IMainPresenter {

    private var col: Int = DEFAULT_VAL
    private  var row: Int = DEFAULT_VAL

    private val gridViewPresenter: IGridViewPresenter = GridViewPresenterImpl(context)

    override fun setCol(col: Int) {
        if (col > 0) {
            this.col = col
        }
    }

    override fun getCol(): Int {
        return col
    }

    override fun setRow(row: Int) {
        if (row > 0) {
            this.row = row
        }
    }

    override fun getRow(): Int {
        return row
    }

    override fun createAdapter(): BaseAdapter? {
        val size = col * row
        gridViewPresenter.init(col, row) { index ->
            Item(false, index >= size)
        }
        return gridViewPresenter.getAdapter()
    }

    override fun selectPoint(point: Point): Boolean {
        return gridViewPresenter.setSelectedPoint(point.x, point.y)
    }

    override fun genRandomPoint(): Point {
        val x = Random.nextInt(0, col)
        val y = Random.nextInt(0, row)
        return Point(x, y)
    }

}