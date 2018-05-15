package com.balivo.wordlistsql

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText


/**
 * Activity to edit an existing or create a new word.
 */
class EditWordActivity : AppCompatActivity() {

    private var mEditWordView: EditText? = null

    internal var mId = MainActivity.WORD_ADD

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_word)

        mEditWordView = findViewById(R.id.edit_word) as EditText

        // Get data sent from calling activity.
        val extras = intent.extras

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            val id = extras.getInt(WordListAdapter.EXTRA_ID, NO_ID)
            val word = extras.getString(WordListAdapter.EXTRA_WORD, NO_WORD)
            if (id != NO_ID && word !== NO_WORD) {
                mId = id
                mEditWordView!!.setText(word)
            }
        } // Otherwise, start with empty fields.
    }

    /**
     * Click handler for the Save button.
     * Creates a new intent for the reply, adds the reply message to it as an extra,
     * sets the intent result, and closes the activity.
     */
    fun returnReply(view: View) {
        val word = (findViewById(R.id.edit_word) as EditText).text.toString()

        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, word)
        replyIntent.putExtra(WordListAdapter.EXTRA_ID, mId)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    companion object {

        private val TAG = EditWordActivity::class.java.simpleName

        private val NO_ID = -99
        private val NO_WORD = ""

        // Unique tag for the intent reply.
        val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}

