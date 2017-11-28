package com.example.ccy.tes.ui.activity

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.LinearLayout.LayoutParams
import com.example.ccy.tes.R
import com.example.ccy.tes.model.bean.CourseBean
import com.example.ccy.tes.service.CourseService
import com.example.ccy.tes.util.CommonUtil
import com.example.ccy.tes.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.supertable.*
import java.text.SimpleDateFormat
import java.util.*

class CourseActivity : AppCompatActivity() {

    private val mCourse: List<CourseBean> = CourseService.getInstance()!!.findAll()
    private val bg = intArrayOf(R.drawable.ic_course_bg_1, R.drawable.ic_course_bg_2, R.drawable.ic_course_bg_3, R.drawable.ic_course_bg_4,
            R.drawable.ic_course_bg_5, R.drawable.ic_course_bg_6, R.drawable.ic_course_bg_7, R.drawable.ic_course_bg_11)
    private val mutBg = intArrayOf(R.drawable.ic_course_bg_multi_1, R.drawable.ic_course_bg_multi_2, R.drawable.ic_course_bg_multi_3, R.drawable.ic_course_bg_multi_4,
            R.drawable.ic_course_bg_multi_5, R.drawable.ic_course_bg_multi_6, R.drawable.ic_course_bg_multi_7, R.drawable.ic_course_bg_multi_11)


    val dayArr = IntArray(7)
    // 每天有多少节课
    private val mMaxCourse: Int = 12
    // 一共有多少周
    private val mMaxWeek: Int = 19
    // 现在是第几周
    private var mNowWeek: Int = 12
    // 左边一节课的高度
    private var mLeftHeight: Float = 0.toFloat()
    // 左边一节课的宽度
    private var mLeftWidth: Float = 0.toFloat()

    private var mChangeWeek: TextView? = null
    private var mLeftNo: LinearLayout? = null
    private var mMonday: FrameLayout? = null
    private var mTuesday: FrameLayout? = null
    private var mWednesday: FrameLayout? = null
    private var mThursday: FrameLayout? = null
    private var mFriday: FrameLayout? = null
    private var mSaturday: FrameLayout? = null
    private var mWeekend: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.supertable)
        initCtrl()
        initData()
        drawLeftNo()
        drawNowWeek()
        drawCourse()
    }

    private fun initCtrl() {
        mChangeWeek = findViewById(R.id.changeWeek) as TextView
        mLeftNo = findViewById(R.id.leftNo) as LinearLayout
        mMonday = findViewById(R.id.monday) as FrameLayout
        mTuesday = findViewById(R.id.tuesday) as FrameLayout
        mWednesday = findViewById(R.id.wednesday) as FrameLayout
        mThursday = findViewById(R.id.thursday) as FrameLayout
        mFriday = findViewById(R.id.firday) as FrameLayout
        mSaturday = findViewById(R.id.saturday) as FrameLayout
        mWeekend = findViewById(R.id.weekend) as FrameLayout
    }

    /**
     * 初始化所有数据
     */
    private fun initData() {
        mChangeWeek!!.setOnClickListener { v -> showChangeWeekDlg(v) }
        val util = SharedPreferenceUtil(applicationContext, "week")
        mNowWeek = util.getKeyIntData("week")
        var day = util.getKeyData("day")
        val date = Date()
        val dateFm = SimpleDateFormat("EEEE MM dd")
        val temp = dateFm.format(date).split(" ")
        val d = if (day == "") temp[2].toInt() else day.toInt()
        mNowWeek += (temp[2].toInt() - d) / 7
        val a = Calendar.getInstance()
        a.set(Calendar.DATE, 1)
        a.roll(Calendar.DATE, -1)
        val maxDate: Int = a.get(Calendar.DATE)

        val week = arrayListOf(mon, tue, wed, thu, fir, sat, sun)
        month.text = temp[1] + "\n月"
        val s = temp[0].substring(2, 3)
        var k = 0
        for (i in 0 until week.size) {
            if (s == week[i].text.substring(1, 2)) {
                week[i].text = "${week[i].text}\n${temp[2]}日"
                week[i].setBackgroundResource(R.drawable.ic_course_bg_1)
                k = i
                break
            }
        }
        for (i in k + 1 until week.size) {
            var d = (temp[2].toInt() + (i - 1)) % maxDate
            if (d == 0) d = maxDate

            week[i].text = "${week[i].text}\n${d}日"
        }
        for (i in 0 until k) {
            var d = (temp[2].toInt() - (k - i)) % maxDate
            if (d == 0) d = maxDate
            week[i].text = "${week[i].text}\n${d}日"
        }


    }

    private fun drawLeftNo() {
        mLeftHeight = resources.getDimension(R.dimen.left_height)
        mLeftWidth = resources.getDimension(R.dimen.left_width)
        val lp = LinearLayout.LayoutParams(
                mLeftWidth.toInt(), mLeftHeight.toInt())
        for (i in 1..mMaxCourse) {
            val tv = TextView(this)
            tv.text = i.toString()
            tv.gravity = Gravity.CENTER
            tv.setTextColor(resources.getColor(R.color.font))
            tv.setBackgroundResource(R.drawable.ic_course_bg_hui)
            lp.topMargin = 2
            lp.bottomMargin = 2
            mLeftNo!!.addView(tv, lp)
        }
    }


    private fun drawCourse() {

        var ll: FrameLayout?
        for (course in mCourse) {
            ll = when (course.dayOfWeek) {
                1 -> mMonday
                2 -> mTuesday
                3 -> mWednesday
                4 -> mThursday
                5 -> mFriday
                6 -> mSaturday
                7 -> mWeekend
                else -> null
            }
            var background: Int
            val temp = course.courseTime!!.split(",")
            var inWeek = false
            for (e in temp) {
                if (e.length > 2) {
                    val t = e.split("-")
                    inWeek = mNowWeek in t[0].toInt()..t[1].toInt()
                    if (inWeek)
                        break
                } else {
                    inWeek = e.toInt() == mNowWeek
                    if (inWeek)
                        break
                }
            }
            course.inWeek = if (inWeek) "t" else "f"
            background = if (inWeek) bg[CommonUtil.getRandom(bg.size - 1)] else R.drawable.ic_course_bg_hui
            val tv = setTv(inWeek, course, background, ll!!)
            println(course.toString())
            var view: TextView?
            var c: CourseBean?
            for (index in 0 until ll.childCount) {
                view = ll.getChildAt(index) as TextView
                c = view.tag as CourseBean
                if (c.startSection == course.startSection && !(c === course)) {
                    println(c)
                    background = mutBg[CommonUtil.getRandom(bg.size - 1)]
                    if (c.inWeek == "t") {
                        view.visibility = View.VISIBLE
                        view.setBackgroundResource(background)
                        tv.visibility = View.INVISIBLE
                    }
                    if (inWeek) {
                        view.visibility = View.INVISIBLE
                        tv.setBackgroundResource(background)
                    }
                    if (!inWeek && c.inWeek == "f") {
                        background = R.drawable.ic_course_bg_hui_multi
                        val t1 = c.courseTime!!.split(",", "-").map { it.toInt() }.max()
                        val t2 = (course.courseTime as String).split(",", "-").map { it.toInt() }.max()
                        if (t1!! < t2!!) {
                            tv.setBackgroundResource(background)
                            view.visibility = View.INVISIBLE
                        } else {
                            view.visibility = View.VISIBLE
                            view.setBackgroundResource(background)
                            tv.visibility = View.INVISIBLE

                        }
                    }
                    break
                }
            }

        }
    }

    fun setTv(inWeek: Boolean, course: CourseBean, background: Int, ll: FrameLayout): TextView {
        val tv = TextView(this)
        tv.textSize = 13f
        val tp = tv.paint
        tp.isFakeBoldText = true
        val text = if (!inWeek) "[非本周]" else ""
        tv.text = (text + course.courseName + "\n@" + course.classsroom)
        tv.setBackgroundResource(background)
        tv.tag = course
        val color = if (!inWeek) resources.getColor(R.color.color_gray) else resources.getColor(R.color.course_font_color)
        tv.setTextColor(color)
        tv.setOnClickListener { v ->
            val tag = v.tag as CourseBean
            showCouseDetails(tag)
        }
        val lp = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, (course.endSection - course.startSection + 1) * mLeftHeight.toInt())
        lp.topMargin = (course.startSection - 1) * (mLeftHeight.toInt() + 4)
        ll.addView(tv, lp)
        return tv

    }


    /**
     * 绘制当前周
     */
    private fun drawNowWeek() {
        mChangeWeek!!.text = "第" + mNowWeek + "周"
    }

    fun showCouseDetails(bean: CourseBean) {
        val view = layoutInflater.inflate(R.layout.details_layout, null)
        val builder = android.app.AlertDialog.Builder(this)
        var textView = view.findViewById(R.id.name) as TextView
        textView.text = bean.courseName
        textView = view.findViewById(R.id.type) as TextView
        textView.text = "${bean.startYear}-${bean.endYear}年第${bean.semester}学期"
        textView = view.findViewById(R.id.teacher) as TextView
        textView.text = (bean.teacher)
        textView = view.findViewById(R.id.address) as TextView
        textView.text = bean.classsroom
        textView = view.findViewById(R.id.week) as TextView
        textView.text = bean.courseTime + "周"
        builder.setView(view)
        builder.setTitle("课程详细")
        builder.setPositiveButton("确定", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })
        val dialog = builder.create()
        dialog .show()
    }

    /**
     * 显示切换当前周的窗口
     */
    fun showChangeWeekDlg(v: View) {
        val view = View.inflate(this, R.layout.changweek_layout, null)
        val weekList = view.findViewById(R.id.weekList) as ListView

        val strList = (1..mMaxWeek).map { if (it == 1) "设置本周" else "第" + (it - 1) + "周" }
        val adapter = ArrayAdapter(this,
                R.layout.item, strList)

        weekList.adapter = adapter
        view.measure(0, 0)
        val pop = PopupWindow(view, 350, 600, true)
        pop.setBackgroundDrawable(ColorDrawable(0x00000000))
        val xOffSet = -(pop.width - v.width) / 2
        pop.showAsDropDown(v, xOffSet, 0)

        weekList.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            var index = position
            var t = mNowWeek
            if (position == 0)
                index = showSetWeekDialog()
            mNowWeek = if(index ==0) mNowWeek else index
            pop.dismiss()
            drawNowWeek()
            removeView(mMonday)
            removeView(mTuesday)
            removeView(mWednesday)
            removeView(mFriday)
            removeView(mThursday)
            removeView(mSaturday)
            removeView(mWeekend)
            drawCourse()
        }
    }

    fun saveWeek(week: Int) {
        val util = SharedPreferenceUtil(applicationContext, "week")
        util.setKeyData("week", week)
        val date = Date()
        val dateFm = SimpleDateFormat("EEEE MM dd")
        val temp = dateFm.format(date).split(" ")
        util.setKeyData("day", temp[2])
    }

    fun showSetWeekDialog(): Int {
        val view = layoutInflater.inflate(R.layout.week_dialog, null)
        val builder = android.app.AlertDialog.Builder(this)
        val et_week = view.findViewById(R.id.et_set_week) as EditText
        var day = 0
        builder.setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
            day = et_week.text.toString().toInt()
            saveWeek(day)
            dialog.dismiss()
        })
        builder.setNegativeButton("取消", DialogInterface.OnClickListener{dialog, which ->
            dialog.dismiss()
            day= mNowWeek
        })
        builder.setCancelable(false)
        builder.setView(view)
        builder.setTitle("设置当前周")
        val dialog = builder.create()
        dialog.show()
        return day
    }

    fun removeView(ll: FrameLayout?) {
        ll?.removeAllViews()
    }

}
