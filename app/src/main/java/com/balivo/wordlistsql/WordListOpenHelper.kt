package com.balivo.wordlistsql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// It's a good idea to always define a log tag like this.
private val TAG = WordListOpenHelper::class.java.simpleName

// has to be 1 first time or app will crash
private const val DATABASE_VERSION = 1
private const val WORD_LIST_TABLE = "word_entries"
private const val DATABASE_NAME = "wordlist"

// Column names...
const val KEY_ID = "_id"
const val KEY_WORD = "word"

// ... and a string array of columns.
private val COLUMNS = arrayOf(KEY_ID, KEY_WORD)

private const val WORD_LIST_TABLE_CREATE =
        "CREATE TABLE " + WORD_LIST_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                // id will auto-increment if no value passed
                KEY_WORD + " TEXT );"


class WordListOpenHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val mWritableDB: SQLiteDatabase? = null
    private val mReadableDB: SQLiteDatabase? = null


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(WORD_LIST_TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}