package com.example.ccy.tes.ui.view

import android.widget.Spinner
import com.example.ccy.tes.model.bean.SelectedBean
import com.example.ccy.tes.ui.adapter.SelectedAdatper

/**
 * Created by ccy on 2017/11/11.
 */
interface SelectedView {

    fun setRefreshing(isRefrsh:Boolean)
    fun setAdapter()
    fun setList(list:MutableList<SelectedBean>?)
    fun save()
    fun setSpinner(b:Boolean)
    var sp_year:Spinner?
    var sp_term:Spinner?

}