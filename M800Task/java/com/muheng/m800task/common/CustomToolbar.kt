package com.muheng.m800task.common

import android.view.View
import android.widget.SearchView
import android.widget.TextView
import com.muheng.m800task.R
import com.muheng.m800task.rxjava.Event
import com.muheng.rxjava.RxEventBus

/**
 * Created by Muheng Li on 2018/8/16.
 */
class CustomToolbar {
    lateinit var primary: TextView
    lateinit var secondary: TextView
    lateinit var searchView: SearchView

    fun init(root: View) {
        primary = root.findViewById(R.id.primary)
        secondary = root.findViewById(R.id.secondary)
        searchView = root.findViewById(R.id.search_view)
        searchView?.setOnQueryTextListener(onQueryTextListener)
    }

    private var onQueryTextListener: SearchView.OnQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean { return true }

        override fun onQueryTextSubmit(query: String?): Boolean {
            RxEventBus.get()?.send(Event.QueryChanged(query))
            return true
        }
    }

}