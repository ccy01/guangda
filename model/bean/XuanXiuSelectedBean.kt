package com.example.ccy.tes.model.bean

/**
 * Created by ccy on 2017/7/20.
 */

data class XuanXiuSelectedBean (
    var courseName: String = "",
    var teacherName: String = "",
    var score: String = "",
    var studyTime: String = "",
    var startEnd: String = "",
    var school: String = "",
    var courseTime: String = "",
    var coursePlace: String = "",
    var book: String = "",
    var courseClass: String = "",
    var courseProperty: String = "",
    var schoolCode: String = "",
    var selectPost: String = "",
    var expand: Boolean = false,
    var type: Int = 0,
    var ID: Int = 0
):BaseBean{
    companion object {
        val PARENT_ITEM = 0//父布局
        val CHILD_ITEM = 1//子布局
    }
}
