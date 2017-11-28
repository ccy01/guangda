package com.example.ccy.tes.service

import com.example.ccy.tes.model.bean.CourseBean
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.litepal.crud.DataSupport

class CourseService private constructor() {

    val list = ArrayList<CourseBean>()

    /**
     * 保存一节课程
     *
     * @param course
     * @return
     */
    fun save(course: CourseBean): Boolean = course.save()

    /**
     * 查询所有课程
     *
     * @return
     */
    fun findAll(): List<CourseBean> = DataSupport.findAll(CourseBean::class.java)

    /**
     * 根据网页返回结果解析课程并保存
     *
     * @param content
     * @return
     */
    fun parseCourse(content: String): String {
        val result = StringBuilder()
        val doc = Jsoup.parse(content)
        val semesters = doc.select("option[selected=selected]")
        val years = semesters[0].text().split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val startYear = Integer.parseInt(years[0])
        val endYear = Integer.parseInt(years[1])
        val semester = Integer.parseInt(semesters[1].text())//学期
        println(semester)//学期

        val elements = doc.select("table#Table6")
        val element = elements[0].child(0)
        //移除一些无用数据

        element.child(0).remove()
        element.child(0).remove()
        element.child(0).child(0).remove()
        element.child(4).child(0).remove()
        element.child(8).child(0).remove()
        val rowNum = element.childNodeSize()
        val map = Array(11) { IntArray(7) }
        for (i in 0 until rowNum - 1) {
            val row = element.child(i)
            val columnNum = row.childNodeSize() - 2
            for (j in 1 until columnNum) {
                val column = row.child(j)
                val week = fillMap(column, map, i)
                //填充map，获取周几，第几节至第几节
                //作用：弥补不能获取这些数据的格式
                if (column.hasAttr("rowspan")) {
                    try {
                        splitCourse(column.html(), startYear, endYear, semester, week, i + 1, i + column.attr("rowspan").toInt())
                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }

                }
            }
        }

        list.forEach {
            println(it)
            it.save()
        }

        return result.toString()
    }


    /**
     * 根据传进来的课程格式转换为对应的实体类并保存
     *
     * @param sub
     * @param startYear
     * @param endYear
     * @param semester
     * @param week
     * @param startSection
     * @param endSection
     * @return
     */
    private fun storeCourseByResult(sub: String, startYear: Int, endYear: Int, semester: Int, week: Int,
                                    startSection: Int, endSection: Int): CourseBean {
        // 周二第1,2节{第4-16周} 		二,1,2,4,16,null
        // {第2-10周|3节/周} 			null,null,null,2,10,3节/周
        // 周二第1,2节{第4-16周|双周} 	二,1,2,4,16,双周
        // 周二第1节{第4-16周} 			二,1,null,4,16,null
        // 周二第1节{第4-16周|双周} 		二,1,null,4,16,双周
        //
        // str格式如上，这里只是简单考虑每个课都只有两节课，实际上有三节和四节，模式就要改动，其他匹配模式请自行修改

        val splitPattern = "<br>"
        val temp = sub.split(splitPattern.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val course = CourseBean()
        course.startYear = startYear

        course.endYear = endYear

        course.semester = semester

        course.courseName = temp[0]
        course.courseTime = temp[1].substringBefore("(")
        course.teacher = temp[2]
        course.classsroom = temp[3]
        course.dayOfWeek = week
        course.startSection = startSection
        course.endSection = endSection
        course.inWeek = "f"

        if (!list.contains(course))
            list.add(course)
        else {
            val index = list.indexOf(course)
            val bean = list[index]
            list.removeAt(index)
            bean.endSection = bean.endSection + course.endSection - course.startSection + 1
            list.add(bean)
        }
        return course
    }


    /**
     * 提取课程格式，可能包含多节课
     *
     * @param str
     * @param startYear
     * @param endYear
     * @param semester
     * @param week
     * @param startSection
     * @param endSection
     * @return
     */
    private fun splitCourse(str: String, startYear: Int, endYear: Int, semester: Int, week: Int, startSection: Int,
                            endSection: Int): Int {
        val pattern = "<br><br>"
        val split = str.split(pattern.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (split.size > 1) {// 如果大于一节课
            for (i in split.indices) {
                if (!(split[i].startsWith("<br>") && split[i].endsWith("<br>"))) {
                    storeCourseByResult(split[i], startYear, endYear, semester, week, startSection,
                            endSection)// 保存单节课
                } else {
                    // <br />文化地理（网络课程）<br />周日第10节{第17-17周}<br />李宏伟<br />
                    // 以上格式的特殊处理，此种格式在没有教师的情况下产生，即教室留空后<br />依旧存在
                    val brLength = "<br>".length
                    val substring = split[i].substring(brLength, split[i].length - brLength)
                    storeCourseByResult(substring, startYear, endYear, semester, week, startSection, endSection)// 保存单节课
                }
            }
            return split.size
        } else {
            storeCourseByResult(str, startYear, endYear, semester, week, startSection, endSection)// 保存
            return 1
        }
    }

    companion object {
        @Volatile private var courseService: CourseService? = null

        fun getInstance(): CourseService? {
            if (courseService == null) {
                synchronized(CourseService::class.java) {
                    if (courseService == null)
                        courseService = CourseService()
                }
            }
            return courseService
        }

        /**
         * 填充map，获取周几，第几节课至第几节课
         *
         * @param childColumn
         * @param map
         * @param i
         * @return 周几
         */
        fun fillMap(childColumn: Element, map: Array<IntArray>, i: Int): Int {
            //这个函数的作用自行领悟，总之就是返回周几，也是无意中发现的，于是就这样获取了，作用是双重保障，因为有些课事无法根据正则匹配出周几第几节到第几节
            val hasAttr = childColumn.hasAttr("rowspan")
            var week = 0
            if (hasAttr) {
                for (t in 0 until map[0].size) {
                    if (map[i][t] == 0) {
                        val r = Integer.parseInt(childColumn.attr("rowspan"))
                        for (l in 0 until r) {
                            map[i + l][t] = 1
                        }
                        week = t + 1
                        break
                    }
                }

            } else {
                if (childColumn.childNodes().size > 1) {
                    childColumn.attr("rowspan", "1")
                }
                for (t in 0 until map[0].size) {
                    if (map[i][t] == 0) {
                        map[i][t] = 1
                        week = t + 1
                        break
                    }
                }
            }
            return week
        }
    }


}

