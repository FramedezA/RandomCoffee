package com.example.randomcoffee.services.storage

import com.example.randomcoffee.data_structures.UserForm

interface Storage {
    val defaultValue: String
    fun getUserId():Int

    fun saveId(id:String?)

    fun deleteData()


    fun getUserName():String


    fun saveUserName(userName:String)

    fun userIsLogged():Boolean

    fun saveUserLogged(bool:Boolean)


    fun clearUserData()


    fun isOut():Boolean

    fun setOut(bool: Boolean)


    var userForm:UserForm

}