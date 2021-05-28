package com.kineticthinkers.financetick.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.google.gson.Gson
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DBManager(private val context: Context) {
    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): DBManager {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase

        return this
    }
/*   Queries For Bill Categories Table*/

    fun close() {
        dbHelper!!.close()
    }

    fun deleteAll() {
        database!!.insert(DatabaseHelper.TABLE_NAME, null, null)
    }

    fun insert(parent_category: Int, category_name: String?, frequency: Int) {
        val contentValue = ContentValues()
        contentValue.put(DatabaseHelper.PARENT_CATEGORY, parent_category)
        contentValue.put(DatabaseHelper.CATEGORY_NAME, category_name)
        contentValue.put(DatabaseHelper.FREQUENCY, frequency)
        database!!.insert(DatabaseHelper.TABLE_NAME, null, contentValue)
    }

    fun fetch(): Cursor? {
        val columns = arrayOf(
            DatabaseHelper._ID,
            DatabaseHelper.PARENT_CATEGORY,
            DatabaseHelper.CATEGORY_NAME,
            DatabaseHelper.FREQUENCY
        )
        val cursor =
            database!!.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null)
        cursor?.moveToFirst()
        return cursor
    }

    fun GetCount(): Int {

        val c: Cursor = database!!.rawQuery("select * from " + DatabaseHelper.TABLE_NAME, null)
        return c.count
    }

    fun update(_id: Long, parent_category: Int, category_name: String?, frequency: Int): Int {
        val contentValues = ContentValues()
        contentValues.put(DatabaseHelper.PARENT_CATEGORY, parent_category)
        contentValues.put(DatabaseHelper.CATEGORY_NAME, category_name)
        contentValues.put(DatabaseHelper.FREQUENCY, frequency)
        return database!!.update(
            DatabaseHelper.TABLE_NAME,
            contentValues,
            DatabaseHelper._ID + " = " + _id,
            null
        )
    }

    fun delete(_id: Long) {
        database!!.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null)
    }
/*   Queries For Bill Categories Table*/


    /*   Queries For Bill Table*/
    fun GetCountBILLS(): Int {

        val c: Cursor = database!!.rawQuery("select * from " + DatabaseHelper.TABLE_NAME_BILLS, null)
        return c.count
    }
    fun deleteAllBill() {
        database!!.insert(DatabaseHelper.TABLE_NAME_BILLS, null, null)
    }

    fun insertBill(
        bill_type: Int,
        bill_name: String?,
        bill_frequency: Int,
        bill_notes: String?,
        bill_start_date: ArrayList<Date>,
        bill_due_date: String?,
        bill_amount: Long,
        bill_paid: Int
    ) {
        val contentValue = ContentValues()
        contentValue.put(DatabaseHelper.BILL_TYPE, bill_type)
        contentValue.put(DatabaseHelper.BILL_NAME, bill_name)
        contentValue.put(DatabaseHelper.BILL_FREQUENCY, bill_frequency)
        contentValue.put(DatabaseHelper.BILL_NOTES, bill_notes)

        contentValue.put(DatabaseHelper.BILL_START_DATE, covertObjectToString(bill_start_date ))
        contentValue.put(DatabaseHelper.BILL_DUE_DATE, bill_due_date)
        contentValue.put(DatabaseHelper.BILL_AMOUNT, bill_amount)
        contentValue.put(DatabaseHelper.BILL_PAID, bill_paid)
       val billParentId =  database!!.insert(DatabaseHelper.TABLE_NAME_BILLS, null, contentValue)
        bill_start_date.forEachIndexed { index, currentbiildate ->
            val currentbiildateString =         SimpleDateFormat("dd/MM/yyy").format(currentbiildate)


            insertBillSchedule(billParentId,bill_type,bill_name,bill_frequency,bill_notes,currentbiildateString,bill_amount,bill_paid)

        }
    }
    open fun fromJson(jsonString: String?, type: Type?): Any? {
        return Gson().fromJson(jsonString, type)
    }


    fun covertObjectToString(`object`: Any): String {
        return Gson().toJson(`object`)
    }

    fun fetchBill(): Cursor? {
        val columns = arrayOf(
            DatabaseHelper.BILL_ID,
            DatabaseHelper.BILL_TYPE,
            DatabaseHelper.BILL_NAME,
            DatabaseHelper.BILL_FREQUENCY,
            DatabaseHelper.BILL_NOTES,
            DatabaseHelper.BILL_DUE_DATE,
            DatabaseHelper.BILL_AMOUNT,
            DatabaseHelper.BILL_PAID
        )
        val cursor =
            database!!.query(DatabaseHelper.TABLE_NAME_BILLS, columns, null, null, null, null, null)
        cursor?.moveToFirst()
        return cursor
    }

    fun getCountBill(): Int {

        val c: Cursor =
            database!!.rawQuery("select * from " + DatabaseHelper.TABLE_NAME_BILLS, null)
        return c.count
    }

    fun updateBill(
        bill_id: Long, bill_type: Int, bill_name: String?, bill_frequency: Int,
        bill_notes: String?, bill_start_date: String?,bill_due_date: String?, bill_amount: Long, bill_paid: Int
    ): Int {
        val contentValue = ContentValues()
        contentValue.put(DatabaseHelper.BILL_TYPE, bill_type)
        contentValue.put(DatabaseHelper.BILL_NAME, bill_name)
        contentValue.put(DatabaseHelper.BILL_FREQUENCY, bill_frequency)
        contentValue.put(DatabaseHelper.BILL_NOTES, bill_notes)
        contentValue.put(DatabaseHelper.BILL_START_DATE, bill_start_date)
        contentValue.put(DatabaseHelper.BILL_DUE_DATE, bill_due_date)
        contentValue.put(DatabaseHelper.BILL_AMOUNT, bill_amount)
        contentValue.put(DatabaseHelper.BILL_PAID, bill_paid)

        return database!!.update(
            DatabaseHelper.TABLE_NAME_BILLS,
            contentValue,
            DatabaseHelper.BILL_ID + " = " + bill_id,
            null
        )
    }

    fun deleteBill(_id: Long) {
        database!!.delete(DatabaseHelper.TABLE_NAME_BILLS, DatabaseHelper.BILL_ID + "=" + _id, null)
    }

    /*   Queries For Bill Table*/


/*   Queries For Bill Schedule Table*/
fun GetCountBillSSchedule(): Int {

    val c: Cursor = database!!.rawQuery("select * from " + DatabaseHelper.TABLE_NAME_BILLS_SCHEDULE, null)
    return c.count
}
    fun deleteAllBillSchedule() {
        database!!.insert(DatabaseHelper.TABLE_NAME_BILLS_SCHEDULE, null, null)
    }

    fun insertBillSchedule(
        bill_id_schedule: Long,
        bill_type: Int,
        bill_name: String?,
        bill_frequency: Int,
        bill_notes: String?,
        bill_due_date: String?,
        bill_amount: Long,
        bill_paid: Int
    ) {
        val contentValue = ContentValues()
        contentValue.put(DatabaseHelper.BILL_ID_PARENT_SCHEDULE, bill_id_schedule)
        contentValue.put(DatabaseHelper.BILL_TYPE_SCHEDULE, bill_type)
        contentValue.put(DatabaseHelper.BILL_NAME_SCHEDULE, bill_name)
        contentValue.put(DatabaseHelper.BILL_FREQUENCY_SCHEDULE, bill_frequency)
        contentValue.put(DatabaseHelper.BILL_NOTES_SCHEDULE, bill_notes)
        contentValue.put(DatabaseHelper.BILL_DUE_DATE_SCHEDULE, bill_due_date)
        contentValue.put(DatabaseHelper.BILL_AMOUNT_SCHEDULE, bill_amount)
        contentValue.put(DatabaseHelper.BILL_PAID_SCHEDULE, bill_paid)
        database!!.insert(DatabaseHelper.TABLE_NAME_BILLS_SCHEDULE, null, contentValue)
    }

    fun fetchBillSchedule(): Cursor? {
        val columns = arrayOf(
            DatabaseHelper.BILL_ID_SCHEDULE,
            DatabaseHelper.BILL_ID_PARENT_SCHEDULE,
            DatabaseHelper.BILL_TYPE_SCHEDULE,
            DatabaseHelper.BILL_NAME_SCHEDULE,
            DatabaseHelper.BILL_FREQUENCY_SCHEDULE,
            DatabaseHelper.BILL_NOTES_SCHEDULE,
            DatabaseHelper.BILL_DUE_DATE_SCHEDULE,
            DatabaseHelper.BILL_AMOUNT_SCHEDULE,
            DatabaseHelper.BILL_PAID_SCHEDULE
        )
        val cursor =
            database!!.query(DatabaseHelper.TABLE_NAME_BILLS_SCHEDULE, columns, null, null, null, null, null)
        cursor?.moveToFirst()
        return cursor
    }

    fun getCountBillSchedule(): Int {

        val c: Cursor =
            database!!.rawQuery("select * from " + DatabaseHelper.TABLE_NAME_BILLS_SCHEDULE, null)
        return c.count
    }

    fun updateBillSchedule(
        bill_id: Long, bill_id_main: Long, bill_type: Int, bill_name: String?, bill_frequency: Int,
        bill_notes: String?, bill_due_date: String?, bill_amount: Long, bill_paid: Int
    ): Int {
        val contentValue = ContentValues()
        contentValue.put(DatabaseHelper.BILL_TYPE_SCHEDULE, bill_type)
        contentValue.put(DatabaseHelper.BILL_ID_PARENT_SCHEDULE, bill_id_main)
        contentValue.put(DatabaseHelper.BILL_NAME_SCHEDULE, bill_name)
        contentValue.put(DatabaseHelper.BILL_FREQUENCY_SCHEDULE, bill_frequency)
        contentValue.put(DatabaseHelper.BILL_NOTES_SCHEDULE, bill_notes)
        contentValue.put(DatabaseHelper.BILL_DUE_DATE_SCHEDULE, bill_due_date)
        contentValue.put(DatabaseHelper.BILL_AMOUNT_SCHEDULE, bill_amount)
        contentValue.put(DatabaseHelper.BILL_PAID_SCHEDULE, bill_paid)

        return database!!.update(
            DatabaseHelper.TABLE_NAME_BILLS_SCHEDULE,
            contentValue,
            DatabaseHelper.BILL_ID_SCHEDULE + " = " + bill_id,
            null
        )
    }

    fun deleteBillSchedule(_id: Long) {
        database!!.delete(DatabaseHelper.TABLE_NAME_BILLS_SCHEDULE, DatabaseHelper.BILL_ID_SCHEDULE + "=" + _id, null)
    }

/*   Queries For Bill Schedule Table*/


}