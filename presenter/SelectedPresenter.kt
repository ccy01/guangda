package com.example.ccy.tes.presenter

import android.R
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.ccy.tes.model.bean.SelectedBean
import com.example.ccy.tes.retrofit.ApiService
import com.example.ccy.tes.retrofit.Constant
import com.example.ccy.tes.retrofit.RetrofitClient
import com.example.ccy.tes.service.SelectedService
import com.example.ccy.tes.ui.view.SelectedView
import com.example.ccy.tes.util.HttpUtil
import com.example.ccy.tes.util.SharedPreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

/**
 * Created by ccy on 2017/11/11.
 */
class SelectedPresenter(context: Context, view: SelectedView) {
    val mContext = context
    val mView = view
    var api: ApiService? = null
    val ret = HashMap<String, Int>()
    var years: MutableList<String>? = null
    var term: MutableList<String>? = null
    var first = true
    val selectedService = SelectedService.seletedService
    var temp: Array<String>? = null

    private var home: String = ""
    private var name: String = ""
    private var link: String = ""
    var util: SharedPreferenceUtil? = null

    fun getHasSelected() {
        util = SharedPreferenceUtil(mContext.applicationContext, "accountInfo")
        home = HttpUtil.URL_MAIN.replace("XH", util!!.getKeyData("username"))
        name = util!!.getKeyData("name")
        link = "http://202.192.18.182/xsxkqk.aspx?xh=${Constant.username}&xm=${URLEncoder.encode(name, "gb2312")}&gnmkdm=N121615"

        if (name == "") {
            Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show()
            return
        }
        mView.setRefreshing(true)
        api = RetrofitClient.getInstance(mContext, "http://202.192.18.182/")
        api!!.getSelectedCourse(home, Constant.username!!, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    try {
                        val s = String(body.bytes(), charset("GB2312"))
                        if (first) {
                            years = selectedService?.getCourseSelectArg(s, 0)?.filter { it > "20${Constant.username!!.substring(0, 2)}" } as MutableList
                            term = selectedService?.getCourseSelectArg(s, 1)
                            setSpinner(years, mView.sp_year!!, "year")
                            setSpinner(term, mView.sp_term!!, "term")
                            temp = selectedService.getPost(s)
                            mView.sp_year?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }

                                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                    getOtherCouser("ddlXN",years!![position],term!![ret["term"]!!])
                                }
                            }
                            mView.sp_term?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }

                                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                    getOtherCouser("ddlXQ",years!![ret["year"]!!],term!![position])
                                }
                            }
                            first = false

                        }else{
                            temp = selectedService!!.getPost(s)
                            years = selectedService?.getCourseSelectArg(s, 0)?.filter { it > "20${Constant.username!!.substring(0, 2)}" } as MutableList
                            term = selectedService?.getCourseSelectArg(s, 1)
                            setRet(years,"year")
                            setRet(term,"term")
                            mView.sp_year?.setSelection(ret["year"]!!)
                            mView.sp_term?.setSelection(ret["term"]!!)
                        }


                        mView.setList(getSelected(s))
                        mView.setAdapter()
                    } catch (e: UnsupportedEncodingException) {
                        Toast.makeText(mContext, e.message, Toast.LENGTH_SHORT).show()
                    } finally {
                        mView.setRefreshing(false)
                    }

                }, { error ->
                    Toast.makeText(mContext, error.message , Toast.LENGTH_SHORT).show()
                    mView.setRefreshing(false)
                })

    }

    fun getOtherCouser(vararg arg: String) {

        val params = setPostParams(arg[0], temp!![1], temp!![2], temp!![3], arg[1], arg[2])
        mView.setRefreshing(true)
        api!!.getSelectedCourse(link, params, Constant.username!!, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("GB2312"))
                    temp = selectedService!!.getPost(s)
                    years = selectedService?.getCourseSelectArg(s, 0)?.filter { it > "20${Constant.username!!.substring(0, 2)}" } as MutableList
                    term = selectedService?.getCourseSelectArg(s, 1)
                    setRet(years,"year")
                    setRet(term,"term")
                    mView.sp_year?.setSelection(ret["year"]!!)
                    mView.sp_term?.setSelection(ret["term"]!!)

                    mView.setList(getSelected(s))
                    mView.setAdapter()
                    mView.setRefreshing(false)
                }, { error ->
                    Toast.makeText(mContext, error.message, Toast.LENGTH_SHORT).show()
                    mView.setRefreshing(false)
                })
    }


    fun getSelected(content: String): MutableList<SelectedBean> {//得到已经选了的课程
        var bean: SelectedBean
        val groupList = ArrayList<SelectedBean>()
        val doc = Jsoup.parse(content)
        val rowRegex = "div.main_box div.mid_box span.formbox table.datelist "//挑选条件
        val rowElements = doc.select(rowRegex)
        val table = rowElements[0]
        val tr = table.select("tr")
        val first = tr[0].select("td")

        for (i in 1 until tr.size) {
            bean = SelectedBean()
            val td = tr[i].select("td")
            bean.courseCode = first[0].text() + ":" + td[0].text()//课程代码
            bean.courseName = td[1].text()//课程名称
            bean.type = 0
            bean.ID = i
            bean.courseProperty = first[2].text() + ":" + td[2].text()//课程性质
            bean.score = first[3].text() + ":" + td[3].text()//学分
            bean.point = first[5].text() + ":" + td[5].text()//绩点
            bean.teacherName = td[4].text()
            bean.studyTime = first[6].text() + ":" + td[6].text()//绩点
            bean.coursePalce = first[8].text() + ":" + td[8].text()
            val title = td[7].attr("title")
            if (title != "") {
                bean.courseTime = td[7].attr("title")

            } else {
                bean.courseTime = td[7].text()

            }
            bean.book = first[9].text() + ":" + td[9].text()

            groupList.add(bean)
        }

        return groupList

    }


    fun setSpinner(list: MutableList<String>?, spinner: Spinner, arg: String) {

        setRet(list,arg)
        val adapter = ArrayAdapter(mContext, R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(ret[arg]!!)
    }

    fun setPostParams(vararg arg: String): HashMap<String, String> {
        val params = HashMap<String, String>()
        params.put("__EVENTTARGET", URLEncoder.encode(arg[0], "GB2312"))
        params.put("__EVENTARGUMENT", URLEncoder.encode(arg[1], "GB2312"))
        params.put("__VIEWSTATE", URLEncoder.encode(arg[2], "GB2312"))
        params.put("__VIEWSTATEGENERATOR", URLEncoder.encode(arg[3], "GB2312"))
        params.put("ddlXN", URLEncoder.encode(arg[4], "GB2312"))
        params.put("ddlXQ", URLEncoder.encode(arg[5], "GB2312"))
        return params
    }

    fun setRet(list: MutableList<String>?,arg: String) {
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
    }


}
