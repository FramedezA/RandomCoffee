package com.example.randomcoffee.services.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.randomcoffee.Utils
import com.example.randomcoffee.data_structures.UserForm
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class StorageImpl(context: Context):Storage {
    private val gson = GsonBuilder().create()


    override val defaultValue = "-1"
    private val preferences: SharedPreferences = context.getSharedPreferences("TABLE", Context.MODE_PRIVATE)
    private val userPreferences: SharedPreferences = context.getSharedPreferences("USERS_TABLE",Context.MODE_PRIVATE)

    override fun getUserId(): Int = userPreferences.getString("id",defaultValue)!!.toInt()

    override fun getUserName(): String = userPreferences.getString("name","nickName")!!
    override fun saveUserName(userName: String) {
        val editor = userPreferences.edit()
        editor?.putString("name",userName)
        editor?.apply()
    }

    override fun userIsLogged(): Boolean = userPreferences.getBoolean("isLogged",false)

    override fun saveUserLogged(bool: Boolean) {
        val editor = userPreferences.edit()
        editor?.putBoolean("isLogged",bool)
        editor?.apply()
    }

    override fun clearUserData() {
        val editor = userPreferences.edit()
        editor?.clear()
        editor?.apply()
    }

    override fun isOut(): Boolean = preferences.getBoolean("out",false)

    override fun setOut(bool: Boolean) {
        val editor = preferences.edit()
        editor?.putBoolean("out",bool)
        editor?.apply()
    }

    override var userForm: UserForm
        get() = getUserForm_()
        set(value) {
            val editor = preferences.edit()
            editor?.putString("userForm", gson.toJson(value))
            editor?.apply()
        }


    fun getUserForm_():UserForm{
         val UserFormJson = preferences.getString("userForm", gson.toJson(Utils.userForm))
         return gson.fromJson(UserFormJson, object : TypeToken<UserForm>() {}.type)
     }


    override fun saveId(id: String?) {
        val editor = userPreferences.edit()
        editor?.putString("id", id.toString())
        editor?.apply()
    }

    override fun deleteData() {
        val editor = preferences.edit()
        editor?.clear()
        editor?.apply()
    }


    override var isCoffee: Boolean
        get() =preferences.getBoolean("coffee",false)
        set(value) {
            val editor = preferences.edit()
            editor?.putBoolean("coffee", value)
            editor?.apply()
        }


}