package com.muheng.m800task

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.muheng.m800task.retrofit.IiTuneSearchApi
import com.muheng.m800task.retrofit.Item
import com.muheng.m800task.rxjava.Event
import com.muheng.rxjava.RxEventBus

/**
 * Created by Muheng Li on 2018/8/15.
 */

class MainFragment : Fragment(), IMainView {

    companion object {
        const val TAG: String = "MainFragment"
        const val SPAN_COUNT = 3
    }

    private lateinit var mAdapter: ItemListAdapter

    private var mList: RecyclerView? = null
    private var mNoData: TextView? = null

    private lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter = MainPresenter(this)
        mPresenter.init()
    }

    override fun onDestroy() {
        mPresenter.dispose()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)

        mList = rootView?.findViewById(R.id.list)
        mNoData = rootView?.findViewById(R.id.no_data)

        mList?.layoutManager = GridLayoutManager(context, SPAN_COUNT)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = ItemListAdapter()
        mList?.adapter = mAdapter

        mPresenter.searchMusics()
    }

    override fun onSearchResult(results: List<Item>) {
        mNoData?.visibility = View.GONE
        mList?.visibility = View.VISIBLE

        mAdapter.data.clear()
        mAdapter.data.addAll(results)
        mAdapter.notifyDataSetChanged()
    }

    override fun onNoData() {
        mNoData?.visibility = View.VISIBLE
        mList?.visibility = View.GONE
    }

    override fun onError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(item: Item) {
        if (IiTuneSearchApi.TRACK.equals(item.wrapperType)) {
            //Toast.makeText(context, "${item.trackName} by ${item.artistName}", Toast.LENGTH_SHORT).show()
            RxEventBus.get()?.send(Event.ShowTrackView(item))
        } else {
            Toast.makeText(context, "Not a track!", Toast.LENGTH_SHORT).show()
        }
    }
}