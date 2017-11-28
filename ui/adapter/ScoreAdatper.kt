package com.example.ccy.tes.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.BaseBean
import com.example.ccy.tes.model.bean.ScoreBean
import com.example.ccy.tes.model.bean.SelectedBean

/**
 * Created by ccy on 2017/11/11.
 */
class ScoreAdatper(val context: Context, val dataBeanList: List<ScoreBean>) : RecyclerView.Adapter<BaseViewHolder>() {
    var mInflater: LayoutInflater? = null


    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        mInflater = LayoutInflater.from(context)
        val view = mInflater!!.inflate(R.layout.score_group, parent, false)
        return ScoreHolder(context, view)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val childViewHolder = holder as ScoreHolder
        childViewHolder.bindView(dataBeanList[position])

    }

    override fun getItemCount() = dataBeanList.size

}