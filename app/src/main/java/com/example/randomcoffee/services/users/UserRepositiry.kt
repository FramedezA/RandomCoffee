package com.example.randomcoffee.services.users

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.randomcoffee.data_structures.LoginRequest
import com.example.randomcoffee.data_structures.RegisterRequest
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
        login: String,
        password: String,
        userIdMutableLiveData: MutableLiveData<Int>
    )

    fun loginUser(
        login: String,
        password: String,
        loginCodeMutableLiveData: MutableLiveData<UserInfo>
    )

    fun setUserForm()

    var userForm: UserForm
}


class UserRepositoryImpl(
    private val retrofitService: UserRetrofitService,
    private val storage: Storage
) : UserRepository {


    override fun getUserName(): String {
        return storage.getUserName()
    }

    override fun isUserLogged(): Boolean {
        return storage.userIsLogged()
    }


    override fun userOut(loginCodeMutableLiveData: MutableLiveData<UserInfo>) {
        loginCodeMutableLiveData.value = UserInfo(-5, -1)
        storage.clearUserData()
    }

    override fun getNewUser(
        login: String,
        password: String,
        userIdMutableLiveData: MutableLiveData<Int>
    ) {
        val registerRequest = RegisterRequest(login, password)
        retrofitService.regNewUser(registerRequest)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        userIdMutableLiveData.value = response.body()!!.toInt()
                        userIdMutableLiveData.value.let {
                            if (it != null) {
                                if (it > 0) {
                                    storage.saveId(it.toString())
                                    //storage.saveUserName(userName)
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
        val loginRequest = LoginRequest(login, password)
        retrofitService.loginUser(loginRequest).enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    loginCodeMutableLiveData.value = response.body()
                    if (loginCodeMutableLiveData.value?.loginStatusCode == 0) {
                        loginCodeMutableLiveData.value.let {
                            storage.saveUserLogged(true)
                            storage.saveId(it!!.userId.toString())
//                            storage.saveUserName(it.userName)

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

    override fun setUserForm() {
        Log.d("mLogID", storage.getUserId().toString())

        retrofitService.setForm(
            storage.getUserId(),
            userForm.name,
            userForm.surname,
            userForm.age,
            userForm.sex,
            userForm.about,
            userForm.telegram
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("mLogERR", "$t   $call")
            }

        })
    }


    override var userForm: UserForm
        get() = storage.userForm
        set(value) {
            storage.userForm = value
        }


}