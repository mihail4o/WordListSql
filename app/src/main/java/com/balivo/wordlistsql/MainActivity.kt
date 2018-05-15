package com.balivo.wordlistsql

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Implements a RecyclerView that displays a list of words from a SQL database.
 * - Clicking the fab button opens a second activity to add a word to the database.
 * - Clicking the Edit button opens an activity to edit the current word in the database.
 * - Clicking the Delete button deletes the current word from the database.
 */
class MainActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: WordListAdapter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview) as RecyclerView
        // Create an mAdapter and supply the data to be displayed.
        mAdapter = WordListAdapter(this)
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
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName

        val WORD_EDIT = 1
        val WORD_ADD = -1
    }
}