package com.example.randomcoffee

import com.example.randomcoffee.data_structures.UserForm
import com.example.randomcoffee.data_structures.UserInfo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utils {
    val userForm = UserForm(" "," "," ",25," ","dffvb")


    fun getNextMondayDate(): String {
        val calendar = Calendar.getInstance()

        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        if (currentDayOfWeek == Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, 7)
        } else {
            calendar.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY - currentDayOfWeek + 7)
        }

        val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}