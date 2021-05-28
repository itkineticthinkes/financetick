package com.kineticthinkers.financetick.model

import java.io.Serializable

class BillCategory : Serializable{

    var _ID = ""
        get() = field
        set(value) {
            field = value
        }
    var PARENT_CATEGORY = ""
        get() = field
        set(value) {
            field = value
        }

    var CATEGORY_NAME = ""
        get() = field
        set(value) {
            field = value
        }
    var FREQUENCY = ""
        get() = field
        set(value) {
            field = value
        }

}