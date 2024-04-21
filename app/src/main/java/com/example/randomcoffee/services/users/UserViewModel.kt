package com.example.randomcoffee.services.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.randomcoffee.data_structures.UserForm
import com.example.randomcoffee.data_structures.UserInfo
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UserRepository) : ViewModel() {


    private val userIdMutable: MutableLiveData<Int> = MutableLiveData()
    val userId: LiveData<Int> = userIdMutable



    private val loginCodeMutable: MutableLiveData<UserInfo> = MutableLiveData()
    val loginCode: LiveData<UserInfo> = loginCodeMutable

    private val meetingIsSetMutableLiveData: MutableLiveData<Int> = MutableLiveData()
    val meetingIsSet: LiveData<Int> = meetingIsSetMutableLiveData

    private val companionFormMutableLiveData: MutableLiveData<UserForm> = MutableLiveData()
    val companionForm: LiveData<UserForm> = companionFormMutableLiveData

    private val historyMutableLiveData: MutableLiveData<List<UserForm>> = MutableLiveData()
    val history: LiveData<List<UserForm>> = historyMutableLiveData






    fun userOut() {
        viewModelScope.launch {
            repository.userOut(loginCodeMutable)
        }
    }

    fun regNewUser(login: String, password: String) {
        viewModelScope.launch {
            repository.getNewUser(login, password, userIdMutable)
        }
    }


    fun getUserName(): String {
        return repository.getUserName()
    }

    fun isUserLogged(): Boolean {
        return repository.isUserLogged()
    }




    fun loginUser(login: String, password: String) {
        viewModelScope.launch {
            repository.loginUser(login, password, loginCodeMutable)
        }
    }



    var userForm: UserForm
        get() = repository.userForm
        set(value) {repository.userForm=value}


    fun sendUserForm(){
        viewModelScope.launch {
            repository.setUserForm()
        }
    }

    var isCoffee:Boolean
        get() = repository.isCoffee
        set(value) {repository.isCoffee = value}

    fun sendMeetingAgreement(during:Int){
        viewModelScope.launch {
            repository.addMeetingAgreement(during)
        }
    }
    fun checkMittings(){
        viewModelScope.launch {
            repository.checkMeetings(meetingIsSetMutableLiveData)
        }
    }
    fun getCompanionForm(){
        viewModelScope.launch {
            repository.getCompanionForm(companionFormMutableLiveData)
        }
    }

    fun getHistory(){
        viewModelScope.launch {
            repository.getHistory(historyMutableLiveData)
        }
    }


}

class UsersViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UsersViewModel(userRepository) as T

}