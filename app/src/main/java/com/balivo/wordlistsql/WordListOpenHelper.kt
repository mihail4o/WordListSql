package com.balivo.wordlistsql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.nio.file.Files.delete








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

    private var mWritableDB: SQLiteDatabase? = null
    private var mReadableDB: SQLiteDatabase? = null


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(WORD_LIST_TABLE_CREATE)
        fillDatabaseWithData(db)
    }

    override fun onUpgrade(db:SQLiteDatabase, oldVersion:Int, newVersion:Int) {
        Log.w(WordListOpenHelper::class.java!!.getName(),
                ("Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"))
        db.execSQL("DROP TABLE IF EXISTS " + WORD_LIST_TABLE)
        onCreate(db)
    }

    private fun fillDatabaseWithData(db : SQLiteDatabase) {
        val words = arrayOf("Android", "Adapter", "ListView", "AsyncTask",
                "Android Studio", "SQLiteDatabase", "SQLOpenHelper", "Data model",
                "ViewHolder", "Android Performance", "OnClickListener")

        // Create a container for the data.
        val values = ContentValues()

        for (i in 0 until words.size)
        {
            // Put column/value pairs into the container.
            // put() overrides existing values.
            values.put(KEY_WORD, words[i])
            db.insert(WORD_LIST_TABLE, null, values)
        }
    }

    fun query(position:Int):WordItem {
        val query = ("SELECT * FROM " + WORD_LIST_TABLE +
                " ORDER BY " + KEY_WORD + " ASC " +
                "LIMIT " + position + ",1")
        var cursor:Cursor? = null
        var entry = WordItem(null, null)
        try
        {
            if (mReadableDB == null)
            {
                mReadableDB = getReadableDatabase()
            }
            cursor = mReadableDB!!.rawQuery(query, null)
            cursor.moveToFirst()
            entry.mId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            entry.mWord = cursor.getString(cursor.getColumnIndex(KEY_WORD))
        }
        catch (e:Exception) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.message)
        }
        finally
        {
            cursor!!.close()
            return entry
        }
    }

    fun insert(word: String): Long {

        var newId: Long = 0

        val values = ContentValues()
        values.put(KEY_WORD, word)

        try {

            if (mWritableDB == null) {
                mWritableDB = writableDatabase
            }

            newId = mWritableDB!!.insert(WORD_LIST_TABLE, null, values);

        } catch (e: Exception) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.message)
        }
        return newId
    }

    fun count():Long {
        if (mReadableDB == null)
        {
            mReadableDB = getReadableDatabase()
        }
        return DatabaseUtils.queryNumEntries(mReadableDB, WORD_LIST_TABLE)
    }

    fun delete(id: Int): Int {

        var deleted = 0

        try {
            if (mWritableDB == null) {
                mWritableDB = writableDatabase
            }

            deleted = mWritableDB!!.delete(WORD_LIST_TABLE,
                    KEY_ID + " = ? ", arrayOf(id.toString()))

        } catch (e: Exception) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.message)
        }
        return deleted
    }

    fun update(id:Int, word:String):Int {
        var mNumberOfRowsUpdated = -1
        try
        {
            if (mWritableDB == null)
            {
                mWritableDB = getWritableDatabase()
            }
            val values = ContentValues()
            values.put(KEY_WORD, word)
            mNumberOfRowsUpdated = mWritableDB!!.update(WORD_LIST_TABLE,
                    values,
                    KEY_ID + " = ?",
                    arrayOf<String>((id).toString()))
        }
        catch (e:Exception) {
            Log.d(TAG, "UPDATE EXCEPTION! " + e.message)
        }
        return mNumberOfRowsUpdated
    }
}