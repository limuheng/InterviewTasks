package com.muheng.m800task

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.muheng.m800task.common.CustomToolbar
import com.muheng.m800task.retrofit.Item
import com.muheng.m800task.rxjava.Event
import com.muheng.rxjava.RxEventBus

/**
 * Created by Muheng Li on 2018/8/15.
 */

class MainActivity : AppCompatActivity(), IToolbarHolder, ITrackViewHolder {

    private var mFragment = MainFragment()
    private var mTarckFragment = TrackViewFragment()

    private lateinit var mToolbarPresenter: ToolbarPresenter
    private lateinit var mCustomToolbar: CustomToolbar

    private lateinit var mTrackDisplayPresenter: TrackDisplayPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val transactin = supportFragmentManager.beginTransaction()
            transactin.add(R.id.frame_container, mFragment, MainFragment.TAG)
            transactin.add(R.id.frame_container, mTarckFragment, TrackViewFragment.TAG)
            transactin.hide(mTarckFragment)
            transactin.commit()

        } else {
            mFragment = supportFragmentManager.findFragmentByTag(MainFragment.TAG) as MainFragment
            mTarckFragment = supportFragmentManager.findFragmentByTag(TrackViewFragment.TAG) as TrackViewFragment
        }

        initToolbar()

        mToolbarPresenter = ToolbarPresenter(this)
        mToolbarPresenter.init()

        mTrackDisplayPresenter = TrackDisplayPresenter(this)
        mTrackDisplayPresenter.init()
    }

    override fun onDestroy() {
        mToolbarPresenter.dispose()
        mTrackDisplayPresenter.dispose()
        super.onDestroy()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        mCustomToolbar = CustomToolbar()
        mCustomToolbar.init(toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onUpdatePrimaryTitle(text: String?) {
        if (text != null) {
            mCustomToolbar.primary.text = text
        }
    }

    override fun onUpdateSecondaryTitle(text: String?) {
        if (text != null) {
            mCustomToolbar.secondary.text = "${getString(R.string.secondary_title)} $text"
            mCustomToolbar.secondary.visibility = View.VISIBLE
        } else {
            mCustomToolbar.secondary.visibility = View.GONE
        }
    }

    override fun onQueryTriggered(text: String?) {
        if (text != null) {
            RxEventBus.get()?.send(Event.Search(text))
        }
    }

    override fun onTrackDisplayed(item: Item) {
        if (mTarckFragment.isHidden) {
            RxEventBus.get()?.send(Event.DisplayTrack(item))
            val transaction = supportFragmentManager.beginTransaction()
            //transaction.hide(mFragment)
            transaction.show(mTarckFragment)
            transaction.commit()
        }
    }

    override fun onTrackClosed() {
        if (!mTarckFragment.isHidden) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.hide(mTarckFragment)
            //transaction.show(mFragment)
            transaction.commit()
        }
    }
}
