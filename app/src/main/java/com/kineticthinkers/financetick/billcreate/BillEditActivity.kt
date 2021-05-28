package com.kineticthinkers.financetick.billcreate

import android.app.DatePickerDialog
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
import com.kineticthinkers.financetick.model.BillMainSchedule
import kotlinx.android.synthetic.main.activity_bill_add.*
import kotlinx.android.synthetic.main.activity_bill_edit.*
import kotlinx.android.synthetic.main.activity_bill_edit.BillAmount_txtValue
import kotlinx.android.synthetic.main.activity_bill_edit.BillName_txtValue
import kotlinx.android.synthetic.main.activity_bill_edit.BillNote_txtValue
import kotlinx.android.synthetic.main.activity_bill_edit.BillType_txtValue
import kotlinx.android.synthetic.main.activity_bill_edit.DefaultFrequency_txtValue
import kotlinx.android.synthetic.main.activity_bill_edit.DueDate_txtValue
import java.util.*


class BillEditActivity : BaseActivity() {
    private lateinit var dbManager: DBManager
    private lateinit var billMain: BillMainSchedule
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_edit)
        if (intent != null && intent.getSerializableExtra("BillCategory") != null) {
            billMain = intent.getSerializableExtra("BillCategory") as BillMainSchedule
            dbManager = openDataBase()
            BillType_txtValue.text = ParentCategories[billMain.BILL_TYPE_SCHEDULE.toInt()]
            BillName_txtValue.setText(billMain.BILL_NAME_SCHEDULE)
            BillNote_txtValue.setText(billMain.BILL_NOTES_SCHEDULE)
            DueDate_txtValue.setText(billMain.BILL_DUE_DATE_SCHEDULE)
            BillAmount_txtValue.setText(billMain.BILL_AMOUNT_SCHEDULE)
            DefaultFrequency_txtValue.text = Frequencies[billMain.BILL_FREQUENCY_SCHEDULE.toInt()]
        } else {
            finish()
        }
    }

    fun updateCategory(view: View) {
        val bill_name = BillName_txtValue.text.toString()
        val bill_notes = BillNote_txtValue.text.toString()
        val bill_due_date = DueDate_txtValue.text.toString()
        val bill_amount = BillAmount_txtValue.text.toString()

        if (bill_name.isNotEmpty()) {
            dbManager.updateBillSchedule(
                billMain.BILL_ID_PARENT_SCHEDULE.toLong(),
                billMain.BILL_ID_SCHEDULE.toLong(),
                billMain.BILL_TYPE_SCHEDULE.toInt(),
                bill_name,
                billMain.BILL_FREQUENCY_SCHEDULE.toInt(),
                bill_notes,
                bill_due_date,
                bill_amount.toLong(),
                billMain.BILL_PAID_SCHEDULE.toInt()
                )
            showToastMessage("Bill Details Updated.")
            finish()

        } else {
            showToastMessage("Please Enter Bill Name.")
        }

        // CallActivity(BillCategoryAddActivity(),false, Bundle.EMPTY)
    }

    fun deleteBill(view: View) {


        AlertDialog.Builder(this)
            .setTitle("DELETE Bill")
            .setMessage("Are your sure ,you want to delete this Bill")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                // for sending data to previous activity use
                // setResult(response code, data)
                dbManager.deleteBillSchedule(billMain.BILL_ID_SCHEDULE.toLong())
                showToastMessage("Bill Deleted")
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
            BillType_txtValue.text = ParentCategories.get(arg2)
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
    fun openDateDialog(view: View) {
        showDatePickerDialog()

    }
    fun showDatePickerDialog() {
        val mcurrentDate = Calendar.getInstance()
        val year = mcurrentDate[Calendar.YEAR]
        val month = mcurrentDate[Calendar.MONTH]
        val day = mcurrentDate[Calendar.DAY_OF_MONTH]

        val mDatePicker = DatePickerDialog(
            this@BillEditActivity,
            { datepicker, selectedYear, selectedMonth, selectedDay -> // TODO Auto-generated method stub
                /*      Your code   to get date and time    */
                Log.e(
                    "Date Selected",
                    "Month:  $selectedDay : $selectedMonth DayYear: $selectedYear"
                )
                DueDate_txtValue.text = "$selectedDay/$selectedMonth/$selectedYear"
            }, year, month, day
        )
        mDatePicker.setTitle("Select date")
        mDatePicker.show()
    }

}