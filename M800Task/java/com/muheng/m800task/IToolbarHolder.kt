package com.muheng.m800task

/**
 * Created by Muheng Li on 2018/8/16.
 */
interface IToolbarHolder {
    fun onUpdatePrimaryTitle(text: String?)
    fun onUpdateSecondaryTitle(text: String?)
    fun onQueryTriggered(text: String?)
}