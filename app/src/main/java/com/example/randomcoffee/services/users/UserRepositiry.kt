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


    var isCoffee:Boolean

    fun addMeetingAgreement(during:Int)

    fun checkMeetings(meetingIsSetMutableLiveData: MutableLiveData<Int>)

    fun getCompanionForm(
        companionFormMutableLiveData: MutableLiveData<UserForm>
    )
    fun getHistory(historyMutableLiveData:MutableLiveData<List<UserForm>>)
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
    override var isCoffee: Boolean
        get() = storage.isCoffee
        set(value) {storage.isCoffee = value}

    override fun addMeetingAgreement(during: Int) {
        Log.d("mLogID", storage.getUserId().toString())

        retrofitService.sendMeetingAgreement(
            storage.getUserId(),
            during

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
    override fun checkMeetings(
        meetingIsSetMutableLiveData: MutableLiveData<Int>
    ) {
        retrofitService.checkMeetings(storage.getUserId())
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        meetingIsSetMutableLiveData.value = response.body()!!.toInt()


                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("mLogERR", t.message.toString())
                }
            })

    }
    override fun getCompanionForm(
        companionFormMutableLiveData: MutableLiveData<UserForm>
    ) {
        retrofitService.getCompanionFormByUserId(storage.getUserId())
            .enqueue(object : Callback<UserForm> {
                override fun onResponse(call: Call<UserForm>, response: Response<UserForm>) {
                    if (response.isSuccessful) {
                        companionFormMutableLiveData.value = response.body()


                    }

                }

                override fun onFailure(call: Call<UserForm>, t: Throwable) {
                    Log.d("mLogERR", t.message.toString())
                }
            })

    }

    override fun getHistory(historyMutableLiveData: MutableLiveData<List<UserForm>>) {
        retrofitService.getHistory(storage.getUserId())
            .enqueue(object : Callback<List<UserForm>> {
                override fun onResponse(call: Call<List<UserForm>>, response: Response<List<UserForm>>) {
                    if (response.isSuccessful) {
                        historyMutableLiveData.value = response.body()


                    }

                }

                override fun onFailure(call: Call<List<UserForm>>, t: Throwable) {
                    Log.d("mLogERR", t.message.toString())
                }
            })
    }


}