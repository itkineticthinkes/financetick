package com.kineticthinkers.financetick

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kineticthinkers.financetick.database.DBManager
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //Parent Categories Array
    open val ParentCategories = arrayOf<String>(
        "Loans", "Utilities", "Insurance",
        "Recurring", "Investment", "Others"
    ) //explicit type declaration

    //Monthly Array
    open val Frequencies = arrayOf<String>(
        "One time (Does not repeat)",
        "Daily (EveryDay)",
        "Weekly (Every 7 Days)",
        "Fortnightly (Every 14 Days)",
        "Every 3 Weeks (Every 21 Days)",
        "Every 4 Weeks (Every 28 Days)",
        "Every 30 days (Every 4 Weeks)",
        "Monthly (Every Month)",
        "Bimonthly (Every 2 Months)",
        "Quarterly (Every 3 Months)",
        "Half-yearly (Every 6 Months)",
        "yearly (Every year)"
    )

    open fun setUpBackBtn(applicationContext: Context) {

      val backbtn = (applicationContext as Activity?)!!.findViewById<ImageView>(R.id.Back_imageView)
        if(backbtn!=null)
        {
            backbtn.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
        }
    }

    open fun callActivity(activity: BaseActivity, Finish: Boolean, b: Bundle) {

        val mainIntent = Intent(this, activity::class.java)

        mainIntent.putExtras(b)

        if (Finish) {
            /*  mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
              mainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
              mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)*/
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            finishAffinity()

        }
        startActivity(mainIntent)

    }

    open fun openDataBase(): DBManager {

        val dbManager = DBManager(this)
        dbManager.open()

        return dbManager
    }

    open fun showToastMessage(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    open fun getIndexFromArray(selected: String, Arrays: Array<String>): Int {
        Arrays.forEachIndexed { index, s ->
            if (s.equals(selected)) {
                return index
            }
        }
        return -1

    }


    open fun addDay(date: Date, i: Int): Date {
        val cal: Calendar = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DAY_OF_YEAR, i)
        return cal.time
    }

    open fun addMonth(date: Date, i: Int): Date {
        val cal: Calendar = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MONTH, i)
        return cal.time
    }

    open fun addYear(date: Date, i: Int): Date {
        val enddateString = toSimpleString(date)
        Log.d("enddateStringstart", enddateString)
        val cal: Calendar = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.YEAR, i)
        return cal.time
    }

    fun toSimpleString(date: Date?) = with(date ?: Date()) {
        SimpleDateFormat("dd/MM/yyy").format(this)
    }

    fun getDaysinMonth(date: Date): Int{
        val cal: Calendar = Calendar.getInstance()
        cal.time = date

        val numDays = cal.getActualMaximum(Calendar.DATE)
        return numDays
    }

    fun get_no_of_Bills(startdate: Date, enddate: Date, frequency: Int): ArrayList<Date> {
        val tptaDaysBtwn = getDifferenceDays(startdate, enddate)

        val allBillsDatesArray = ArrayList<Date>()
        when (frequency) {
               0 -> //no repeat
            {
                allBillsDatesArray.add(startdate)
            }
            1 ->//daily
            {

                for (i in 0..tptaDaysBtwn) {

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(startdate)

                    }
                    else
                    {
                        val curDate= addDay(allBillsDatesArray[allBillsDatesArray.size-1],1)
                        allBillsDatesArray.add(curDate)
                    }

                }


            }
            2 -> // 7 weekly
            {

                for (i in 0..tptaDaysBtwn) {

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(startdate)

                    }
                    else
                    {
                        val curDate= addDay(allBillsDatesArray[allBillsDatesArray.size-1],7)

                        if (curDate <= enddate) {
                            allBillsDatesArray.add(curDate)
                        } else {

                            break
                        }
                    }
                    i+7
                }

            }
            3 ->  // 14 fortnightly
            {
                for (i in 0..tptaDaysBtwn) {

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(startdate)

                    }
                    else
                    {
                        val curDate= addDay(allBillsDatesArray[allBillsDatesArray.size-1],14)

                        if (curDate <= enddate) {
                            allBillsDatesArray.add(curDate)
                        } else {

                            break
                        }
                    }
                    i+14
                }
            }
            4 ->  // 21  3weeks
            {
                for (i in 0..tptaDaysBtwn) {

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(startdate)

                    }
                    else
                    {
                        val curDate= addDay(allBillsDatesArray[allBillsDatesArray.size-1],21)

                        if (curDate <= enddate) {
                            allBillsDatesArray.add(curDate)
                        } else {

                            break
                        }
                    }
                    i+21
                }
            }
            5 -> //  28  4weeks
            {
                for (i in 0..tptaDaysBtwn) {

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(startdate)

                    }
                    else
                    {
                        val curDate= addDay(allBillsDatesArray[allBillsDatesArray.size-1],28)

                        if (curDate <= enddate) {
                            allBillsDatesArray.add(curDate)
                        } else {

                            break
                        }
                    }
                    i+28
                }
            }
            6 ->  //  30 4weeks
            {
                for (i in 0..tptaDaysBtwn) {

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(startdate)

                    }
                    else
                    {
                        val curDate= addDay(allBillsDatesArray[allBillsDatesArray.size-1],21)

                        if (curDate <= enddate) {
                            allBillsDatesArray.add(curDate)
                        } else {

                            break
                        }
                    }
                    i + 30
                }
            }
            7 -> //    1month
            {
                for (i in 0..tptaDaysBtwn) {
                    var curDate= startdate

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(curDate)

                    }
                    else
                    {
                        curDate= addMonth(allBillsDatesArray[allBillsDatesArray.size-1],1)

                        if (curDate <= enddate) {
                            allBillsDatesArray.add(curDate)
                        } else {

                            break
                        }
                    }
                    i + getDaysinMonth(curDate)
                }
            }
            8 -> //  60 2months
            {
                for (i in 0..tptaDaysBtwn) {
                    var curDate= startdate

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(curDate)

                    }
                    else
                    {
                        curDate= addMonth(allBillsDatesArray[allBillsDatesArray.size-1],2)

                        if (curDate <= enddate) {
                            allBillsDatesArray.add(curDate)
                        } else {

                            break
                        }
                    }
                    i + getDaysinMonth(curDate)
                }
            }
            9 -> // 90  3months
            {
                for (i in 0..tptaDaysBtwn) {
                    var curDate= startdate

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(curDate)

                    }
                    else
                    {
                        curDate= addMonth(allBillsDatesArray[allBillsDatesArray.size-1],3)

                        if (curDate <= enddate) {
                            allBillsDatesArray.add(curDate)
                        } else {

                            break
                        }
                    }
                    i + getDaysinMonth(curDate)
                }
            }
            10 -> //  180 6months
            {
                for (i in 0..tptaDaysBtwn) {
                    var curDate= startdate

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(curDate)

                    }
                    else
                    {
                        curDate= addMonth(allBillsDatesArray[allBillsDatesArray.size-1],6)

                        if (curDate <= enddate) {
                            allBillsDatesArray.add(curDate)
                        } else {

                            break
                        }
                    }
                    i + getDaysinMonth(curDate)
                }
            }
            11 ->  // 365 yearly
            {
                for (i in 0..tptaDaysBtwn) {
                    var curDate= startdate

                    if(i.toInt() == 0)
                    {
                        allBillsDatesArray.add(curDate)

                    }
                    else
                    {
                        curDate= addYear(allBillsDatesArray[allBillsDatesArray.size-1],1)

                        if (curDate <= enddate) {
                            allBillsDatesArray.add(curDate)
                        } else {

                            break
                        }
                    }
                    i + 365
                }
            }
        }
        if (allBillsDatesArray.size>1)
     allBillsDatesArray.removeAt(allBillsDatesArray.size-1)
        return allBillsDatesArray
    }


    open fun getDifferenceDays(d1: Date, d2: Date): Long {
        val diff = d2.time - d1.time
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
    }


    open fun fromJson(jsonString: String?, type: Type?): Any? {
        return Gson().fromJson(jsonString, type)
    }

    fun covertObjectToString(`object`: Any): String {
        return Gson().toJson(`object`)
    }
}