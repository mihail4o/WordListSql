package com.balivo.wordlistsql

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.Toast

/**
 * Implements a RecyclerView that displays a list of words from a SQL database.
 * - Clicking the fab button opens a second activity to add a word to the database.
 * - Clicking the Edit button opens an activity to edit the current word in the database.
 * - Clicking the Delete button deletes the current word from the database.
 */
class MainActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: WordListAdapter? = null

    private var mDB: WordListOpenHelper? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDB = WordListOpenHelper(this)

        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview) as RecyclerView
        // Create an mAdapter and supply the data to be displayed.
        mAdapter = WordListAdapter(this, mDB!!)
        // Connect the mAdapter with the recycler view.
        mRecyclerView!!.setAdapter(mAdapter)
        // Give the recycler view a default layout manager.
        mRecyclerView!!.setLayoutManager(LinearLayoutManager(this))

        // Add a floating action click handler for creating new entries.
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(View.OnClickListener {
            // Start empty edit activity.
            val intent = Intent(baseContext, EditWordActivity::class.java)
            startActivityForResult(intent, WORD_EDIT)
        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Add code to update the database.
        if (requestCode === WORD_EDIT)
        {
            if (resultCode === RESULT_OK)
            {
                val word = data.getStringExtra(EditWordActivity.EXTRA_REPLY)

                // Update the database
                if (!TextUtils.isEmpty(word))
                {
                    val id = data.getIntExtra(WordListAdapter.EXTRA_ID, -99)
                    if (id == WORD_ADD)
                    {
                        mDB!!.insert(word)
                    }

                    // Update the UI
                    mAdapter!!.notifyDataSetChanged()
                }
                else
                {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.empty_not_saved,
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName

        val WORD_EDIT = 1
        val WORD_ADD = -1
    }
}