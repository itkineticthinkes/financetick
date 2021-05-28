package com.kineticthinkers.financetick

import android.os.Bundle
import android.view.View
import com.kineticthinkers.financetick.billcategory.BillCategoryListActivity
import com.kineticthinkers.financetick.billcreate.BillListAllActivity
import com.kineticthinkers.financetick.billcreate.BillNewAddActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun callBillCategoryListActivity(view: View) {

        callActivity(BillCategoryListActivity(),false, Bundle.EMPTY)
    }

    fun callCreateBillActivity(view: View) {

        callActivity(BillNewAddActivity(),false, Bundle.EMPTY)
    }

    fun callAllBillActivity(view: View) {

        callActivity(BillListAllActivity(),false, Bundle.EMPTY)
    }
}