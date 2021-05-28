package com.kineticthinkers.financetick.billcategory

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.kineticthinkers.financetick.BaseActivity
import com.kineticthinkers.financetick.R
import com.kineticthinkers.financetick.database.DBManager
import com.kineticthinkers.financetick.model.BillCategory
import kotlinx.android.synthetic.main.activity_billcategory_update.*


class BillCategoryEditActivity : BaseActivity() {
    private lateinit var dbManager: DBManager
    private lateinit var billCategory: BillCategory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billcategory_update)
        if (intent != null && intent.getSerializableExtra("BillCategory") != null) {
            billCategory = intent.getSerializableExtra("BillCategory") as BillCategory
            dbManager = openDataBase()
            ParentCategory_txtValue.text = ParentCategories[billCategory.PARENT_CATEGORY.toInt()]
            BillName_txtValue.setText(billCategory.CATEGORY_NAME)
            DefaultFrequency_txtValue.text = Frequencies[billCategory.FREQUENCY.toInt()]
        } else {
            finish()
        }
    }

    fun updateCategory(view: View) {
        val categorName = BillName_txtValue.text.toString()

        if (categorName.isNotEmpty()) {
            dbManager.update(
                billCategory._ID.toLong(),
                billCategory.PARENT_CATEGORY.toInt(),
                categorName,
                billCategory.FREQUENCY.toInt()
            )
            showToastMessage("Bill Category Updated.")
            finish()

        } else {
            showToastMessage("Please Enter Bill Category.")
        }

        // CallActivity(BillCategoryAddActivity(),false, Bundle.EMPTY)
    }

    fun deleteCategory(view: View) {


        AlertDialog.Builder(this)
            .setTitle("DELETE CATEGORY")
            .setMessage("Are your sure ,you want to delete this category")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                // for sending data to previous activity use
                // setResult(response code, data)
                dbManager.delete(billCategory._ID.toLong())
                showToastMessage("Category Deleted")
                finish()
            }
            .setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            .show()

        // CallActivity(BillCategoryAddActivity(),false, Bundle.EMPTY)
    }

    fun openParentCategoriesDialog(v: View?) {
        val alertDialog: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alertDialog.setTitle("SELECT PARENT CATEGORY")
        val rowList: View = layoutInflater.inflate(R.layout.row, null)
        val listView = rowList.findViewById<View>(R.id.listView) as ListView
        val adapter =
            ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1, ParentCategories)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        val dialog: android.app.AlertDialog = alertDialog.create()

        listView.onItemClickListener = AdapterView.OnItemClickListener { arg0, arg1, arg2, arg3 ->
            Log.d("############", "Items " + ParentCategories.get(arg2))
            ParentCategory_txtValue.text = ParentCategories.get(arg2)
            dialog.cancel()
        }
        dialog.show()
    }

    fun openFrequencyDialog(v: View?) {
        val alertDialog: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alertDialog.setTitle("SELECT PARENT CATEGORY")
        val rowList: View = layoutInflater.inflate(R.layout.row, null)
        val listView = rowList.findViewById<View>(R.id.listView) as ListView
        val adapter =
            ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1, Frequencies)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        val dialog: android.app.AlertDialog = alertDialog.create()

        listView.onItemClickListener = AdapterView.OnItemClickListener { arg0, arg1, arg2, arg3 ->
            Log.d("############", "Items " + Frequencies.get(arg2))
            DefaultFrequency_txtValue.text = Frequencies.get(arg2)
            dialog.cancel()
        }
        dialog.show()
    }
}