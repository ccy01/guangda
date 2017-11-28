package com.example.ccy.tes.model.bean

/**
 * Created by ccy on 2017/7/17.
 */

data class XuanXiuBean(
        var courseName: String = "",
        var courseCode: String = "",
        var teacherName: String = "",
        var courseTime: String = "",
        var isCheck_c: Boolean = false,
        var isCheck_b: Boolean = false,
        var tv_capacity: String = "",
        var tv_last: String = "",
        var coursePost: String = "",
        var bookPost: String = "",
        var coursePlace: String = "",
        var score: String = "",
        var studyTime: String = "",
        var startEnd: String = "",
        var capacity: String = "",
        var last: String = "",
        var courseClass: String = "",
        var courseProperty: String = "",
        var schoolCode: String = "",
        var holdPlace: String = "",
        var txtPageSize: String = "",
        var currentPage: String = "",
        var txtPageSize2: String = "",
        var currentPage2: String = "",
        var expand: Boolean = false,
        var type: Int = 0,
        var ID: Int = 0
): BaseBean {
    companion object {
        val PARENT_ITEM = 0//父布局
        val CHILD_ITEM = 1//子布局
    }
}
