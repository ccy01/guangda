package com.example.ccy.tes.ui.view

import com.example.ccy.tes.model.bean.PhysicalBean

/**
 * Created by ccy on 2017/11/10.
 */
interface PhysicalView {
    fun setRefreshing(isRefresh:Boolean)
    fun setListOne(lists: MutableList<PhysicalBean>?)
    fun setListTwo(lists2: MutableList<PhysicalBean>?)
    fun setAdapter()
    fun setSelectedAdapter()
    fun setSize()
    fun save()
}