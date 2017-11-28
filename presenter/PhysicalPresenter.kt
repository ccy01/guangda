package com.example.ccy.tes.presenter

import android.content.Context
import android.widget.Toast
import com.example.ccy.tes.retrofit.Constant
import com.example.ccy.tes.retrofit.RetrofitClient
import com.example.ccy.tes.service.SelectedService
import com.example.ccy.tes.ui.activity.MainActivity
import com.example.ccy.tes.ui.view.PhysicalView
import com.example.ccy.tes.util.HttpUtil
import com.example.ccy.tes.util.SharedPreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.UnsupportedEncodingException

/**
 * Created by ccy on 2017/11/10.
 */
class PhysicalPresenter(context: Context, view: PhysicalView) {
    val mContext = context
    val mView = view
    val selectedService = SelectedService.seletedService


    fun getCourse() {
        val util = SharedPreferenceUtil(mContext.applicationContext, "accountInfo")
        val home = HttpUtil.URL_MAIN.replace("XH", util.getKeyData("username"))
        val name = util.getKeyData("name")
        if(name == ""){
            Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show()
            return
        }
        mView.setRefreshing(true)

        val api = RetrofitClient.getInstance(mContext, "http://202.192.18.182/")
        api.getPhysicalCourse(home, Constant.username!!, name, "N121205")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ body ->
                    try {
                        val s = String(body.bytes(), charset("GB2312"))
                        mView.setListOne(selectedService!!.getCourse(s))
                        mView.setListTwo(selectedService!!.getCourseSelected(s))
                        mView.setAdapter()
                        mView.save()
                        MainActivity.main!!.getmAdapter()!!.notifyDataSetChanged()
                        mView.setSelectedAdapter()
                        mView.setSize()

                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    } finally {
                        mView.setRefreshing(false)

                    }
                },{
                    error ->
                    Toast.makeText(mContext,error.message,Toast.LENGTH_SHORT).show()
                    mView.setRefreshing(false)
                })
    }
}