package com.kineticthinkers.financetick.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
        db.execSQL(CREATE_TABLE_BILLS)
        db.execSQL(CREATE_TABLE_BILLS_SCHEDULE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BILLS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BILLS_SCHEDULE)

        onCreate(db)
    }

    companion object {

       // Database Information
        const val DB_NAME = "APP_BILL_DB.DB"

        // database version
        const val DB_VERSION = 3





        // Table NameDB_NAME
        const val TABLE_NAME = "CATEGORIES"
        const val TABLE_NAME_BILLS = "BILLS"
        const val TABLE_NAME_BILLS_SCHEDULE = "BILLS_SCHEDULE"

        // Table columns CATEGORIES
        const val _ID = "_id"
        const val PARENT_CATEGORY = "parent_category"
        const val CATEGORY_NAME = "category_name"
        const val FREQUENCY = "frequency"


        // Table columns BILLS
        const val BILL_ID = "bill_id"
        const val BILL_TYPE = "bill_type"
        const val BILL_NAME = "bill_name"
        const val BILL_FREQUENCY = "bill_frequency"
        const val BILL_NOTES = "bill_notes"
        const val BILL_DUE_DATE = "bill_due_date"
        const val BILL_START_DATE = "bill_start_date"
        const val BILL_AMOUNT = "bill_amount"
        const val BILL_PAID = "bill_paid"




        // Table columns BILLS
        const val BILL_ID_SCHEDULE = "bill_id_schedule"
        const val BILL_ID_PARENT_SCHEDULE = "bill_id_main"
        const val BILL_TYPE_SCHEDULE = "bill_type_schedule"
        const val BILL_NAME_SCHEDULE = "bill_name_schedule"
        const val BILL_FREQUENCY_SCHEDULE = "bill_frequency_schedule"
        const val BILL_NOTES_SCHEDULE = "bill_notes_schedule"
        const val BILL_DUE_DATE_SCHEDULE = "bill_due_date_schedule"
        const val BILL_AMOUNT_SCHEDULE = "bill_amount_schedule"
        const val BILL_PAID_SCHEDULE = "bill_paid_schedule"


        // Creating CATEGORIES table query
        private const val CREATE_TABLE = ("create table " + TABLE_NAME +
                "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PARENT_CATEGORY + " INTEGER, " +
                CATEGORY_NAME + "  TEXT NOT NULL ," +
                FREQUENCY + " INTEGER   );")


        // Creating BILLS table query
        private const val CREATE_TABLE_BILLS = ("create table " + TABLE_NAME_BILLS +
                " ( " + BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BILL_TYPE + " INTEGER, " +
                BILL_NAME + "  TEXT NOT NULL ," +
                BILL_FREQUENCY + " INTEGER, " +
                BILL_NOTES + "  TEXT NOT NULL ," +
                BILL_START_DATE + "  TEXT NOT NULL ," +
                BILL_DUE_DATE + "  TEXT NOT NULL ," +
                BILL_AMOUNT + " INTEGER, " +
                BILL_PAID + " INTEGER   );")




        // Creating BILLS table query
        private const val CREATE_TABLE_BILLS_SCHEDULE = ("create table " + TABLE_NAME_BILLS_SCHEDULE +
                " ( " + BILL_ID_SCHEDULE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BILL_ID_PARENT_SCHEDULE + " INTEGER, " +
                BILL_TYPE_SCHEDULE + " INTEGER, " +
                BILL_NAME_SCHEDULE + "  TEXT NOT NULL ," +
                BILL_FREQUENCY_SCHEDULE + " INTEGER, " +
                BILL_NOTES_SCHEDULE + "  TEXT NOT NULL ," +
                BILL_DUE_DATE_SCHEDULE + "  TEXT NOT NULL ," +
                BILL_AMOUNT_SCHEDULE + " INTEGER, " +
                BILL_PAID_SCHEDULE + " INTEGER   );")

    }
}