package com.muheng.m800task.rxjava

import com.muheng.m800task.retrofit.Item

/**
 * Created by Muheng Li on 2018/8/15.
 */

class Event {
    class QueryChanged(val text: String?)
    class UpdatePrimaryTitle(val text: String?)
    class UpdateSecondaryTitle(val text: String?)
    class Search(val text: String)
    class ItemClicked(val item: Item)
    class ShowTrackView(val item: Item)
    class CloseTrackView()
    class DisplayTrack(val item: Item)
    class ShowProgress(show: Boolean)
}