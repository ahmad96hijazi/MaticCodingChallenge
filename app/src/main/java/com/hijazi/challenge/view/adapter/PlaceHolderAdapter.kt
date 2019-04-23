package com.hijazi.challenge.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hijazi.challenge.R

class PlaceHolderAdapter(private val itemCount: Int) :
    RecyclerView.Adapter<PlaceHolderAdapter.ViewHolder>() {

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_placeholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount() = itemCount
}