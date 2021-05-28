package com.kineticthinkers.financetick.billcreate

import android.os.Bundle
import android.view.View
import com.kineticthinkers.financetick.BaseActivity
import com.kineticthinkers.financetick.R
import com.kineticthinkers.financetick.billcreate.adapters.BillListadapter
import com.kineticthinkers.financetick.database.DBManager
import com.kineticthinkers.financetick.database.DatabaseHelper
import com.kineticthinkers.financetick.model.BillMain
import com.kineticthinkers.financetick.model.BillMainSchedule
import kotlinx.android.synthetic.main.activity_billcategory_list.*
import java.util.*


class BillListAllActivity : BaseActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billcategory_list)
        setUpBackBtn(this)
        dbManager = openDataBase()

        setData()
    }

    override fun onResume() {
        if (dbManager != null) {
            setData()
        }


        super.onResume()
    }

    private fun setData()
    {
        val totalElements = dbManager.GetCountBillSSchedule()
        if (totalElements>0) {
            val mArrayList = ArrayList<BillMainSchedule>()
            val c =dbManager.fetchBillSchedule()!!
            c.moveToFirst()
            while (!c.isAfterLast) {
               val billCategory = BillMainSchedule()
                billCategory.BILL_ID_PARENT_SCHEDULE= c.getString(c.getColumnIndex(DatabaseHelper.BILL_ID_PARENT_SCHEDULE))
                billCategory.BILL_ID_SCHEDULE= c.getString(c.getColumnIndex(DatabaseHelper.BILL_ID_SCHEDULE))
                billCategory.BILL_TYPE_SCHEDULE= c.getString(c.getColumnIndex(DatabaseHelper.BILL_TYPE_SCHEDULE))
                billCategory.BILL_NAME_SCHEDULE= c.getString(c.getColumnIndex(DatabaseHelper.BILL_NAME_SCHEDULE))
                billCategory.BILL_FREQUENCY_SCHEDULE= c.getString(c.getColumnIndex(DatabaseHelper.BILL_FREQUENCY_SCHEDULE))
                billCategory.BILL_NOTES_SCHEDULE= c.getString(c.getColumnIndex(DatabaseHelper.BILL_NOTES_SCHEDULE))
                billCategory.BILL_DUE_DATE_SCHEDULE= c.getString(c.getColumnIndex(DatabaseHelper.BILL_DUE_DATE_SCHEDULE))
                billCategory.BILL_AMOUNT_SCHEDULE= c.getString(c.getColumnIndex(DatabaseHelper.BILL_AMOUNT_SCHEDULE))
                billCategory.BILL_PAID_SCHEDULE= c.getString(c.getColumnIndex(DatabaseHelper.BILL_PAID_SCHEDULE))
                mArrayList.add(billCategory) //add the item

                c.moveToNext()
            }
            c.close()
            noCategory_txt.visibility=View.GONE
            recyclerView.visibility=View.VISIBLE
            recyclerView.adapter = BillListadapter(mArrayList,this)
        }
        else
        {
            noCategory_txt.visibility=View.VISIBLE
            recyclerView.visibility=View.GONE
        }
    }

    fun callAddBillCategoryActivity(view: View) {

        callActivity(BillNewAddActivity(), false, Bundle.EMPTY)
    }
}