package com.example.ccy.tes.ui.view

import com.example.ccy.tes.model.bean.XuanXiuBean
import com.example.ccy.tes.model.bean.XuanXiuSelectedBean

/**
 * Created by ccy on 2017/11/23.
 */
interface XuanXiuView {

    fun setRefreshing(isRefresh:Boolean)
    fun setList1(list: MutableList<XuanXiuBean>?)
    fun setList2(list: MutableList<XuanXiuSelectedBean>?)
    fun setFirstItem()
    fun setRate(ct: String)
    fun setSumPager(sum: String)
    fun setJumpText(jump: String)
    fun save()
    fun addList1(list: MutableList<XuanXiuBean>?)
    fun addList2(list: MutableList<XuanXiuSelectedBean>?)
    var postArg: MutableList<Map<String, String>>?
    var lists: MutableList<XuanXiuBean>?

}