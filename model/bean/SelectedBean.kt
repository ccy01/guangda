package com.example.ccy.tes.model.bean


data class SelectedBean(
        var courseCode: String? = null,
        var courseName: String? = null,
        var courseProperty: String? = null,
        var score: String? = null,
        var teacherName: String? = null,
        var point: String? = null,
        var studyTime: String? = null,
        var courseTime: String? = null,
        var coursePalce: String? = null,
        var book: String? = null,
        var ID: Int = 0,
        var expand:Boolean = false,
        var type:Int = 0
):BaseBean{
    companion object {
        val PARENT_ITEM = 0//父布局
        val CHILD_ITEM = 1//子布局
    }
}
