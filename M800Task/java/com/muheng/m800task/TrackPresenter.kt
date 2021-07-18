package com.muheng.m800task

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.View
import android.widget.MediaController
import com.muheng.m800task.common.BasePresenter
import com.muheng.m800task.retrofit.Item
import com.muheng.m800task.rxjava.Event
import com.muheng.rxjava.RxEventBus
import io.reactivex.functions.Consumer
import java.lang.ref.WeakReference

/**
 * Created by Muheng Li on 2018/8/16.
 */
class TrackPresenter(trackView: ITrackView) : BasePresenter<ITrackView>() {

    companion object {
        const val TAG = "TrackPresenter"
    }

    class CustomMediaController(context: Context?) : MediaController(context) {
        override fun hide() {
            // Do not hide the controller
        }

        fun hideSelf() {
            super.hide()
        }
    }

    init {
        mView = WeakReference(trackView)
    }

    override fun registerRxBus() {
        val subscribe = RxEventBus.get()!!.subscribe(
                Consumer { event ->
                    when (event) {
                        is Event.DisplayTrack -> {
                            mView.get()?.onTrackChanged(event.item)
                        }
                    }
                })

        mCompositeDisposable.add(subscribe)
    }

    private var mMediaPlayer: MediaPlayer? = null
    private var mMediaController: CustomMediaController? = null

    private val mMPController = object : MediaController.MediaPlayerControl {
        override fun isPlaying(): Boolean {
            return mMediaPlayer?.isPlaying ?: false
        }

        override fun canSeekForward(): Boolean {
            return true
        }

        override fun getDuration(): Int {
            return mMediaPlayer?.duration ?: 0
        }

        override fun pause() {
            mMediaPlayer?.pause()
        }

        override fun getBufferPercentage(): Int {
            return 0
        }

        override fun seekTo(pos: Int) {
            mMediaPlayer?.seekTo(pos)
        }

        override fun getCurrentPosition(): Int {
            return mMediaPlayer?.currentPosition ?: 0
        }

        override fun canSeekBackward(): Boolean {
            return true
        }

        override fun start() {
            mMediaPlayer?.start()
        }

        override fun getAudioSessionId(): Int {
            return mMediaPlayer?.audioSessionId ?: 0
        }

        override fun canPause(): Boolean {
            return true
        }
    }

    private var mPreparedListener = object : MediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: MediaPlayer?) {
            mView.get()?.hideProgress()
            mMediaController?.show()
        }
    }

    private var mCompletionListener = object : MediaPlayer.OnCompletionListener {
        override fun onCompletion(mp: MediaPlayer?) {
            mMediaPlayer?.stop()
            mMediaPlayer?.prepareAsync()
        }
    }

    fun initMediaPlayer(item: Item) {
        mView.get()?.showProgress()

        releaseMediaPlayer()

        mMediaPlayer = MediaPlayer()
        val attr = AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        mMediaPlayer?.setOnPreparedListener(mPreparedListener)
        mMediaPlayer?.setOnCompletionListener(mCompletionListener)
        mMediaPlayer?.setAudioAttributes(attr)
        mMediaPlayer?.setDataSource(item.previewUrl)
        mMediaPlayer?.prepareAsync()
    }

    // Release MediaPlayer resources
    fun releaseMediaPlayer() {
        if (mMediaPlayer?.isPlaying == true) {
            mMediaPlayer?.stop()
        }

        if (mMediaPlayer != null) {
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
    }

    fun initMediaController(context: Context?) {
        mMediaController = CustomMediaController(context)
        mMediaController?.setMediaPlayer(mMPController)
        mMediaController?.hideSelf()
    }

    fun setMediaControllerAnchorView(view: View) {
        mMediaController?.setAnchorView(view)
    }

    fun hideMediaController() {
        mMediaController?.hideSelf()
    }
}