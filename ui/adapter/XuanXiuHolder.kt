package com.example.ccy.tes.ui.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.*
import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.XuanXiuBean
import com.example.ccy.tes.model.bean.XuanXiuSelectedBean
import com.example.ccy.tes.ui.activity.XuanXiuActivity


/**
 * Created by ccy on 2017/11/10.
 */

class XParentViewHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {

    val itemView = itemView

    var book = itemView.findViewById(R.id.book) as CheckBox
    var courseName = itemView.findViewById(R.id.tv_cn) as TextView
    var course = itemView.findViewById(R.id.select) as CheckBox
    var holdPlace = itemView.findViewById(R.id.tv_p) as TextView
    var score = itemView.findViewById(R.id.tv_s) as TextView
    var pb = itemView.findViewById(R.id.pb_dis) as ProgressBar
    var tv_capacity = itemView.findViewById(R.id.tv_capacity) as TextView
    var tv_last = itemView.findViewById(R.id.tv_last) as TextView
    val containerLayout: CardView = itemView.findViewById(R.id.phy_group) as CardView
    var iv_expend = itemView.findViewById(R.id.iv_expend) as ImageView

    fun bindView(dataBean: XuanXiuBean, listener: ItemClickListener) {

        containerLayout.setOnClickListener {

            if (dataBean.expand) {
                listener.onHideChildren(dataBean)
                dataBean.expand = false
                rotationExpandIcon(180f, 0f)
            } else {
                listener.onExpandChildren(dataBean)
                dataBean.expand = true
                rotationExpandIcon(0f, 180f)
            }

        }

        book.setOnCheckedChangeListener { buttonView, isChecked ->
            dataBean.isCheck_b = isChecked
        }
        course.setOnCheckedChangeListener { buttonView, isChecked ->
            dataBean.isCheck_c = isChecked
        }

        book.isChecked = dataBean.isCheck_b
        course.isChecked = dataBean.isCheck_c
        courseName.text = dataBean.courseName
        score.text = dataBean.score
        holdPlace.text = dataBean.holdPlace
        pb.progress = dataBean.tv_last.toInt()
        pb.max = dataBean.tv_capacity.toInt()
        tv_capacity.text = dataBean.tv_capacity
        tv_last.text = dataBean.tv_last
    }

    private fun rotationExpandIcon(from: Float, to: Float) {

        val valueAnimator = ValueAnimator.ofFloat(from, to)//属性动画
        valueAnimator.duration = 500
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { valueAnimator -> iv_expend.rotation = valueAnimator.animatedValue as Float }
        valueAnimator.start()

    }

}

class XParentSelectedViewHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {
    val itemView = itemView

    var courseName = itemView.findViewById(R.id.tv_cn) as TextView
    var score = itemView.findViewById(R.id.tv_s) as TextView
    var teacherName = itemView.findViewById(R.id.tv_tn) as TextView
    var post = itemView.findViewById(R.id.post) as Button
    val containerLayout: CardView = itemView.findViewById(R.id.cardview) as CardView
    var iv_expend = itemView.findViewById(R.id.iv_expend) as ImageView

    fun bindView(data: XuanXiuSelectedBean, listener: ItemClickListener) {
        containerLayout.setOnClickListener {

            if (data.expand) {
                listener.onHideChildren(data)
                data.expand = false
                rotationExpandIcon(180f, 0f)
            } else {
                listener.onExpandChildren(data)
                data.expand = true
                rotationExpandIcon(0f, 180f)
            }

        }
        val cancelPost = data.selectPost
        courseName.text = data.courseName
        score.text = data.score
        teacherName.text = data.teacherName
        post.setOnClickListener {
            val builder = AlertDialog.Builder(XuanXiuActivity.main!!)
            builder.setMessage("你真的要退选此门课程吗?")
            builder.setPositiveButton("确定") { dialog, which -> XuanXiuActivity.main?.presenter?.cancelSelected(cancelPost) }
            builder.setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
            builder.show()
        }
    }

    private fun rotationExpandIcon(from: Float, to: Float) {

        val valueAnimator = ValueAnimator.ofFloat(from, to)//属性动画
        valueAnimator.duration = 500
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { valueAnimator -> iv_expend.rotation = valueAnimator.animatedValue as Float }
        valueAnimator.start()

    }


}

class XChildViewHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {

    val itemView = itemView

    var courseCode = itemView.findViewById(R.id.courseCode) as TextView
    var courseTime = itemView.findViewById(R.id.courseTime) as TextView
    var coursePlace = itemView.findViewById(R.id.coursePlace) as TextView
    var courseProperty = itemView.findViewById(R.id.courseProperty) as TextView
    var studyTime = itemView.findViewById(R.id.studyTime) as TextView
    var last = itemView.findViewById(R.id.last) as TextView
    var teacherName = itemView.findViewById(R.id.teacherName) as TextView
    var capacity = itemView.findViewById(R.id.capacity) as TextView
    var startEnd = itemView.findViewById(R.id.startEnd) as TextView
    var courseClass = itemView.findViewById(R.id.courseClass) as TextView
    var schoolCode = itemView.findViewById(R.id.schoolCode) as TextView

    fun bindView(bean: XuanXiuBean) {
        courseCode.text = bean.courseCode
        courseTime.text = bean.courseTime
        coursePlace.text = bean.coursePlace
        courseProperty.text = bean.courseProperty
        studyTime.text = bean.studyTime
        last.text = bean.last
        teacherName.text = bean.teacherName
        capacity.text = bean.capacity
        startEnd.text = bean.startEnd
        courseClass.text = bean.courseClass
        schoolCode.text = bean.schoolCode
    }
}


class XChildSelectedViewHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {
    val itemView = itemView

    var courseTime = itemView.findViewById(R.id.courseTime) as TextView
    var coursePlace = itemView.findViewById(R.id.coursePlace) as TextView
    var courseProperty = itemView.findViewById(R.id.courseProperty) as TextView
    var studyTime = itemView.findViewById(R.id.studyTime) as TextView
    var startEnd = itemView.findViewById(R.id.startEnd) as TextView
    var courseClass = itemView.findViewById(R.id.courseClass) as TextView
    var schoolCode = itemView.findViewById(R.id.schoolCode) as TextView
    var book = itemView.findViewById(R.id.book) as TextView
    var school = itemView.findViewById(R.id.school) as TextView

    fun bindView(bean: XuanXiuSelectedBean) {
        courseTime.text = bean.courseTime
        coursePlace.text = bean.coursePlace
        courseProperty.text = bean.courseProperty
        studyTime.text = bean.studyTime
        startEnd.text = bean.startEnd
        courseClass.text = bean.courseClass
        schoolCode.text = bean.schoolCode
        book.text = bean.book
        school.text = bean.school

    }

}