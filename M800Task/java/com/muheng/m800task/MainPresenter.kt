package com.muheng.m800task

import android.util.Log
import com.muheng.m800task.common.BasePresenter
import com.muheng.m800task.retrofit.IiTuneSearchApi
import com.muheng.m800task.retrofit.Parameter
import com.muheng.m800task.retrofit.RetrofitClient
import com.muheng.m800task.rxjava.Event
import com.muheng.rxjava.RxEventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

/**
 * Created by Muheng Li on 2018/8/15.
 */

class MainPresenter(mainView: IMainView) : BasePresenter<IMainView>() {

    companion object {
        const val TAG = "MainPresenter"
        var sSearchTerm: String = "top+music"
    }

    init {
        mView = WeakReference(mainView)
    }

    override fun registerRxBus() {
        val subscribe = RxEventBus.get()!!.subscribe(
                Consumer { event ->
                    when (event) {
                        is Event.Search -> {
                            searchMusics(event.text)
                        }
                        is Event.ItemClicked -> {
                            mView.get()?.onItemClicked(event.item)
                        }
                    }
                })

        mCompositeDisposable.add(subscribe)
    }

    fun searchMusics(term: String = "") {
        var paramList = ArrayList<Parameter>()
        if (term.isBlank()) {
            paramList.add(Parameter(IiTuneSearchApi.TERM, sSearchTerm))
        } else {
            term.replace(' ', '+')
            paramList.add(Parameter(IiTuneSearchApi.TERM, term))
            sSearchTerm = term
        }

        mCompositeDisposable.add(
                RetrofitClient.get()
                        .search(IiTuneSearchApi.getParamsMap(paramList))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            if (result.resultCount > 0) {
                                RxEventBus.get()?.send(Event.UpdateSecondaryTitle(result.resultCount.toString()))
                                mView.get()?.onSearchResult(result.results)
                            } else {
                                mView.get()?.onNoData()
                            }
                        }, { error ->
                            error.printStackTrace()
                            mView.get()?.onError(error.message)
                        })
        )
    }
}