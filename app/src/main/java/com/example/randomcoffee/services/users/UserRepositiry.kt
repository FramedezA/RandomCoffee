package com.example.randomcoffee.services.users

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.randomcoffee.data_structures.UserForm
import com.example.randomcoffee.data_structures.UserInfo
import com.example.randomcoffee.services.Retrofit.interfaces.UserRetrofitService
import com.example.randomcoffee.services.storage.Storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface UserRepository {
    fun getUserName(): String
    fun isUserLogged(): Boolean
    fun userOut(loginCodeMutableLiveData: MutableLiveData<UserInfo>)
    fun getNewUser(
        userName: String,
        login: String,
        password: String,
        userIdMutableLiveData: MutableLiveData<Int>
    )

    fun loginUser(
        login: String,
        password: String,
        loginCodeMutableLiveData: MutableLiveData<UserInfo>
    )

    var userForm:UserForm
}


class UserRepositoryImpl(
    private val retrofitService: UserRetrofitService,
    private val storage: Storage
):UserRepository {


    override fun getUserName(): String {
        return storage.getUserName()
    }

    override fun isUserLogged(): Boolean {
        return storage.userIsLogged()
    }


    override fun userOut(loginCodeMutableLiveData: MutableLiveData<UserInfo>) {
        loginCodeMutableLiveData.value = UserInfo(-5, "", -1)
        storage.clearUserData()
    }

    override fun getNewUser(
        userName: String,
        login: String,
        password: String,
        userIdMutableLiveData: MutableLiveData<Int>
    ) {

        retrofitService.regNewUser(userName, login, password)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        userIdMutableLiveData.value = response.body()!!.toInt()
                        userIdMutableLiveData.value.let {
                            if (it != null) {
                                if (it > 0) {
                                    storage.saveId(it.toString())
                                    storage.saveUserName(userName)
                                }
                            }
                        }

                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("mLogERR", t.message.toString())
                }
            })

    }



    override fun loginUser(
        login: String,
        password: String,
        loginCodeMutableLiveData: MutableLiveData<UserInfo>
    ) {
        retrofitService.loginUser(login, password).enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    loginCodeMutableLiveData.value = response.body()
                    if (loginCodeMutableLiveData.value?.loginStatusCode == 0) {
                        loginCodeMutableLiveData.value.let {
                            storage.saveUserLogged(true)
                            storage.saveId(it!!.userId.toString())
                            storage.saveUserName(it.userName)

                        }
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("mLogERR", "$t   $call")
            }

        })

    }

    override var userForm: UserForm
        get() = storage.userForm
        set(value) {storage.userForm = value}


}