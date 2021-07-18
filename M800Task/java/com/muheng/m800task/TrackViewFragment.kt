package com.muheng.m800task

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.muheng.m800task.retrofit.Item
import com.muheng.m800task.rxjava.Event
import com.muheng.rxjava.RxEventBus

/**
 * Created by Muheng Li on 2018/8/16.
 */
class TrackViewFragment : Fragment(), ITrackView {

    companion object {
        const val TAG: String = "TrackViewFragment"
        const val KEY_ITEM = "item"
    }

    private lateinit var mClose: TextView
    private lateinit var mTrackImage: ImageView
    private lateinit var mTrackName: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mControllerContainer: ViewGroup

    private var mItem: Item? = null

    private lateinit var mTrackPresenter: TrackPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTrackPresenter = TrackPresenter(this)
        mTrackPresenter.init()
    }

    override fun onDestroy() {
        mTrackPresenter.dispose()
        mTrackPresenter.releaseMediaPlayer()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_track, container, false)
        mClose = rootView.findViewById(R.id.close)
        mTrackImage = rootView.findViewById(R.id.track_image)
        mTrackName = rootView.findViewById(R.id.track_name)
        mControllerContainer = rootView.findViewById(R.id.controller_container)
        mProgressBar = rootView.findViewById(R.id.progress_bar)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mClose.setOnClickListener {
            RxEventBus.get()?.send(Event.CloseTrackView())
        }

        mTrackPresenter.initMediaController(context)
        mTrackPresenter.setMediaControllerAnchorView(mControllerContainer)

        if (savedInstanceState != null) {
            val item = savedInstanceState.getParcelable<Item>(KEY_ITEM)
            if (item != null) {
                onTrackChanged(item)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_ITEM, mItem)
    }

    override fun onTrackChanged(item: Item) {
        mItem = item
        mTrackName.text = item.trackName
        try {
            val photoUrl: String = item.artworkUrl100
            Glide.with(context).load(photoUrl).centerCrop()
                    .placeholder(R.drawable.image_coming_soon).into(mTrackImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mTrackPresenter.initMediaPlayer(item)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            mTrackPresenter.hideMediaController()
            mTrackPresenter.releaseMediaPlayer()
        }
    }

    override fun showProgress() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        mProgressBar.visibility = View.INVISIBLE
    }
}