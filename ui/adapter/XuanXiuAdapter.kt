package com.example.ccy.tes.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.BaseBean
import com.example.ccy.tes.model.bean.PhysicalBean
import com.example.ccy.tes.model.bean.XuanXiuBean

/**
 * Created by ccy on 2017/11/10.
 */
class XuanXiuAdapter(val context: Context, val dataBeanList: MutableList<XuanXiuBean>) : RecyclerView.Adapter<BaseViewHolder>() {
    var mInflater: LayoutInflater? = null
    var mOnScrollListener: OnScrollListener? = null

    init {
        this.mInflater = LayoutInflater.from(context)
    }


    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var view: View
        when (viewType) {
            XuanXiuBean.PARENT_ITEM -> {
                view = mInflater!!.inflate(R.layout.xuanxiu_group, parent, false)
                return XParentViewHolder(context, view)
            }

            XuanXiuBean.CHILD_ITEM -> {
                view = mInflater!!.inflate(R.layout.xuanxiu_child, parent, false)
                return XChildViewHolder(context, view)
            }
            else -> {
                view = mInflater!!.inflate(R.layout.xuanxiu_group, parent, false)
                return XParentViewHolder(context, view)
            }

        }
    }

    /**
     * 根据不同的类型绑定View
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (getItemViewType(position)) {
            XuanXiuBean.PARENT_ITEM -> {
                val parentViewHolder = holder as XParentViewHolder
                parentViewHolder.bindView(dataBeanList[position], itemClickListener)
            }

            XuanXiuBean.CHILD_ITEM -> {
                val childViewHolder = holder as XChildViewHolder
                childViewHolder.bindView(dataBeanList[position])
            }

        }
    }


    override fun getItemCount() = dataBeanList.size


    override fun getItemViewType(position: Int) = dataBeanList[position].type


    val itemClickListener = object : ItemClickListener {
        override fun onExpandChildren(bean: BaseBean) {
            val child = (bean as XuanXiuBean).copy(type = 1)
            val position = getCurrentPosition(child.ID)//确定当前点击的item位置
            add(child, position + 1)//在当前的item下方插入
            if (position == dataBeanList.size - 2 && mOnScrollListener != null) { //如果点击的item为最后一个
                mOnScrollListener?.scrollTo(position + 1)//向下滚动，使子布局能够完全展示
            }
        }

        override fun onHideChildren(bean: BaseBean) {
            val position = getCurrentPosition((bean as XuanXiuBean).ID)//确定当前点击的item位置
            remove(position + 1)//删除
            if (mOnScrollListener != null) {
                mOnScrollListener?.scrollTo(position)
            }
        }
    }

    /**
     * 在父布局下方插入一条数据
     * @param bean
     * @param position
     */
    fun add(bean: XuanXiuBean, position: Int) {
        dataBeanList.add(position, bean)
        notifyItemInserted(position)
    }

    /**
     *移除子布局数据
     * @param position
     */
    fun remove(position: Int) {
        dataBeanList.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * 确定当前点击的item位置并返回
     * @param uuid
     * @return
     */
    fun getCurrentPosition(uuid: Int): Int {
        for (i in dataBeanList.indices) {
            if (uuid == dataBeanList[i].ID) {
                return i
            }
        }
        return -1
    }




    interface OnScrollListener {
        fun scrollTo(pos: Int)
    }

    fun setOnScrollListener(onScrollListener: OnScrollListener) {
        mOnScrollListener = onScrollListener
    }

}