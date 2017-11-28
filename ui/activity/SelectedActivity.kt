package com.example.ccy.tes.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Spinner
import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.SelectedBean
import com.example.ccy.tes.presenter.SelectedPresenter
import com.example.ccy.tes.ui.adapter.SelectedAdatper
import com.example.ccy.tes.ui.view.SelectedView
import com.example.ccy.tes.util.SharedPreferenceUtil

/**
 * 已经选课界面
 * Created by ccy on 2017/6/30.
 */

class SelectedActivity : AppCompatActivity() ,SelectedView{
    override fun setSpinner(b: Boolean) {
        if(b){
            sp_year
        }
    }


    val presenter:SelectedPresenter = SelectedPresenter(this,this)
    private var recyclerview: RecyclerView? = null
    private var datas: MutableList<SelectedBean>? = null
    private var adapter: SelectedAdatper? = null
     var srl: SwipeRefreshLayout? = null
     override var sp_year:Spinner? =null
     override var sp_term:Spinner? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected)
        initView()
        presenter.getHasSelected()
    }

    private fun initView() {

        recyclerview = findViewById(R.id.recyclerview) as RecyclerView
        sp_term = findViewById(R.id.sp_term) as Spinner
        sp_year = findViewById(R.id.sp_year) as Spinner
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        recyclerview?.layoutManager = LinearLayoutManager(this)
        srl = findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        srl!!.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        srl!!.isRefreshing = true
        srl!!.setOnRefreshListener { presenter.getHasSelected() }
        toolbar.title = "选课情况"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

    }

    override fun setList(list: MutableList<SelectedBean>?) {
        datas = list
    }

    override fun save() {
        val ss = StringBuilder()
        for (i in datas!!.indices) {
            if (i == datas!!.size - 1) {
                ss.append(datas!![i].courseName!! + "\n....")
            } else
                ss.append(datas!![i].courseName!! + "\n")
        }
        val util = SharedPreferenceUtil(this, "selected")
        util.setKeyData("selected", ss.toString().trim { it <= ' ' })
        MainActivity.main!!.getmAdapter()!!.notifyDataSetChanged()
    }

    override fun setAdapter() {
        adapter = SelectedAdatper(this,datas!!)
        recyclerview!!.adapter = adapter
        adapter!!.setOnScrollListener(object : SelectedAdatper.OnScrollListener {
            override fun scrollTo(pos: Int) {
                recyclerview!!.scrollToPosition(pos)
            }
        })

    }

    override fun setRefreshing(isRefrsh: Boolean) {
        srl?.isRefreshing = isRefrsh
    }


}
