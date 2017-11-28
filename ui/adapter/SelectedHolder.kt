package com.example.ccy.tes.ui.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.support.v7.widget.CardView
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.SelectedBean

/**
 * Created by ccy on 2017/11/11.
 */
class SelectedParentHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {
    val itemView = itemView

    val containerLayout: CardView = itemView.findViewById(R.id.cardview) as CardView
    var iv_expend = itemView.findViewById(R.id.iv_expend) as ImageView
    var teacherName: TextView = itemView.findViewById(R.id.tv_tn) as TextView
    var courseName: TextView = itemView.findViewById(R.id.tv_m) as TextView
    var point: TextView = itemView.findViewById(R.id.tv_p) as TextView

    fun bindView(data: SelectedBean, listener: ItemClickListener) {
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

        courseName.text = data.courseName
        teacherName.text = data.teacherName
        point.text = data.point
    }

    private fun rotationExpandIcon(from: Float, to: Float) {

        val valueAnimator = ValueAnimator.ofFloat(from, to)//属性动画
        valueAnimator.duration = 500
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { valueAnimator -> iv_expend.rotation = valueAnimator.animatedValue as Float }
        valueAnimator.start()
    }


}

class SelectedChildHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {

    val itemView = itemView

    var courseProperty: TextView = itemView.findViewById(R.id.courseName) as TextView
    var courseTime: TextView = itemView.findViewById(R.id.courseTime) as TextView
    var score: TextView = itemView.findViewById(R.id.score) as TextView
    var studyTime: TextView = itemView.findViewById(R.id.studyTime) as TextView
    var book: TextView = itemView.findViewById(R.id.book) as TextView
    var courseCode: TextView = itemView.findViewById(R.id.courseCode) as TextView


    fun bindView(bean: SelectedBean) {
        courseProperty.text = bean.courseProperty
        courseTime.text = bean.courseTime
        book.text = bean.book
        score.text = bean.score
        studyTime.text = bean.studyTime
        courseCode.text = bean.courseCode

    }
}

