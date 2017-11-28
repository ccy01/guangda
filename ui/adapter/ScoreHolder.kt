package com.example.ccy.tes.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.ScoreBean

/**
 * Created by ccy on 2017/11/11.
 */
class ScoreHolder(val context: Context, itemView: View) : BaseViewHolder(itemView) {
    val itemView = itemView

    val courseName = itemView.findViewById(R.id.courseNmae) as TextView
    val score = itemView.findViewById(R.id.score) as TextView
    val courseCode = itemView.findViewById(R.id.courseCode) as TextView
    val courseProperty = itemView.findViewById(R.id.courseProperty) as TextView
    val credit = itemView.findViewById(R.id.credit) as TextView
    val point = itemView.findViewById(R.id.point) as TextView

    fun bindView(data: ScoreBean) {

        courseName.text = data.courseName
        score.text = data.score

        courseCode.text = data.courseCode
        courseProperty.text = data.courseProperty
        credit.text = data.credit
        point.text = data.point

    }

    private fun rotationExpandIcon(from: Float, to: Float) {
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            val valueAnimator = ValueAnimator.ofFloat(from, to)//属性动画
            valueAnimator.duration = 500
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.addUpdateListener { valueAnimator -> iv_expend.rotation = valueAnimator.animatedValue as Float }
            valueAnimator.start()
        }*/
    }


}

