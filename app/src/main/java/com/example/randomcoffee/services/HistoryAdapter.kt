package com.example.randomcoffee.services

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.randomcoffee.R
import com.example.randomcoffee.data_structures.UserForm


class HistoryAdapter(private val userFormList: List<UserForm>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {



    inner class HistoryHolder(item: View) : RecyclerView.ViewHolder(item) {
        val nameTextView: TextView = item.findViewById(R.id.operationNameTextView)
        val stateTextView: TextView = item.findViewById(R.id.stateTextView)
        val telegramTextView: TextView = item.findViewById(R.id.telegram)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryHolder(view)
    }

    override fun getItemCount(): Int {
        return userFormList.size
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val element = userFormList[position]
        holder.nameTextView.text = "Встреча"+position
        holder.stateTextView.text = element.name +" "+element.surname
        holder.telegramTextView.text = element.telegram

    }





}