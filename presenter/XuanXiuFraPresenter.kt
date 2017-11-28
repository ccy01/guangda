package com.example.ccy.tes.presenter

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.ccy.tes.R
import com.example.ccy.tes.ui.activity.XuanXiuActivity
import com.example.ccy.tes.ui.fragment.XuanXiuFragment
import com.example.ccy.tes.ui.view.XuanXiuFraView
import java.util.*
import java.util.regex.Pattern

/**
 *
 * Created by ccy on 2017/11/24.
 */
class XuanXiuFraPresenter(val context: Context, val mView: XuanXiuFraView) {

    var property:Spinner? = null
    var coursePlace :Spinner? = null
    var courseClass :Spinner? = null
    var has :Spinner? = null
    var courseTime:Spinner? = null
    var et_search: EditText? = null
    var btn_search : Button? = null

    private val ret = HashMap<String, Int>()
    private val isFrist = booleanArrayOf(true, true, true, true, true)


    fun setItemSelectedListener(index: Int, map: Map<String, String>?, direction: Spinner, arg: String, check: (Int) -> Boolean, doOne: () -> Unit, doTwo: (Int) -> Unit) {
        direction.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (isFrist[index]) {
                    isFrist[index] = false
                } else {
                    if (check(position))
                        doOne()
                    else
                        doTwo(position)
                    XuanXiuActivity.main?.presenter?.getSelected(map!!, arg)
                    println("courseSelected will call")
                   // dialog?.cancel()
                    for (i in isFrist.indices) {
                        isFrist[i] = true
                    }
                }
            }
        }

    }


    fun setRes(list: MutableList<String>?, spinner: Spinner, arg: String) {
        if (list == null)
            return
        var i = 0
        while (i < list.size) {
            val p = Pattern.compile("selected")
            val matcher = p.matcher(list[i])
            if (matcher.find()) {
                list[i] = list[i].replace("selected", "")
                ret.put(arg, i)
                break
            }
            i++
        }
        if (i == list.size) {
            i = ret[arg]!!
        }
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(i)
    }

    fun setDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.select_fitler_dialog,null)
         property = view.findViewById(R.id.property) as Spinner
         coursePlace = view.findViewById(R.id.coursePlace) as Spinner
         courseClass = view.findViewById(R.id.courseClass) as Spinner
         has = view.findViewById(R.id.has) as Spinner
         courseTime = view.findViewById(R.id.courseTime) as Spinner
         et_search = view.findViewById(R.id.et_search) as EditText
         btn_search = view.findViewById(R.id.btn_search) as Button
        val dialog = AlertDialog.Builder(context)
        dialog.setCancelable(false)
        dialog.setTitle("筛选条件")
        dialog.setNegativeButton("取消") { dialog, which ->
            for (i in isFrist.indices) {
                isFrist[i] = true
            }
            dialog.dismiss()
        }
        btn_search?.setOnClickListener {
            val arg = et_search?.text.toString().trim { it <= ' ' }
            if (arg != "") {
                XuanXiuActivity.main?.presenter?.search(arg, (mView as XuanXiuFragment).isFirstPager)
               // this.dialog?.cancel()
                for (i in isFrist.indices) {
                    isFrist[i] = true
                }
            }
        }

        dialog.setView(view)
        dialog.show()

    }

    fun showDialog(pro: MutableList<String>, cclass: MutableList<String>, cplace: MutableList<String>, h: MutableList<String>, cTime: MutableList<String>) {

        setDialog()
        val map = XuanXiuActivity.main?.presenter?.hashMap
        setItemSelectedListener(0, map, property!!, "ddl_kcxz", { pro[it] == "默认" }, { map?.put("ddl_kcxz", "") }, { map?.put("ddl_kcxz", pro[it]) })
        setItemSelectedListener(1, map, coursePlace!!, "ddl_xqbs", { cplace[it] == "大学城" }, { map?.put("ddl_xqbs", "1") }, { map?.put("ddl_xqbs", "0") })
        setItemSelectedListener(2, map, courseTime!!, "ddl_sksj", { cTime[it] == "默认" }, { map?.put("ddl_sksj", "") }, { map?.put("ddl_sksj", cTime[it]) })
        setItemSelectedListener(3, map, has!!, "ddl_ywyl", { h[it] == "默认" }, { map?.put("ddl_ywyl", "") }, { map?.put("ddl_ywyl", h[it]) })
        setItemSelectedListener(4, map, courseClass!!, "ddl_kcgs", { cclass[it] == "默认" }, { map?.put("ddl_kcgs", "") }, { map?.put("ddl_kcgs", cclass[it]) })

        setRes(pro, property!!, "pro")
        setRes(cclass, courseClass!!, "cclass")
        setRes(cplace, coursePlace!!, "cplace")
        setRes(h, has!!, "h")
        setRes(cTime, courseTime!!, "cTime")



    }
}