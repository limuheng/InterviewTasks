package com.muheng.m800task

import com.muheng.m800task.common.BasePresenter
import com.muheng.m800task.rxjava.Event
import com.muheng.rxjava.RxEventBus
import io.reactivex.functions.Consumer
import java.lang.ref.WeakReference

/**
 * Created by Muheng Li on 2018/8/16.
 */
class TrackDisplayPresenter(trackViewHolder: ITrackViewHolder) : BasePresenter<ITrackViewHolder>() {

    companion object {
        const val TAG = "ToolbarPresenter"
    }

    init {
        mView = WeakReference(trackViewHolder)
    }

    override fun registerRxBus() {
        val subscribe = RxEventBus.get()!!.subscribe(
                Consumer { event ->
                    when (event) {
                        is Event.ShowTrackView -> {
                            mView.get()?.onTrackDisplayed(event.item)
                        }
                        is Event.CloseTrackView -> {
                            mView.get()?.onTrackClosed()
                        }
                    }
                })

        mCompositeDisposable.add(subscribe)
    }
}