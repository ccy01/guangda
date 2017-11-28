package com.example.ccy.tes.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.XuanXiuBean
import com.example.ccy.tes.model.bean.XuanXiuSelectedBean
import com.example.ccy.tes.presenter.XuanXiuPresenter
import com.example.ccy.tes.ui.adapter.FragmentAdapter
import com.example.ccy.tes.ui.adapter.XuanXiuAdapter
import com.example.ccy.tes.ui.adapter.XuanxiuSelectedAdapter
import com.example.ccy.tes.ui.fragment.XuanXiuFragment
import com.example.ccy.tes.ui.view.XuanXiuView
import com.example.ccy.tes.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_xuanxiu.*
import java.util.*

/**
 * 选课主界面
 * Created by ccy on 2017/6/27.
 */

class XuanXiuActivity : AppCompatActivity(), View.OnClickListener, XuanXiuView {

    private var mIndicator: TabLayout? = null
    private var courseAdapter: XuanXiuAdapter? = null
    private var courseSelectedAdapter: XuanxiuSelectedAdapter? = null
    private var context: Context? = null
    override var lists: MutableList<XuanXiuBean>? = null
    private var lists2: MutableList<XuanXiuSelectedBean>? = null
    private var viewPager: ViewPager? = null
    private var fragments: ArrayList<Fragment>? = null
    private var titles: MutableList<String>? = null
    private var adapter: FragmentAdapter? = null
    private var fragment1: XuanXiuFragment? = null
    private var fragment: XuanXiuFragment? = null
    private var isFirstPager= true

    val presenter  = XuanXiuPresenter(this, this)

    override var postArg: MutableList<Map<String, String>>? = null

    private val mWriteListener = TextView.OnEditorActionListener { view, actionId, event ->
        //设置输入完成的监听
        if (actionId == EditorInfo.IME_NULL && event.action == KeyEvent.ACTION_UP) {
            when (view.id) {
                R.id.et_jump -> presenter.setJump(et_jump.text.toString().trim { it <= ' ' }, isFirstPager)
            }
        }
        true
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xuanxiu)
        context = this
        main = this
        initExpandListView()

    }

    override fun setRefreshing(isRefresh:Boolean) {
        if(isFirstPager)
            fragment?.setRefreshing(isRefresh)
        else
            fragment1?.setRefreshing(isRefresh)
    }

    private fun initExpandListView() {

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "选择课程"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.action_commit ->{
                    if (isFirstPager) {
                        postArg = ArrayList()
                        var result = 0
                        var result2 = 0
                        var count = 0
                         lists!!.forEach{
                            if (it.isCheck_c) {
                                val map = HashMap<String, String>()
                                map.put("选课", it.coursePost)
                                map.put("count", count.toString())
                                result2 = result
                                result = count

                                if (it.isCheck_b) {
                                    map.put("教材", it.bookPost)
                                } else {
                                    map.put("教材", "")
                                }
                                postArg!!.add(map)
                            }
                            count++
                        }
                        if (postArg!!.size > 0) {
                            if (result - result2 >= 6) {
                                Toast.makeText(applicationContext, "由于系统逻辑，当两者间隔大于6时，提交不成功，请去掉一个，或者两者间隔减小", Toast.LENGTH_LONG).show()

                            } else
                                presenter.coursePost()

                        } else {
                            Toast.makeText(this, "请选择一个", Toast.LENGTH_LONG).show()
                        }
                    }

                }
                R.id.action_refresh->{
                    presenter.courseRefresh(isFirstPager)
                }
                R.id.action_filter ->{
                    if (isFirstPager)
                        fragment1!!.presenter?.showDialog(presenter.property!!, presenter.courseClass!!, presenter.coursePlace!!, presenter.has!!, presenter.courseTime!!)
                }
            }
            true
        }
        toolbar.setNavigationOnClickListener { finish() }

        et_jump.setOnEditorActionListener(mWriteListener)

        val firstBtn = findViewById(R.id.first_page) as Button
        firstBtn.setOnClickListener(this)

        viewPager = findViewById(R.id.view_pager) as ViewPager
        fragments = ArrayList()
        mIndicator = findViewById(R.id.indicator) as TabLayout
        fragment = XuanXiuFragment(true)
        fragment1 = XuanXiuFragment(false)
        fragments!!.add(fragment!!)
        fragments!!.add(fragment1!!)
        titles = ArrayList()
        titles!!.add("选课")
        titles!!.add("已选")

        mIndicator!!.addTab(mIndicator!!.newTab().setText(titles!![0]))
        mIndicator!!.addTab(mIndicator!!.newTab().setText(titles!![1]))

        adapter = FragmentAdapter(supportFragmentManager, fragments, titles)
        viewPager!!.adapter = adapter
        mIndicator!!.setupWithViewPager(viewPager)
        presenter.getCourse()
        viewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    toolbar.title = "选择课程"
                    isFirstPager = true
                    setJumpText(presenter.temp!![1])
                    setSumPager(presenter.temp!![0])
                    setRate(presenter.temp!![1] + "/" + presenter.temp!![2])
                } else {
                    toolbar.title = "已选课程"
                    isFirstPager = false
                    setJumpText(presenter.temp!![5])
                    setSumPager(presenter.temp!![4])
                    setRate(presenter.temp!![5] + "/" + presenter.temp!![6])
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


    }

    override fun setFirstItem() {
        viewPager!!.currentItem = 1
    }

    override fun setList1(list: MutableList<XuanXiuBean>?) {

        lists = list
        courseAdapter = XuanXiuAdapter(context!!, list!!)
        fragment!!.setAdapter(courseAdapter!!)
        setJumpText(presenter.temp!![1])
        setSumPager(presenter.temp!![0])
        setRate(presenter.temp!![1] + "/" + presenter.temp!![2])
    }

    override fun setList2(list: MutableList<XuanXiuSelectedBean>?) {

        lists2 = list
        courseSelectedAdapter = XuanxiuSelectedAdapter(context!!, list!!)
        fragment1!!.setSelectedAdapter(courseSelectedAdapter!!)
        setJumpText(presenter.temp!![5])
        setSumPager(presenter.temp!![4])
        setRate(presenter.temp!![5] + "/" + presenter.temp!![6])
    }

    override fun addList1(list: MutableList<XuanXiuBean>?) {
        if(list == null)
            return
        lists?.addAll(list)
        courseAdapter?.notifyDataSetChanged()
    }

    override fun addList2(list: MutableList<XuanXiuSelectedBean>?) {
        if(list == null)
            return
        lists2?.addAll(list)
        courseSelectedAdapter?.notifyDataSetChanged()
    }

    override fun setRate(ct: String) {
        tv_rate.text = ct
    }

    override fun setSumPager(sum: String) {
        tv_sum_pager.text = "总共${sum}条"
    }

    override fun setJumpText(jump: String) {
        et_jump.setText(jump)
    }


    override fun onClick(v: View) {

        presenter.getNextCourse(isFirstPager)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.xxselect_menu, menu)//加载menu文件到布局
        return true
    }

    override fun save() {
        val ss = StringBuilder()
        if (lists!!.size > 3)
            for (i in 0..3) {
                ss.append(lists!![i].courseName!! + "\n")
                if (i == 3) {
                    ss.append("...\n已选:\n")
                }
            }
        for (i in lists2!!.indices) {
            if (i == lists2!!.size - 1) {
                ss.append(lists2!![i].courseName)
            } else {
                ss.append(lists2!![i].courseName + "\n")
            }
        }
        val util = SharedPreferenceUtil(applicationContext, "xuanxiu_selected")
        util.setKeyData("select", ss.toString())
        MainActivity.main!!.getmAdapter()!!.notifyDataSetChanged()
    }


    companion object {
        var main: XuanXiuActivity? = null
            private set
    }


}
