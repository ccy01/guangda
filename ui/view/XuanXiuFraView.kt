package com.example.ccy.tes.ui.view

import com.example.ccy.tes.ui.adapter.XuanXiuAdapter
import com.example.ccy.tes.ui.adapter.XuanxiuSelectedAdapter

/**
 * Created by ccy on 2017/11/24.
 */
interface XuanXiuFraView {

    fun setAdapter(adapter: XuanXiuAdapter)
    fun setSelectedAdapter(adapter: XuanxiuSelectedAdapter)
    fun setRefreshing(isRefresh: Boolean)
}