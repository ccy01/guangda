package com.example.ccy.tes.model.bean

import org.litepal.crud.DataSupport

data class CourseBean(var id: Int = 0,//主鍵，自增
                      var startYear: Int = 0,//学年开始年
                      var endYear: Int = 0,//学年结束年
                      var semester: Int = 0,//学期
                      var courseName: String? = null,//课程名
                      var courseTime: String? = null,//课程时间，冗余字段
                      var classsroom: String? = null,//教室
                      var teacher: String? = null,//老师
                      var dayOfWeek: Int = 0,//星期几
                      var startSection: Int = 0,
                      var endSection: Int = 0,
                      var inWeek:String ?= null
) : DataSupport() {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is CourseBean) return false
        return other.courseName == courseName && other.classsroom == classsroom
    }
    override fun hashCode(): Int = courseName!!.hashCode() + classsroom!!.hashCode()
}





