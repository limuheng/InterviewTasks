package com.muheng.trevitask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

const val KEY_TAG_COL = 1234567890

class GridViewAdapter(private val context: Context, private val gridViewPresenter: IGridViewPresenter): BaseAdapter() {

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.gridview_item, null)

        val item = getItem(pos)

        val tv: TextView = view.findViewById(R.id.text)
        if (item.isSelected) {
            tv.visibility = View.VISIBLE
        } else {
            tv.visibility = View.INVISIBLE
        }

        val btn: Button = view.findViewById(R.id.btn)
        if (item.isBtn) {
            btn.visibility = View.VISIBLE
            tv.visibility = View.GONE
            btn.setTag(KEY_TAG_COL, pos % gridViewPresenter.getCol())
            btn.setOnClickListener { view ->
                val tag: Int = btn.getTag(KEY_TAG_COL) as? Int ?: -1
                if (tag == gridViewPresenter?.getSelectedPoint()?.x) {
                    gridViewPresenter.clearSelectedPoint()
                }
            }
        } else {
            btn.visibility = View.GONE
        }

        gridViewPresenter.getSelectedPoint()?.let {
            view.isSelected = pos % gridViewPresenter.getCol() == it.x

            // Since selector not works here, we manually change the background
            view.background = if (view.isSelected) {
                context.getDrawable(R.drawable.gridview_bg_border_s)
            } else {
                context.getDrawable(R.drawable.gridview_bg_border_n)
            }
        } ?: run {
            view.isSelected = false
            view.background = context.getDrawable(R.drawable.gridview_bg_border_n)
        }

        // Set item height to make GridView full of parent
        if (parent != null) {
            val layoutParams = view.layoutParams ?: LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.height = parent.measuredHeight / (gridViewPresenter.getRow() + 1)
            view.layoutParams = layoutParams
        }

        return view
    }

    override fun getItem(pos: Int): Item {
        return gridViewPresenter.getItem(pos)
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getCount(): Int {
        return gridViewPresenter.getSize()
    }

}