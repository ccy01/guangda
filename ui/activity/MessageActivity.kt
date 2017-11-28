package com.example.ccy.tes.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView

import com.example.ccy.tes.R
import com.example.ccy.tes.presenter.MsgPresenter
import com.example.ccy.tes.ui.view.MessageView
import com.example.ccy.tes.util.ImageLoadUtils

/**
 * 个人信息
 * Created by ccy on 2017/8/20.
 */

class MessageActivity : AppCompatActivity(),MessageView {
    override fun setImg(url: String) {
        ImageLoadUtils.display(this,iv_head,url)
    }

    override fun setText(msg: String) {
        textView!!.text = msg
    }

    override fun setRefreshing(isRefresh: Boolean) {
        srl!!.isRefreshing = isRefresh
    }

    private var textView: TextView? = null
    private var srl: SwipeRefreshLayout? = null
    private val presenter:MsgPresenter = MsgPresenter(this,this)
    var iv_head:ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_msg_activity)
        textView = findViewById(R.id.msg) as TextView
        srl = findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        srl!!.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        iv_head = findViewById(R.id.iv_head) as ImageView?
        srl!!.setOnRefreshListener { presenter.getMsg() }
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "个人信息"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)

        toolbar.setNavigationOnClickListener { finish() }
        presenter.getMsg()

    }

}
