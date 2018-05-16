package com.balivo.wordlistsql


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

/**
 * Implements a simple Adapter for a RecyclerView.
 * Demonstrates how to add a click handler for each item in the ViewHolder.
 */
class WordListAdapter(internal var mContext: Context, db : WordListOpenHelper) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private var mDB : WordListOpenHelper

    private val mInflater: LayoutInflater

     /**
     * Custom view holder with a text view and two buttons.
     */
    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView
        var delete_button: Button
        var edit_button: Button

        init {
            wordItemView = itemView.findViewById(R.id.word) as TextView
            delete_button = itemView.findViewById(R.id.delete_button) as Button
            edit_button = itemView.findViewById(R.id.edit_button) as Button
        }
    }

    init {
        mInflater = LayoutInflater.from(mContext)
        this.mDB = db
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = mInflater.inflate(R.layout.wordlist_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val isMDB = mDB!=null
        Log.i("BALIVO", "mDB != null:" + isMDB)
        val current = mDB.query(position) as WordItem
        val isCurrent = current!=null
        Log.i("BALIVO", "current != null:" + isCurrent)
        holder.wordItemView.text = current!!.mWord
    }

    override fun getItemCount(): Int {
        return 10
    }

    companion object {

        private val TAG = WordListAdapter::class.java.simpleName

        val EXTRA_ID = "ID"
        val EXTRA_WORD = "WORD"
    }
}
