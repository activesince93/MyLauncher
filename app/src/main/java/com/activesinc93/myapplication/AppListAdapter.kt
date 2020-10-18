package com.activesinc93.myapplication

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_app.view.*


/**
 * Created by Darshan Parikh on 18/10/20.
 */

class AppListAdapter(
    private val context: Context,
    private val appsList: List<MyApp>,
    private val listener: AppClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemHolder) {
            val application = appsList[position]
            holder.tvAppName.text = application.name

            val icon: Drawable = application.icon
            holder.ivAppIcon.setImageDrawable(icon)
        }
    }

    override fun getItemCount(): Int {
        return appsList.count()
    }

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivAppIcon = itemView.ivAppIcon!!
        val tvAppName = itemView.tvAppName!!

        init {
            itemView.setOnClickListener {
                listener.onAppClicked(appsList[adapterPosition])
            }
        }
    }
}