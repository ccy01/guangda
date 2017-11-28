package com.example.ccy.tes.presenter

import android.content.Context
import android.widget.Toast
import com.example.ccy.tes.retrofit.Constant
import com.example.ccy.tes.retrofit.RetrofitClient
import com.example.ccy.tes.service.SelectedService
import com.example.ccy.tes.ui.view.XuanXiuView
import com.example.ccy.tes.util.HttpUtil
import com.example.ccy.tes.util.SharedPreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URLEncoder

/**
 * Created by ccy on 2017/11/23.
 */
class XuanXiuPresenter(context: Context, mView: XuanXiuView) {
    val context = context
    val mView = mView
    private var selectedService: SelectedService = SelectedService.seletedService!!
    private val api = RetrofitClient.getInstance(context, "http://202.192.18.182/")

    private var home: String = ""
    private var name: String = ""
    private var link: String = ""
    var util: SharedPreferenceUtil? = null


    var post: Array<String>? = null
    var temp: Array<String>? = null
    var records: Array<String>? = null
    var property: MutableList<String>? = null
    var has: MutableList<String>? = null
    var courseClass: MutableList<String>? = null
    var coursePlace: MutableList<String>? = null
    var courseTime: MutableList<String>? = null

    val hashMap: MutableMap<String, String>
        get() {
            val map = HashMap<String, String>()
            map.put("ddl_kcxz", records!![0])
            map.put("ddl_ywyl", records!![1])
            map.put("ddl_kcgs", records!![2])
            map.put("ddl_xqbs", records!![3])
            map.put("ddl_sksj", records!![4])
            return map
        }



    fun getCourse() {
        util = SharedPreferenceUtil(context.applicationContext, "accountInfo")
        home = HttpUtil.URL_MAIN.replace("XH", util!!.getKeyData("username"))
        name = util!!.getKeyData("name")
        link = "http://202.192.18.182/xf_xsqxxxk.aspx?xh=${Constant.username}&xm=${URLEncoder.encode(name, "gb2312")}&gnmkdm=N121203"

        if (name == "") {
            Toast.makeText(context, "数据出错", Toast.LENGTH_SHORT).show()
            return
        }
        mView.setRefreshing(true)
        api.getXuanXiuCourse(header = home, number = Constant.username!!, name = name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("GB2312"))
                    setArg(s)
                    property = selectedService.getCourseSelectArg(s, 0)
                    has = selectedService.getCourseSelectArg(s, 1)
                    courseClass = selectedService.getCourseSelectArg(s, 2)
                    coursePlace = selectedService.getCourseSelectArg(s, 3)
                    courseTime = selectedService.getCourseSelectArg(s, 4)
                    mView.setList1(selectedService.getXXCourse(s))
                    mView.setList2(selectedService.getXXCourseSelected(s))

                    println("this is check")

                    mView.setRefreshing(false)

                }, { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    mView.setRefreshing(false)
                })
    }

    fun getNextCourse(isFirstPager: Boolean) {

        mView.setRefreshing(true)
        val formParars = setPostParams(post!![0], post!![1], post!![2], post!![3], records!![0], records!![1], records!![2],
                records!![3], records!![4], "", temp!![1], temp!![3], temp!![5], temp!![6])

        formParars.put(if (isFirstPager) "&dpkcmcGrid:btnFirstPage" else "&dpkcmcGrid:btnLastPage", if (isFirstPager) "首页" else "末页")
        mView.setRefreshing(true)
        api.getXuanXiuNextCourse(link, formParars, Constant.username!!, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("GB2312"))
                    setArg(s)
                    if (isFirstPager) {
                        mView.setList1(selectedService.getXXCourse(s))
                    } else {
                        mView.setList2(selectedService.getXXCourseSelected(s))
                    }
                    mView.setRefreshing(false)
                }, { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    mView.setRefreshing(false)
                })

    }

    fun getNextCourse2(isFirstPager: Boolean) {

        val params = setPostParams(post!![0], post!![1], post!![2], post!![3], records!![0], records!![1], records!![2],
                records!![3], records!![4], "", temp!![1], temp!![3], temp!![5], temp!![7])

        if (isFirstPager)
            params.put("dpkcmcGrid:btnNextPage", "下一页")
        else
            params.put("dpDataGrid2:btnNextPage", "下一页")
        mView.setRefreshing(true)
        api.getXuanXiuNextCourse(link, params, Constant.username!!, name, "N121203")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("GB2312"))
                    setArg(s)
                    if (isFirstPager) {
                        mView.addList1(selectedService.getXXCourse(s))

                        mView.setJumpText(temp!![1])
                        mView.setSumPager(temp!![0])
                        mView.setRate(temp!![1] + "/" + temp!![2])
                    } else {
                        mView.setList2(selectedService.getXXCourseSelected(s))
                    }
                    mView.setRefreshing(false)
                }, { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    mView.setRefreshing(false)
                })


    }

    fun courseRefresh(isFirstPager: Boolean) {

        val params = setPostParams(post!![0], post!![1], post!![2], post!![3], records!![0], records!![1], records!![2],
                records!![3], records!![4], "", temp!![1], temp!![3], temp!![5], temp!![7])

        mView.setRefreshing(true)
        api.getXuanXiuNextCourse(link, params, Constant.username!!, name, "N121203")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("GB2312"))
                    setArg(s)
                    if (isFirstPager) {
                        mView.setList1(selectedService.getXXCourse(s))
                    } else {
                        mView.setList2(selectedService.getXXCourseSelected(s))
                    }
                    mView.setRefreshing(false)
                }, { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    mView.setRefreshing(false)
                })


    }

    fun getSelected(arg2: Map<String, String>, arg: String) {

        val params = setPostParams(arg, post!![1], post!![2], post!![3], arg2["ddl_kcxz"]!!, arg2["ddl_ywyl"]!!, arg2["ddl_kcgs"]!!,
                arg2["ddl_xqbs"]!!, arg2["ddl_sksj"]!!, "", temp!![1], temp!![3], temp!![5], temp!![7])

        api.getXuanXiuNextCourse(link, params, Constant.username!!, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("GB2312"))
                    setArg(s)
                    property = selectedService!!.getCourseSelectArg(s, 0)
                    has = selectedService!!.getCourseSelectArg(s, 1)
                    courseClass = selectedService!!.getCourseSelectArg(s, 2)
                    coursePlace = selectedService!!.getCourseSelectArg(s, 3)
                    courseTime = selectedService!!.getCourseSelectArg(s, 4)
                    mView.setList1(selectedService.getXXCourse(s))

                }, { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                })
    }

    fun setJump(arg: String, isFirstPager: Boolean) {

        val params = setPostParams("dpkcmcGrid:txtChoosePage", post!![1], post!![2], post!![3], records!![0], records!![1], records!![2],
                records!![3], records!![4], "", temp!![1], temp!![3], temp!![5], temp!![7])

        if (isFirstPager) {
            params.put("dpkcmcGrid:txtChoosePage", arg)
            params.put("dpDataGrid2:txtChoosePage", temp!![5])
        } else {
            params.put("dpkcmcGrid:txtChoosePage", temp!![1])
            params.put("dpDataGrid2:txtChoosePage", arg)
        }
        api.getXuanXiuNextCourse(link, params, Constant.username!!, name, "N121203")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("GB2312"))
                    setArg(s)
                    if (isFirstPager) {
                        mView.setList1(selectedService.getXXCourse(s))
                    } else {
                        mView.setList2(selectedService.getXXCourseSelected(s))
                    }
                }, { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                })

    }

    fun search(arg: String, isFirstPager: Boolean) {

        val params = setPostParams(post!![0], post!![1], post!![2], post!![3], records!![0], records!![1], records!![2],
                records!![3], records!![4], arg, temp!![1], temp!![3], temp!![5], temp!![7])
        params.put("Button2", "%C8%B7%B6%A8")

        //todo
        api.getXuanXiuNextCourse(link, params, Constant.username!!, name, "N121203")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("GB2312"))
                    setArg(s)
                    mView.setList1(selectedService.getXXCourse(s))

                }, { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()

                })

    }

    fun coursePost() {
        val params = setPostParams(post!![0], post!![1], post!![2], post!![3], records!![0], records!![1], records!![2],
                records!![3], records!![4], "", temp!![1], temp!![3], temp!![5], temp!![7])
        for (map in mView.postArg!!) {
            params.put(map["选课"]!!, "on")
            if (map["教材"] != "")
                params.put(map["教材"]!!, "on")
            params.put("Button1", "++%CC%E1%BD%BB++")
            params.put("dpkcmcGrid:txtChoosePage", mView.lists!![Integer.parseInt(map["count"])].currentPage)
            params.put("dpkcmcGrid:txtPageSize", mView.lists!![Integer.parseInt(map["count"])].txtPageSize)
            params.put("dpDataGrid2:txtChoosePage", mView.lists!![Integer.parseInt(map["count"])].currentPage2)
            params.put("dpDataGrid2:txtPageSize", mView.lists!![Integer.parseInt(map["count"])].txtPageSize2)
        }

        api.getXuanXiuNextCourse(link, params, Constant.username!!, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("GB2312"))
                    mView.postArg = null
                    setArg(s)
                    mView.save()
                    mView.setList1(selectedService.getXXCourse(s))
                    mView.setList2(selectedService.getXXCourseSelected(s))
                    mView.setFirstItem()

                }, {
                    Toast.makeText(context, it.message,Toast.LENGTH_SHORT).show()
                })

    }

    fun cancelSelected(arg: String) {

        val params = setPostParams(arg, post!![1], post!![2], post!![3], records!![0], records!![1], records!![2],
                records!![3], records!![4], "", temp!![1], temp!![3], temp!![5], temp!![7])

        api.getXuanXiuNextCourse(link, params, Constant.username!!, name, "N121203")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("GB2312"))
                    setArg(s)
                    mView.setList2(selectedService.getXXCourseSelected(s))
                }, {

                })
    }

    fun setArg(s: String) {
        records = selectedService.getRecords(s)
        post = selectedService.getPost(s)
        temp = selectedService.getMsg(s)

    }

    fun setPostParams(vararg arg: String): HashMap<String, String> {
        val params = HashMap<String, String>()
        params.put("__EVENTTARGET", URLEncoder.encode(arg[0], "GB2312"))
        params.put("__EVENTARGUMENT", URLEncoder.encode(arg[1], "GB2312"))
        params.put("__VIEWSTATE", URLEncoder.encode(arg[2], "GB2312"))
        params.put("__VIEWSTATEGENERATOR", URLEncoder.encode(arg[3], "GB2312"))
        params.put("ddl_kcxz", URLEncoder.encode(arg[4], "GB2312"))
        params.put("ddl_ywyl", URLEncoder.encode(arg[5], "GB2312"))
        params.put("ddl_kcgs", URLEncoder.encode(arg[6], "GB2312"))
        params.put("ddl_xqbs", URLEncoder.encode(arg[7], "GB2312"))
        params.put("ddl_sksj", URLEncoder.encode(arg[8], "GB2312"))
        params.put("TextBox1", URLEncoder.encode(arg[9], "GB2312"))
        params.put("dpkcmcGrid:txtChoosePage", URLEncoder.encode(arg[10], "GB2312"))
        params.put("dpkcmcGrid:txtPageSize", URLEncoder.encode(arg[11], "GB2312"))
        params.put("dpDataGrid2:txtChoosePage", URLEncoder.encode(arg[12], "GB2312"))
        params.put("dpDataGrid2:txtPageSize", URLEncoder.encode(arg[13], "GB2312"))
        return params
    }
}