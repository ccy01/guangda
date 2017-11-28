package com.example.ccy.tes.service

import com.example.ccy.tes.model.bean.ScoreBean

import org.jsoup.Jsoup

import java.util.ArrayList

/**
 * Created by ccy on 2017/6/27.
 */

class ScoreService private constructor() {

    fun getScore(content: String): List<ScoreBean> {
        val scores = ArrayList<ScoreBean>()
        val doc = Jsoup.parse(content)
        val rowRegex = "div.main_box div.mid_box span.formbox table#Datagrid1.datelist tbody tr"//挑选条件

        val rowElements = doc.select(rowRegex)
        if (rowElements.size > 0) {
            val first = rowElements[0].select("td")
            for (i in 1 until rowElements.size) {
                val elements = rowElements[i].select("td")
                val scoreBean = ScoreBean()
                scoreBean.courseCode = first[2].text() + ":" + elements[2].text()//课程代码
                scoreBean.courseName = elements[3].text()//课程名称
                scoreBean.courseProperty = first[4].text() + ":" + elements[4].text()//课程性质
                scoreBean.credit = first[6].text() + ":" + elements[6].text()//学分
                scoreBean.point = first[7].text() + ":" + elements[7].text()//绩点
                scoreBean.score = elements[8].text()// 成绩
                println(scoreBean.score)

                scores.add(scoreBean)
            }
        }

        return scores
    }

    fun getArg(content: String): Array<String> {
        val temp = Array(2){""}
        val document = Jsoup.parse(content)
        temp[0] = document.select("input[name=__VIEWSTATE]").`val`()
        temp[1] = document.select("input[name=__VIEWSTATEGENERATOR]").`val`()
        return temp
    }

    fun getPoint(content: String): String {
        val temp: String
        val doc = Jsoup.parse(content)
        temp = doc.select("span#pjxfjd b").text() + doc.select("span#xfjdzh b").text()
        return temp
    }

    companion object {
        @Volatile private var scoreService: ScoreService? = null

        fun getInstance(): ScoreService? {
            if (scoreService == null) {
                synchronized(CourseService::class.java) {
                    if (scoreService == null)
                        scoreService = ScoreService()
                }
            }
            return scoreService
        }
    }
}
