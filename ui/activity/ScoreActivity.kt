package com.example.ccy.tes.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.ScoreBean
import com.example.ccy.tes.presenter.ScorePresenter
import com.example.ccy.tes.ui.adapter.ScoreAdatper
import com.example.ccy.tes.ui.view.ScoreView
import com.example.ccy.tes.util.SharedPreferenceUtil

/**
 * Created by ccy on 2017/6/27.
 */

class ScoreActivity : AppCompatActivity(), ScoreView {


    private var recyclerview: RecyclerView? = null
    private var adapter: ScoreAdatper? = null
    private val dialog: ProgressDialog? = null
    var lists: List<ScoreBean>? = null

    val presenter = ScorePresenter(this, this)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_score)
        initExpandListView()

    }

    fun initExpandListView() {

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "成绩"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)

        toolbar.setNavigationOnClickListener { finish() }
        recyclerview = findViewById(R.id.recyclerview) as RecyclerView
        recyclerview?.layoutManager = LinearLayoutManager(this)
        presenter.getScore()

    }

    override fun setList(list: List<ScoreBean>) {
        this.lists = list
    }

    override fun setAdapter() {
        this.adapter = ScoreAdatper(this, lists!!)
        recyclerview?.adapter = adapter
    }

    override fun save() {
        val ss = StringBuffer()
        if (lists!!.size > 3)
            for (i in 0..4) {
                if (i == 3) {
                    ss.append(lists?.get(i)?.courseName + ":   " + lists?.get(i)?.score + "\n...\n")
                } else {
                    ss.append(lists?.get(i)?.courseName + ":   " + lists?.get(i)?.score + "\n")
                }
            }
        // ss.append("学期情况:\n" + scoreService.getPoint(s));
        val util = SharedPreferenceUtil(this.applicationContext, "score")
        util.setKeyData("score", ss.toString().trim())
        MainActivity.Companion.main?.getmAdapter()?.notifyDataSetChanged()
    }

}
