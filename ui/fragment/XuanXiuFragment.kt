package com.example.ccy.tes.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ccy.tes.R
import com.example.ccy.tes.presenter.XuanXiuFraPresenter
import com.example.ccy.tes.ui.activity.XuanXiuActivity
import com.example.ccy.tes.ui.adapter.XuanXiuAdapter
import com.example.ccy.tes.ui.adapter.XuanxiuSelectedAdapter
import com.example.ccy.tes.ui.view.XuanXiuFraView


/**
 * 选择选修课程的fragment界面
 * Created by ccy on 2017/6/29.
 */

class XuanXiuFragment(val isFirstPager: Boolean) : Fragment(), XuanXiuFraView {

    private var recyclerview: RecyclerView? = null
    private var swiperefreshlayout: SwipeRefreshLayout? = null
    var presenter: XuanXiuFraPresenter? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.list, container, false)
        presenter = XuanXiuFraPresenter(context, this)
        recyclerview = view.findViewById(R.id.recyclerview) as RecyclerView
        swiperefreshlayout = view.findViewById(R.id.swiperefreshlayout) as SwipeRefreshLayout
        initView(view)
        return view
    }

    private fun initView(view: View) {
        recyclerview!!.layoutManager = LinearLayoutManager(context)
        swiperefreshlayout?.isEnabled = false
        swiperefreshlayout?.setColorSchemeColors(view.resources.getColor(R.color.colorPrimary))
        if (isFirstPager)
            setRefreshing(true)
        swiperefreshlayout?.setOnRefreshListener { XuanXiuActivity.main!!.presenter.courseRefresh(isFirstPager = true) }
        /*recyclerView!!.setOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView:RecyclerView, newState:Int) {
                // TODO Auto-generated method stub
                if (recyclerView!!.totalItemCount == recyclerView!!.lastVisibieItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && XuanXiuActivity.getMain().isLoad()) {
                    if (!recyclerView!!.isLoading) {
                        recyclerView!!.isLoading = true
                        recyclerView!!.footer.findViewById(R.id.footer_layout).visibility = View.VISIBLE
                        // 加载更多（获取接口）
                        recyclerView!!.iLoadListener.onLoad()
                    }
                }
            }

            override fun onScrolled(recyclerView:RecyclerView, dx:Int, dy:Int) {
                // TODO Auto-generated method stub
                recyclerView!!.lastVisibieItem = firstVisibleItem + visibleItemCount
                recyclerView!!.totalItemCount = totalItemCount
            }
        })*/


    }

    override fun setAdapter(adapter: XuanXiuAdapter) {
        recyclerview!!.adapter = adapter
        adapter.setOnScrollListener(object : XuanXiuAdapter.OnScrollListener {
            override fun scrollTo(pos: Int) {
                recyclerview!!.scrollToPosition(pos)
            }
        })

    }

    override fun setSelectedAdapter(adapter: XuanxiuSelectedAdapter) {
        recyclerview!!.adapter = adapter
        adapter.setOnScrollListener(object : XuanxiuSelectedAdapter.OnScrollListener {
            override fun scrollTo(pos: Int) {
                recyclerview!!.scrollToPosition(pos)
            }
        })

    }

    override fun setRefreshing(isRefresh: Boolean) {
        swiperefreshlayout?.isRefreshing = isRefresh
    }

    fun onLoad() {
        XuanXiuActivity.main?.presenter?.getNextCourse2(isFirstPager)

    }


}
