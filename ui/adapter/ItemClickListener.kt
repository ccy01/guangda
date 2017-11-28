package com.example.ccy.tes.ui.adapter

import com.example.ccy.tes.model.bean.BaseBean

/**
 * Created by ccy on 2017/11/10.
 */
interface ItemClickListener {

    fun onExpandChildren(bean: BaseBean)
    fun onHideChildren(bean: BaseBean)
}