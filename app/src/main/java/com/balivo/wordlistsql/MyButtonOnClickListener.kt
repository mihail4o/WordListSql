package com.balivo.wordlistsql

import android.view.View

/**
 * Instantiated for the Edit and Delete buttons in WordListAdapter.
 */
class MyButtonOnClickListener(internal var id: Int, internal var word: String) : View.OnClickListener {

    override fun onClick(v: View) {
        // Implemented in WordListAdapter
    }

    companion object {
        private val TAG = View.OnClickListener::class.java.simpleName
    }
}
