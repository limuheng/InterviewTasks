package com.muheng.m800task.common

import com.muheng.m800task.IMainView
import com.muheng.rxjava.IRxEventBus
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

/**
 * Created by Muheng Li on 2018/8/15.
 */

abstract class BasePresenter<T> : IRxEventBus {

    protected lateinit var mView: WeakReference<T>

    protected lateinit var mCompositeDisposable: CompositeDisposable

    open fun init() {
        mCompositeDisposable = CompositeDisposable()
        registerRxBus()
    }

    open fun dispose() {
        unregisterRxBus()
        mView.clear()
    }

    override fun registerRxBus() { }

    override fun unregisterRxBus() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.clear()
        }
    }
}