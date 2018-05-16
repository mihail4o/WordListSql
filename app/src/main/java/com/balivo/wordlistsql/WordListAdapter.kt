package com.balivo.wordlistsql


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
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

       val current = mDB.query(position)
       holder.wordItemView.text = current!!.mWord

        val h = holder // needs to be final for use in callback
        holder.delete_button.setOnClickListener(
                object:MyButtonOnClickListener(current.mId!!, null) {
                    override fun onClick(v:View) {
                        val deleted = mDB.delete(id)
                        if (deleted >= 0)
                            notifyItemRemoved(h.getAdapterPosition())
                    }
                })

        holder.edit_button.setOnClickListener(object:MyButtonOnClickListener(
                current.mId as Int, current.mWord) {
            override fun onClick(v:View) {
                val intent = Intent(mContext, EditWordActivity::class.java)
                intent.putExtra(EXTRA_ID, id)
                intent.putExtra(EXTRA_POSITION, h.getAdapterPosition())
                intent.putExtra(EXTRA_WORD, word)
                // Start an empty edit activity.
                (mContext as Activity).startActivityForResult(
                        intent, MainActivity.WORD_EDIT)
            }
        })
    }

    override fun getItemCount(): Int {
        return mDB.count().toInt()
    }

    companion object {

        private val TAG = WordListAdapter::class.java.simpleName

        val EXTRA_ID = "ID"
        val EXTRA_WORD = "WORD"
        val EXTRA_POSITION = "POSITION"
    }
}
