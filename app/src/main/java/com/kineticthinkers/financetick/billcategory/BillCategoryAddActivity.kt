package com.kineticthinkers.financetick.billcategory

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import com.kineticthinkers.financetick.BaseActivity
import com.kineticthinkers.financetick.R
import com.kineticthinkers.financetick.database.DBManager
import kotlinx.android.synthetic.main.activity_billcategory_add.*


class BillCategoryAddActivity : BaseActivity() {
    private lateinit var dbManager: DBManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billcategory_add)
        dbManager = openDataBase()
    }

    fun saveCategory(view: View) {
        val categoryName = BillName_txtValue.text.toString()
        val parentCategory = ParentCategory_txtValue.text.toString()
        val defaultFrequency = DefaultFrequency_txtValue.text.toString()

        when {
            categoryName.isEmpty() -> {
                showToastMessage("Please Enter Bill Category.")

            }
            parentCategory.isEmpty() -> {
                showToastMessage("Please select Parent Category.")

            }
            defaultFrequency.isEmpty() -> {
                showToastMessage("Please select Default Frequency.")

            }
            else -> {
                val parentCategoriesIndex =getIndexFromArray(parentCategory,ParentCategories)
                val defaultFrequencyIndex =getIndexFromArray(defaultFrequency,Frequencies)

                if (parentCategoriesIndex !=-1 && defaultFrequencyIndex !=-1) {
                    dbManager.insert(parentCategoriesIndex, categoryName, defaultFrequencyIndex)
                    showToastMessage("Bill Category Added.")
                    finish()
                } else {
                    showToastMessage("Category or Frequency not found.")

                }
            }
        }

        // CallActivity(BillCategoryAddActivity(),false, Bundle.EMPTY)
    }

    fun openParentCategoriesDialog(v: View?) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("SELECT PARENT CATEGORY")
        val rowList: View = layoutInflater.inflate(R.layout.row, null)
        val listView = rowList.findViewById<View>(R.id.listView) as ListView
        val adapter =
            ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1, ParentCategories)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        val dialog: AlertDialog = alertDialog.create()

        listView.onItemClickListener = OnItemClickListener { arg0, arg1, arg2, arg3 ->
            Log.d("############", "Items " + ParentCategories.get(arg2))
            ParentCategory_txtValue.text =     ParentCategories.get(arg2)
            dialog.cancel()
        }
        dialog.show()
    }

    fun openFrequencyDialog(v: View?) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("SELECT PARENT CATEGORY")
        val rowList: View = layoutInflater.inflate(R.layout.row, null)
        val listView = rowList.findViewById<View>(R.id.listView) as ListView
        val adapter =
            ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1, Frequencies)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        val dialog: AlertDialog = alertDialog.create()

        listView.onItemClickListener = OnItemClickListener { arg0, arg1, arg2, arg3 ->
            Log.d("############", "Items " + Frequencies.get(arg2))
            DefaultFrequency_txtValue.text =     Frequencies.get(arg2)
            dialog.cancel()
        }
        dialog.show()
    }
}