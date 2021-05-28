package com.kineticthinkers.financetick.billcategory

import android.os.Bundle
import android.view.View
import com.kineticthinkers.financetick.BaseActivity
import com.kineticthinkers.financetick.R
import com.kineticthinkers.financetick.billcategory.adapters.BillCategoryListadapter
import com.kineticthinkers.financetick.billcreate.BillNewAddActivity
import com.kineticthinkers.financetick.database.DBManager
import com.kineticthinkers.financetick.database.DatabaseHelper
import com.kineticthinkers.financetick.model.BillCategory
import kotlinx.android.synthetic.main.activity_billcategory_list.*
import java.util.*


class BillCategoryListActivity : BaseActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billcategory_list)
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
        val totalElements = dbManager.GetCount()
        if (totalElements>0) {
            val mArrayList = ArrayList<BillCategory>()
            val c =dbManager.fetch()!!
            c.moveToFirst()
            while (!c.isAfterLast()) {
               val billCategory =BillCategory()
                billCategory._ID= c.getString(c.getColumnIndex(DatabaseHelper._ID))
                billCategory.PARENT_CATEGORY= c.getString(c.getColumnIndex(DatabaseHelper.PARENT_CATEGORY))
                billCategory.CATEGORY_NAME= c.getString(c.getColumnIndex(DatabaseHelper.CATEGORY_NAME))
                billCategory.FREQUENCY= c.getString(c.getColumnIndex(DatabaseHelper.FREQUENCY))
                mArrayList.add(billCategory) //add the item

                c.moveToNext()
            }
            c.close()
            noCategory_txt.visibility=View.GONE
            recyclerView.visibility=View.VISIBLE
            recyclerView.adapter = BillCategoryListadapter(mArrayList,this)
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