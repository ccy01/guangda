package com.example.ccy.tes.service

import com.example.ccy.tes.model.bean.PhysicalBean
import com.example.ccy.tes.model.bean.XuanXiuSelectedBean
import com.example.ccy.tes.model.bean.XuanXiuBean

import org.jsoup.Jsoup

import java.util.ArrayList
import java.util.regex.Pattern

/**
 * Created by ccy on 2017/6/27.
 */

class SelectedService private constructor() {

    fun getCourse(content: String): MutableList<PhysicalBean> {
        val nodes = ArrayList<PhysicalBean>()
        val doc = Jsoup.parse(content)
        val rowRegex = "div.main_box div.mid_box span.formbox table.datelist "//挑选条件
        val rowElements = doc.select(rowRegex)
        val table = rowElements[0]
        val tr = table.select("tr")
        if (tr.size > 0) {
            val first = tr[0].select("td")
            for (i in 1 until tr.size) {
                val td = tr[i].select("td")

                val node = PhysicalBean()
                node.type = 0
                node.ID = i
                node.courseName = first[0].text() + ":" + td[0].text()//课程代码
                node.studyTime = first[5].text() + ":" + td[5].text()//课程名称
                node.teacherName = td[1].text()//课程性质
                node.coursePlace = first[3].text() + ":" + td[3].text()//学分
                node.score = first[4].text() + ":" + td[4].text()//绩点
                node.capacity = first[6].text() + ":" + td[6].text()//绩点
                node.tv_capacity = td[6].text()
                node.tv_last = td[8].text()
                node.selected = first[7].text() + ":" + td[7].text()//绩点
                node.last = first[8].text() + ":" + td[8].text()//绩点
                if (td[11].text().indexOf("，") != -1) {
                    val t = td[11].text().split("，".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    node.more = t[0]//绩点
                    node.phone = t[1]
                } else {
                    val t = td[11].text().split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    node.more = t[0]//绩点
                    node.phone = t[1]
                }
                node.courseTime = first[2].text() + ":" + td[2].text()// 成绩
                nodes.add(node)
            }
        }

        return nodes
    }

    fun getCourseSelected(content: String): MutableList<PhysicalBean> {
        val nodes = ArrayList<PhysicalBean>()
        val doc = Jsoup.parse(content)
        val rowRegex = "div.main_box div.mid_box span.formbox table.datelist "//挑选条件
        val rowElements = doc.select(rowRegex)
        val table = rowElements[1]
        val tr = table.select("tr")
        if (tr.size > 0) {
            val first = tr[0].select("td")
            for (i in 1 until tr.size) {
                val td = tr[i].select("td")
                val node = PhysicalBean()
                node.type = 0
                node.ID = i
                node.courseCode = first[0].text() + ":" + td[0].text()//课程代码
                node.studyTime = first[4].text() + ":" + td[4].text()//课程名称
                node.teacherName = td[2].text()//课程性质
                node.more = td[7].text()
                node.courseTime = first[5].text() + ":" + td[5].text()
                node.phone = first[8].text() + ":" + td[8].text()
                node.courseName = first[1].text() + ":" + td[1].text()
                node.coursePlace = first[6].text() + ":" + td[6].text()//学分
                node.score = first[3].text() + ":" + td[3].text()//绩点
                nodes.add(node)
            }

        }
        return nodes

    }

    fun getXXCourse(content: String): MutableList<XuanXiuBean> {
        val nodes = ArrayList<XuanXiuBean>()
        val doc = Jsoup.parse(content)
        val rowRegex = "div.main_box div.mid_box span.formbox table.datelist "//挑选条件
        val rowElements = doc.select(rowRegex)
        val table = rowElements[0]
        val tr = table.select("tr")
        if (tr.size > 0) {
            val first = tr[0].select("td")
            for (i in 1 until tr.size) {
                val td = tr[i].select("td")
                val node = XuanXiuBean()
                node.type = 0
                node.ID = i
                node.courseName = td[2].text()//课程代码
                node.courseCode = first[3].text() + ":" + td[3].text()
                node.studyTime = first[8].text() + ":" + td[8].text()//课程名称
                node.teacherName = first[4].text() + ":" + td[4].text()//课程性质
                node.coursePlace = first[3].text() + ":" + td[3].text()//学分
                node.score = first[7].text() + ":" + td[7].text()//绩点
                node.capacity = first[10].text() + ":" + td[10].text()//绩点
                node.coursePlace = first[6].text() + ":" + td[6].text()
                node.startEnd = first[9].text() + ":" + td[9].text()
                node.last = first[11].text() + ":" + td[11].text()//绩点
                node.courseTime = first[5].text() + ":" + td[5].text()// 成绩
                node.courseProperty = first[13].text() + ":" + td[13].text()
                node.courseClass = first[12].text() + ":" + td[12].text()
                node.schoolCode = first[14].text() + ":" + td[14].text()
                node.holdPlace = td[15].text()
                node.tv_capacity = td[10].text()
                node.tv_last = td[11].text()
                node.coursePost = td[0].select("input").attr("name")
                node.bookPost = td[1].select("input").attr("name")
                node.txtPageSize = doc.select("input[name=dpkcmcGrid:txtPageSize]").`val`()
                node.currentPage = doc.select("span#dpkcmcGrid_lblCurrentPage").text()
                node.txtPageSize2 = doc.select("input[name=dpDataGrid2:txtPageSize]").`val`()
                node.currentPage2 = doc.select("span#dpDataGrid2_lblCurrentPage").text()
                println(node.courseName)
                nodes.add(node)
            }
        }
        return nodes
    }

    fun getXXCourseSelected(content: String): MutableList<XuanXiuSelectedBean> {
        val nodes = ArrayList<XuanXiuSelectedBean>()
        val doc = Jsoup.parse(content)
        val rowRegex = "div.main_box div.mid_box span.formbox table.datelist "//挑选条件
        val rowElements = doc.select(rowRegex)
        val table = rowElements[1]
        val tr = table.select("tr")
        if (tr.size > 0) {
            val first = tr[0].select("td")
            for (i in 1 until tr.size) {
                val td = tr[i].select("td")
                val node = XuanXiuSelectedBean()
                node.type = 0
                node.ID = i
                node.courseName = td[0].text()//课程名称
                node.teacherName = td[1].text()//教师名称
                node.score = first[2].text() + ":" + td[2].text()//学分
                node.studyTime = first[3].text() + ":" + td[3].text()//周学时
                node.startEnd = first[4].text() + ":" + td[4].text()//开始结束
                node.school = first[5].text() + ":" + td[5].text()//校区
                node.courseTime = first[6].text() + ":" + td[6].text()//上课时间
                node.coursePlace = first[7].text() + ":" + td[7].text()//上课时间
                node.book = first[8].text() + ":" + td[8].text()//教材
                node.courseClass = first[9].text() + ":" + td[9].text()//课程归属
                node.courseProperty = first[10].text() + ":" + td[10].text()// 课程性质
                node.schoolCode = first[11].text() + ":" + td[11].text()//校区代码
                val rex = td[12].select("a").attr("href")
                var pattern = Pattern.compile("DataGrid2._ctl\\d{1,3}._ctl\\d{1,3}")
                var matcher = pattern.matcher(rex)
                if (matcher.find()) {
                    val res = matcher.group()
                    pattern = Pattern.compile("\\$")
                    matcher = pattern.matcher(res)
                    val buf = StringBuffer()
                    while (matcher.find()) {
                        matcher.appendReplacement(buf, ":")
                    }
                    matcher.appendTail(buf)
                    node.selectPost = buf.toString()
                }
                nodes.add(node)
            }
        }
        return nodes

    }

    fun getMsg(content: String): Array<String> {
        val temp = Array(8){""}
        val doc = Jsoup.parse(content)
        temp[0] = doc.select("span#dpkcmcGrid_lblTotalRecords").text()
        temp[1] = doc.select("span#dpkcmcGrid_lblCurrentPage").text()
        temp[2] = doc.select("span#dpkcmcGrid_lblTotalPages").text()
        temp[3] = doc.select("input[name=dpkcmcGrid:txtPageSize]").`val`()

        temp[4] = doc.select("span#dpDataGrid2_lblTotalRecords").text()
        temp[5] = doc.select("span#dpDataGrid2_lblCurrentPage").text()
        temp[6] = doc.select("span#dpDataGrid2_lblTotalPages").text()
        temp[7] = doc.select("input[name=dpDataGrid2:txtPageSize]").`val`()

        return temp
    }

    fun getPost(content: String): Array<String> {
        val temp = Array(4){""}
        val doc = Jsoup.parse(content)
        temp[0] = doc.select("input[name=__EVENTTARGET]").`val`()
        temp[1] = doc.select("input[name=__EVENTARGUMENT]").`val`()
        temp[2] = doc.select("input[name=__VIEWSTATE]").`val`()
        temp[3] = doc.select("input[name=__VIEWSTATEGENERATOR]").`val`()

        return temp
    }

    fun getCourseSelectArg(content: String, index: Int): MutableList<String>? {

        val doc = Jsoup.parse(content)
        val rowRegex = "div.searchbox p.search_con "//挑选条件
        val elements = doc.select(rowRegex)
        val select = elements.select("select")[index]
        println(select.toString() + "this")
        var temp: MutableList<String>? = null
        val size = select.select("option").size
        if (size > 0) {
            temp = ArrayList()
            for (i in 0 until size) {
                if (select.select("option")[i].text() == "") {
                    if (select.select("option")[i].hasAttr("selected")) {
                        temp.add("默认" + "selected")
                    } else {
                        temp.add("默认")
                    }

                } else {
                    if (select.select("option")[i].hasAttr("selected")) {
                        temp.add(select.select("option")[i].text() + "selected")

                    } else {
                        temp.add(select.select("option")[i].text())
                    }

                }


            }
        }
        println(temp.toString()+"hahhaha")
        return temp
    }

    fun getRecords(content: String): Array<String>? {
        val doc = Jsoup.parse(content)
        val rowRegex = "div.searchbox p.search_con "//挑选条件
        val elements = doc.select(rowRegex)
        val select = elements.select("select")
        var temp: Array<String>? = null
        val size = select.size
        if (size > 0) {
            temp = Array(5){""}
            for (i in 0 until size) {
                if (i != 3) {
                    temp[i] = select[i].select("option[selected=selected]").text()
                } else {
                    temp[i] = select[i].select("option[selected=selected]").attr("value")
                }
            }
        }
        return temp

    }

    companion object {
        @Volatile private var selectedService: SelectedService? = null

        val seletedService: SelectedService?
            get() {
                if (selectedService == null) {
                    synchronized(CourseService::class.java) {
                        if (selectedService == null)
                            selectedService = SelectedService()
                    }
                }
                return selectedService
            }
    }
}
