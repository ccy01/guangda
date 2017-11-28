package com.example.ccy.tes.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.PhysicalBean
import com.example.ccy.tes.presenter.PhysicalPresenter
import com.example.ccy.tes.service.SelectedService
import com.example.ccy.tes.ui.adapter.*
import com.example.ccy.tes.ui.fragment.PhyFragment
import com.example.ccy.tes.ui.view.PhysicalView
import com.example.ccy.tes.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activty_physical.*

import java.util.ArrayList

/**
 * Created by ccy on 2017/6/27.
 */

class PhysicalActivity : AppCompatActivity(),PhysicalView {

    private var statusAdapter: PhysicalAdapter? = null
    private var statusAdapterSelected: PhysicalSelectedAdapter? = null
    private var context: Context? = null
    private var lists: MutableList<PhysicalBean>? = null
    private var lists2: MutableList<PhysicalBean>? = null
    private var fragments: ArrayList<Fragment>? = null
    private var titles: MutableList<String>? = null
    private var adapter: FragmentAdapter? = null
    private var fragment1: PhyFragment? = null
    private var fragment2: PhyFragment? = null
    private var selectedService: SelectedService? = null
    private var item: MenuItem? = null
    private var isFirst = true
    val presenter:PhysicalPresenter = PhysicalPresenter(this,this)

    override fun save() {
        val ss = StringBuilder()
        if (lists!!.size > 3)
            for (i in 0..3) {
                ss.append(lists!![i].more + "  " + lists!![i].teacherName + "\n")
                if (i == 3) {
                    ss.append("...\n" + "已选:\n")
                }
            }
        for (i in lists2!!.indices) {
            if (i == lists2!!.size - 1) {
                ss.append(lists2!![i].courseName + "  " + lists2!![i].teacherName)
            } else {
                ss.append(lists2!![i].courseName + "  " + lists2!![i].teacherName + "\n")
            }
        }
        val util = SharedPreferenceUtil(applicationContext, "physical")
        util.setKeyData("select", ss.toString().trim { it <= ' ' })
    }

    override fun setSize() {
        item!!.title = "" + if (isFirst) lists!!.size else lists2!!.size

    }

    override fun setAdapter() {
        statusAdapter = PhysicalAdapter(context!!, lists!!)
        fragment1!!.setAdapter(statusAdapter!!)
    }

    override fun setSelectedAdapter() {
        statusAdapterSelected = PhysicalSelectedAdapter(context!!, lists2!!)
        fragment2!!.setSelectedAdapter(statusAdapterSelected!!)
    }

    override fun setListOne(lists: MutableList< PhysicalBean>?) {
        this.lists = lists
    }

    override fun setListTwo(lists2: MutableList<PhysicalBean>?) {
        this.lists2 = lists2
    }

    override fun setRefreshing(isRefresh: Boolean) {
        if (isFirst)
            fragment1!!.setRefreshing(isRefresh)
        else
            fragment2!!.setRefreshing(isRefresh)
    }


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_physical)
        context = this
        main = this
        initExpandListView()
       getCourse()

    }

    fun getCourse(){
        presenter.getCourse()
    }

    private fun initExpandListView() {
        toolbar.title = "选择课程"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_commit) {
                Toast.makeText(applicationContext, "提交", Toast.LENGTH_LONG).show()
            } else if (item.itemId == R.id.action_refresh) {
               getCourse()
            }
            true
        }
        toolbar.setNavigationOnClickListener { finish() }
        fragments = ArrayList()
        fragment1 = PhyFragment(true)
        fragment2 = PhyFragment(false)
        fragments!!.add(fragment1!!)
        fragments!!.add(fragment2!!)

        titles = ArrayList()
        titles!!.add("选课")
        titles!!.add("已选")
        selectedService = SelectedService.seletedService
        indicator.addTab(indicator.newTab().setText(titles!![0]))
        indicator.addTab(indicator.newTab().setText(titles!![1]))
        adapter = FragmentAdapter(supportFragmentManager, fragments, titles)
        pager.adapter = adapter
        indicator.setupWithViewPager(pager)
        indicator.setTabsFromPagerAdapter(adapter)
        pager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    isFirst = true
                    toolbar!!.title = "选择课程"
                    item!!.title = lists!!.size.toString() + ""
                } else {
                    isFirst = false
                    toolbar!!.title = "已选课程"
                    item!!.title = lists2!!.size.toString() + ""
                }
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.physical_menu, menu)//加载menu文件到布局
        item = menu.findItem(R.id.action_tips)
        return true
    }

    companion object {
        var main: PhysicalActivity? = null
            private set
    }

}
