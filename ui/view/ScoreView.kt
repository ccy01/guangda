package com.example.ccy.tes.ui.view

import com.example.ccy.tes.model.bean.ScoreBean
import com.example.ccy.tes.ui.adapter.ScoreAdatper

/**
 * Created by ccy on 2017/11/25.
 */
interface ScoreView {

    fun setList(list: List<ScoreBean>)
    fun setAdapter()
    fun save()
}