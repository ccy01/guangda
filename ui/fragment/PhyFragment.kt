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
import com.example.ccy.tes.ui.activity.PhysicalActivity
import com.example.ccy.tes.ui.adapter.PhysicalAdapter
import com.example.ccy.tes.ui.adapter.PhysicalSelectedAdapter


/**
 * Created by ccy on 2017/11/10.
 */
class PhyFragment(var isFirst: Boolean) : Fragment() {

    private var recyclerview:RecyclerView? = null
    private var swiperefreshlayout:SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.phy_fragment, container, false)

        recyclerview = view.findViewById(R.id.recyclerview) as RecyclerView
        swiperefreshlayout = view.findViewById(R.id.swiperefreshlayout) as  SwipeRefreshLayout
        recyclerview!!.layoutManager = LinearLayoutManager(context)
        swiperefreshlayout?.setColorSchemeColors(view.resources.getColor(R.color.colorPrimary))
        if (isFirst)
            swiperefreshlayout?.isRefreshing = true
        swiperefreshlayout?.setOnRefreshListener { PhysicalActivity.main!!.getCourse() }
        return view
    }

    fun setAdapter(adapter: PhysicalAdapter) {
        recyclerview!!.adapter = adapter
        adapter.setOnScrollListener(object : PhysicalAdapter.OnScrollListener {
            override fun scrollTo(pos: Int) {
                recyclerview!!.scrollToPosition(pos)
            }
        })

    }

    fun setSelectedAdapter(adapter: PhysicalSelectedAdapter) {
        recyclerview!!.adapter = adapter
        adapter.setOnScrollListener(object : PhysicalSelectedAdapter.OnScrollListener {
            override fun scrollTo(pos: Int) {
                recyclerview!!.scrollToPosition(pos)
            }
        })

    }

    fun setRefreshing(b: Boolean) {
        swiperefreshlayout?.isRefreshing = b
    }
}