package com.kineticthinkers.financetick.billcreate

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.kineticthinkers.financetick.BaseActivity
import com.kineticthinkers.financetick.R
import com.kineticthinkers.financetick.database.DBManager
import com.kineticthinkers.financetick.database.DatabaseHelper
import com.kineticthinkers.financetick.model.BillCategory
import kotlinx.android.synthetic.main.activity_bill_add.*
import java.text.SimpleDateFormat
import java.util.*


class BillNewAddActivity : BaseActivity() {
    var mArrayList = ArrayList<BillCategory>()
    var mArrayListCategoryName = ArrayList<String>()

    private lateinit var dbManager: DBManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_add)
        setUpBackBtn(this)
        dbManager = openDataBase()
        mArrayList = getCategoryTypes()

        if (mArrayList.size > 0) {
            mArrayListCategoryName = getOnlyCategoryName(mArrayList)
        } else {
            showToastMessage("Please Add at least one bill category.")
            finish()
        }


    }

    private fun getOnlyCategoryName(mArrayList: ArrayList<BillCategory>): ArrayList<String> {
        val arr = ArrayList<String>()
        mArrayList.forEachIndexed { index, billCategory ->
            arr.add(billCategory.CATEGORY_NAME)
        }
        return arr
    }

    fun showDatePickerDialog() {
        val mcurrentDate = Calendar.getInstance()
        val year = mcurrentDate[Calendar.YEAR]
        val month = mcurrentDate[Calendar.MONTH]
        val day = mcurrentDate[Calendar.DAY_OF_MONTH]

        val mDatePicker = DatePickerDialog(
            this@BillNewAddActivity,
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

    fun saveBill(view: View) {
        val billType_txtValue = BillType_txtValue.text.toString()

        val billName = BillName_txtValue.text.toString()
        val defaultFrequency = DefaultFrequency_txtValue.text.toString()
        val billNote = BillNote_txtValue.text.toString()
        val dueDate = DueDate_txtValue.text.toString()
        val billAmount = BillAmount_txtValue.text.toString()

        when {

            billType_txtValue.isEmpty() -> {
                showToastMessage("Please select Bill Type.")

            }


            billName.isEmpty() -> {
                showToastMessage("Please Enter Bill Name.")

            }

            defaultFrequency.isEmpty() -> {
                showToastMessage("Please select Default Frequency.")

            }
            dueDate.isEmpty() -> {
                showToastMessage("Please select due date.")

            }
            billAmount.isEmpty() -> {
                showToastMessage("Please Enter Bill amount.")

            }
            else -> {

                val billType_txtValueIndex =
                    getIndexFromArray(billType_txtValue, mArrayListCategoryName.toTypedArray())


                val defaultFrequencyIndex = getIndexFromArray(defaultFrequency, Frequencies)

                if (billType_txtValueIndex != -1 && defaultFrequencyIndex != -1) {
                    handelFrequencyAndaddBiils(
                        billType_txtValueIndex,
                        billName,
                        defaultFrequencyIndex,
                        billNote,
                        dueDate,
                        billAmount.toLong(),
                        0
                    )

                } else {
                    showToastMessage("Category or Frequency not found.")

                }
            }
        }

        // CallActivity(BillCategoryAddActivity(),false, Bundle.EMPTY)
    }

    private fun handelFrequencyAndaddBiils(
        billtypeTxtvalueindex: Int,
        billName: String,
        defaultFrequencyIndex: Int,
        billNote: String,
        dueDate: String,
        billAmount: Long,
        bilpaid: Int
    ) {
        var dialog: AlertDialog
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("END DATE OR NUMBER OF BILLS")
        val rowList: View = layoutInflater.inflate(R.layout.dialogbillsave, null)
        val one_year_imageView = rowList.findViewById<ImageView>(R.id.one_year_imageView)
        val three_year_imageView = rowList.findViewById<ImageView>(R.id.three_year_imageView)
        val ten_year_imageView = rowList.findViewById<ImageView>(R.id.ten_year_imageView)
        val end_date_calendar_imageView = rowList.findViewById<ImageView>(R.id.end_date_calendar_imageView)
        val no_of_bills_imageView = rowList.findViewById<ImageView>(R.id.no_of_bills_imageView)

        val end_date_textView_value = rowList.findViewById<TextView>(R.id.end_date_textView_value)
        val Create_no_of_bills_textView_value = rowList.findViewById<TextView>(R.id.Create_no_of_bills_textView_value)
        var noOfBills =   ArrayList<Date>()
        one_year_imageView.setOnClickListener(View.OnClickListener {
            noOfBills =   ArrayList<Date>()
            resetImages(
                one_year_imageView,
                three_year_imageView,
                ten_year_imageView,
                end_date_calendar_imageView,
                no_of_bills_imageView,
                end_date_textView_value,
                Create_no_of_bills_textView_value
            )
            one_year_imageView.setImageResource(R.drawable.checkbox_on_background)
            val startdate = SimpleDateFormat("dd/MM/yyyy").parse(dueDate)
            val enddate = addYear(startdate,1)
            val enddateString = toSimpleString(enddate)
            Log.d("enddateString",enddateString)
            end_date_textView_value.text = enddateString
             noOfBills = get_no_of_Bills(startdate!!,enddate,defaultFrequencyIndex)
            Create_no_of_bills_textView_value.text = noOfBills.size.toString()
           /* noOfBills.forEachIndexed { index, date ->
                val dateString = toSimpleString(date)
                Log.d("dateString",dateString)
            }*/

        })
        three_year_imageView.setOnClickListener(View.OnClickListener {
            noOfBills =   ArrayList<Date>()
            resetImages(
                one_year_imageView,
                three_year_imageView,
                ten_year_imageView,
                end_date_calendar_imageView,
                no_of_bills_imageView,
                end_date_textView_value,
                Create_no_of_bills_textView_value
            )
            three_year_imageView.setImageResource(R.drawable.checkbox_on_background)

            val startdate = SimpleDateFormat("dd/MM/yyyy").parse(dueDate)
            val enddate = addYear(startdate,3)
            val enddateString = toSimpleString(enddate)
            Log.d("enddateString",enddateString)
            end_date_textView_value.text = enddateString
             noOfBills = get_no_of_Bills(startdate!!,enddate,defaultFrequencyIndex)
            Create_no_of_bills_textView_value.text = noOfBills.size.toString()
            /* noOfBills.forEachIndexed { index, date ->
                 val dateString = toSimpleString(date)
                 Log.d("dateString",dateString)
             }*/


        })

        ten_year_imageView.setOnClickListener(View.OnClickListener {
            noOfBills =   ArrayList<Date>()
            resetImages(
                one_year_imageView,
                three_year_imageView,
                ten_year_imageView,
                end_date_calendar_imageView,
                no_of_bills_imageView,
                end_date_textView_value,
                Create_no_of_bills_textView_value
            )
            ten_year_imageView.setImageResource(R.drawable.checkbox_on_background)


            val startdate = SimpleDateFormat("dd/MM/yyyy").parse(dueDate)
            val enddate = addYear(startdate,10)
            val enddateString = toSimpleString(enddate)
            Log.d("enddateString",enddateString)
            end_date_textView_value.text = enddateString
             noOfBills = get_no_of_Bills(startdate!!,enddate,defaultFrequencyIndex)
            Create_no_of_bills_textView_value.text = noOfBills.size.toString()
            /* noOfBills.forEachIndexed { index, date ->
                 val dateString = toSimpleString(date)
                 Log.d("dateString",dateString)
             }*/

        })
        end_date_calendar_imageView.setOnClickListener(View.OnClickListener {
            noOfBills =   ArrayList<Date>()
            resetImages(
                one_year_imageView,
                three_year_imageView,
                ten_year_imageView,
                end_date_calendar_imageView,
                no_of_bills_imageView,
                end_date_textView_value,
                Create_no_of_bills_textView_value
            )
            end_date_calendar_imageView.setImageResource(R.drawable.checkbox_on_background)

        })
        no_of_bills_imageView.setOnClickListener(View.OnClickListener {
            noOfBills =   ArrayList<Date>()
            resetImages(
                one_year_imageView,
                three_year_imageView,
                ten_year_imageView,
                end_date_calendar_imageView,
                no_of_bills_imageView,
                end_date_textView_value,
                Create_no_of_bills_textView_value
            )
            no_of_bills_imageView.setImageResource(R.drawable.checkbox_on_background)
        })
        alertDialog.setCancelable(false)

        alertDialog.setView(rowList)
        alertDialog.setNegativeButton(
            "CANCEL",
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })

        alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
            showToastMessage("Bill Added Added.")
            if(noOfBills.size==0)
            {
                showToastMessage("please select one option")
            }
            else
            {
                      dbManager.insertBill(billtypeTxtvalueindex, billName, defaultFrequencyIndex,billNote,noOfBills,dueDate,billAmount.toLong(),bilpaid)
                      showToastMessage("Bill Added Added.")
                      finish()
            }


        })
        dialog = alertDialog.create()


        dialog.show()


    }

    fun resetImages(
        img1: ImageView,
        img2: ImageView,
        img3: ImageView,
        img4: ImageView,
        img5: ImageView,
    textView1: TextView,
    textView2: TextView
    ) {
        img1.setImageResource(R.drawable.checkbox_off_background)
        img2.setImageResource(R.drawable.checkbox_off_background)
        img3.setImageResource(R.drawable.checkbox_off_background)
        img4.setImageResource(R.drawable.checkbox_off_background)
        img5.setImageResource(R.drawable.checkbox_off_background)
        textView1.text= "-"
        textView2.text= "-"
    }

    fun openTypeDialog(v: View?) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("SELECT PARENT CATEGORY")
        val rowList: View = layoutInflater.inflate(R.layout.row, null)
        val listView = rowList.findViewById<View>(R.id.listView) as ListView
        val adapter =
            ArrayAdapter<Any?>(
                this, android.R.layout.simple_list_item_1,
                mArrayListCategoryName as List<String?>
            )
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        val dialog: AlertDialog = alertDialog.create()

        listView.onItemClickListener = OnItemClickListener { arg0, arg1, arg2, arg3 ->
            Log.d("############", "Items " + mArrayListCategoryName.get(arg2))
            BillType_txtValue.text = mArrayListCategoryName.get(arg2)
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
            DefaultFrequency_txtValue.text = Frequencies.get(arg2)
            dialog.cancel()
        }
        dialog.show()
    }

    fun openDateDialog(view: View) {
        showDatePickerDialog()

    }


    private fun getCategoryTypes(): ArrayList<BillCategory> {
        val mArrayList = ArrayList<BillCategory>()

        val totalElements = dbManager.GetCount()
        if (totalElements > 0) {
            val c = dbManager.fetch()!!
            c.moveToFirst()
            while (!c.isAfterLast) {
                val billCategory = BillCategory()
                billCategory._ID = c.getString(c.getColumnIndex(DatabaseHelper._ID))
                billCategory.PARENT_CATEGORY =
                    c.getString(c.getColumnIndex(DatabaseHelper.PARENT_CATEGORY))
                billCategory.CATEGORY_NAME =
                    c.getString(c.getColumnIndex(DatabaseHelper.CATEGORY_NAME))
                billCategory.FREQUENCY = c.getString(c.getColumnIndex(DatabaseHelper.FREQUENCY))
                mArrayList.add(billCategory) //add the item
                c.moveToNext()
            }
            c.close()

        }
        return mArrayList
    }
}