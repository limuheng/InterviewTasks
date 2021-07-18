package com.muheng.trevitask

import android.content.Context
import android.graphics.Point
import android.widget.BaseAdapter
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class GridViewPresenterImpl(private val context: Context): IGridViewPresenter {

    private var adapter: GridViewAdapter? = null

    private var col: Int = DEFAULT_VAL
    private var row: Int = DEFAULT_VAL
    private val data: MutableList<Item> = mutableListOf()

    private var selectedPoint: Point? = null

    private val locker = ReentrantLock()

    override fun init(col: Int, row: Int, factory : (Int) -> Item) {
        this.col = col
        this.row = row

        data.clear()
        val size = col * (row + 1)
        for (i in 0 until size) {
            data.add(factory(i))
        }

        if (adapter == null) {
            adapter = GridViewAdapter(context, this)
        }

        adapter?.notifyDataSetChanged()
    }

    override fun getCol(): Int {
        return col
    }

    override fun getRow(): Int {
        return row
    }

    override fun getSize(): Int {
        return data.size
    }

    override fun getItem(pos: Int): Item {
        return data[pos]
    }

    override fun getAdapter(): BaseAdapter? {
        return adapter
    }

    override fun setSelectedPoint(x: Int, y: Int): Boolean {
        locker.withLock {
            return if (x in 0 until col && y in 0 until row) {
                clearSelectedPoint()
                selectedPoint = Point(x, y)
                data[y * col + x].isSelected = true
                adapter?.notifyDataSetChanged()
                true
            } else {
                selectedPoint = null
                false
            }
        }
    }

    override fun getSelectedPoint(): Point? {
        return selectedPoint
    }

    override fun clearSelectedPoint() {
        locker.withLock {
            if (selectedPoint != null) {
                data[selectedPoint!!.y * col + selectedPoint!!.x].isSelected = false
                selectedPoint = null
                adapter?.notifyDataSetChanged()
            }
        }
    }
}