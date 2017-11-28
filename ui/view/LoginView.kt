package com.example.ccy.tes.ui.view

/**
 * Created by ccy on 2017/11/9.
 */
interface LoginView {
    fun showDialog(isShow:Boolean)
    fun setSwipeRefreshLayout(isRefresh:Boolean)
    fun getPassword():String
    fun getUserName():String
    fun isChecked():Boolean
    fun joinToMain()
    fun showToast(message:String)


}