package com.example.ccy.tes.ui.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.support.v7.widget.CardView
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.*
import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.PhysicalBean


/**
 * Created by ccy on 2017/11/10.
 */

class ParentViewHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {

    val itemView = itemView

    var iv_expend = itemView.findViewById(R.id.iv_expend) as ImageView
    val containerLayout: CardView = itemView.findViewById(R.id.phy_group) as CardView
    var book: CheckBox = itemView.findViewById(R.id.book) as CheckBox
    var course: CheckBox = itemView.findViewById(R.id.select) as CheckBox
    var more: TextView = itemView.findViewById(R.id.tv_m) as TextView
    var teacherName: TextView = itemView.findViewById(R.id.tv_tn) as TextView
    var pb: ProgressBar = itemView.findViewById(R.id.pb_dis) as ProgressBar
    var tv_capacity: TextView = itemView.findViewById(R.id.tv_capacity) as TextView
    var tv_last: TextView = itemView.findViewById(R.id.tv_last) as TextView

    fun bindView(dataBean: PhysicalBean, listener: ItemClickListener) {

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

        book.setOnCheckedChangeListener { uttonView, isChecked ->
            dataBean.isCheck_b = isChecked
        }
        course.setOnCheckedChangeListener { buttonView, isChecked ->
            dataBean.isCheck_c = isChecked
        }

        book.isChecked = dataBean.isCheck_b
        course.isChecked = dataBean.isCheck_c
        more.text = dataBean.more
        teacherName.text = dataBean.teacherName
        val max = dataBean.tv_capacity.toInt()
        val last = dataBean.tv_last.toInt()
        pb.progress = last
        pb.max = max
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

class ParentSelectedViewHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {
    val itemView = itemView

    val containerLayout: CardView = itemView.findViewById(R.id.cardview) as CardView
    var iv_expend = itemView.findViewById(R.id.iv_expend) as ImageView
    var teacherName: TextView = itemView.findViewById(R.id.tv_tn) as TextView
    var more: TextView = itemView.findViewById(R.id.tv_m) as TextView
    var place: TextView = itemView.findViewById(R.id.tv_p) as TextView

    fun bindView(data: PhysicalBean, listener: ItemClickListener) {
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
        more.text = data.more
        teacherName.text = data.teacherName
        place.text = data.coursePlace
    }

    private fun rotationExpandIcon(from: Float, to: Float) {

        val valueAnimator = ValueAnimator.ofFloat(from, to)//属性动画
        valueAnimator.duration = 500
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { valueAnimator -> iv_expend.rotation = valueAnimator.animatedValue as Float }
        valueAnimator.start()

    }


}

class ChildViewHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {

    val itemView = itemView

    var courseName: TextView = itemView.findViewById(R.id.courseName) as TextView
    var courseTime: TextView = itemView.findViewById(R.id.courseTime) as TextView
    var coursePlace: TextView = itemView.findViewById(R.id.coursePlace) as TextView
    var score: TextView = itemView.findViewById(R.id.score) as TextView
    var studyTime: TextView = itemView.findViewById(R.id.studyTime) as TextView
    var last: TextView = itemView.findViewById(R.id.last) as TextView
    var selected: TextView = itemView.findViewById(R.id.selected) as TextView
    var capacity: TextView = itemView.findViewById(R.id.capacity) as TextView
    var phone: TextView = itemView.findViewById(R.id.phone) as TextView

    fun bindView(bean: PhysicalBean) {
        courseName.text = bean.courseName
        courseTime.text = bean.courseTime
        coursePlace.text = bean.coursePlace
        score.text = bean.score
        studyTime.text = bean.studyTime
        last.text = bean.last
        selected.text = bean.selected
        capacity.text = bean.capacity
        phone.text = bean.phone

    }
}


class ChildSelectedViewHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {
    val itemView = itemView

    var courseName: TextView = itemView.findViewById(R.id.courseName) as TextView
    var courseTime: TextView = itemView.findViewById(R.id.courseTime) as TextView
    var score: TextView = itemView.findViewById(R.id.score) as TextView
    var studyTime: TextView = itemView.findViewById(R.id.studyTime) as TextView
    var courseCode: TextView = itemView.findViewById(R.id.courseCode) as TextView
    var book: TextView = itemView.findViewById(R.id.book) as TextView

    fun bindView(bean: PhysicalBean) {
        courseName.text = bean.courseName
        courseTime.text = bean.courseTime
        courseCode.text = bean.courseCode
        score.text = bean.score
        studyTime.text = bean.studyTime
        book.text = bean.phone

    }

}