package com.muheng.m800task

import com.muheng.m800task.common.BasePresenter
import com.muheng.m800task.rxjava.Event
import com.muheng.rxjava.RxEventBus
import io.reactivex.functions.Consumer
import java.lang.ref.WeakReference

/**
 * Created by Muheng Li on 2018/8/16.
 */
class ToolbarPresenter(toolbarHolder: IToolbarHolder) : BasePresenter<IToolbarHolder>() {

    companion object {
        const val TAG = "ToolbarPresenter"
    }

    init {
        mView = WeakReference(toolbarHolder)
    }

    override fun registerRxBus() {
        val subscribe = RxEventBus.get()!!.subscribe(
                Consumer { event ->
                    when (event) {
                        is Event.QueryChanged -> {
                            mView.get()?.onQueryTriggered(event.text)
                        }
                        is Event.UpdatePrimaryTitle -> {
                            mView.get()?.onUpdatePrimaryTitle(event.text)
                        }
                        is Event.UpdateSecondaryTitle -> {
                            mView.get()?.onUpdateSecondaryTitle(event.text)
                        }
                    }
                })

        mCompositeDisposable.add(subscribe)
    }

}