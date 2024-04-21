package com.example.randomcoffee.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.randomcoffee.R
import com.example.randomcoffee.services.FragmentFactory
import com.example.randomcoffee.services.Retrofit.RetrofitServiceProvider
import com.example.randomcoffee.services.Router
import com.example.randomcoffee.services.ServiceLocator
import com.example.randomcoffee.services.storage.StorageImpl
import com.example.randomcoffee.services.users.UserRepositoryImpl
import com.example.randomcoffee.services.users.UsersViewModel
import com.example.randomcoffee.services.users.UsersViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var router: Router


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initServises()
        val viewModel = ServiceLocator.getService<UsersViewModel>("UserViewModel")!!
        router = ServiceLocator.getService<Router>("Router")!!


        viewModel.meetingIsSet.observe(this) {
         checkMeeting(it)
        }


        if (viewModel.isUserLogged()) {
            router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_PROFILE)

        } else {
            router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_LOGIN)
        }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener  { item ->
            // Получаем ссылку на InputMethodManager
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // Закрываем клавиатуру
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            when (item.itemId) {
                R.id.home -> {
                    router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_PROFILE)

                    true
                }

                R.id.meeting -> {
                    val userForm = viewModel.userForm
                    if (userForm.name == "" || userForm.surname == "" || userForm.age == 0 || userForm.about == "" || userForm.telegram == "") {
                        Toast.makeText(this,"Заполните все поля!",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        if(viewModel.isCoffee){
                            viewModel.checkMittings()
                        }
                        else{
                            router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_MEETING_CONFIRM)
                        }
                    }
                    true
                }

                R.id.history -> {
                    val userForm = viewModel.userForm
                    if (userForm.name == "" || userForm.surname == "" || userForm.age == 0 || userForm.about == "" || userForm.telegram == "") {
                        Toast.makeText(this,"Заполните все поля!",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_HISTORY)

                    }
                    // Обработка нажатия на пункт "Notifications"
                    true
                }

                else -> false
            }
        }


    }
    fun checkMeeting(meetingState:Int){
        if(meetingState==0){
            router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_MEETING_CONFIRM)

        }
        else{
            router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_MEETING)

        }
    }


    fun initServises() {
        val storage = StorageImpl(this)


        ServiceLocator.registerService(
            "Router",
            Router(
                R.id.place_holder,
                supportFragmentManager,
                FragmentFactory(),
                findViewById(R.id.bottom_navigation)
            )
        )
        ServiceLocator.registerService(
            "UserViewModel",
            ViewModelProvider(
                this,
                UsersViewModelFactory(
                    UserRepositoryImpl(
                        RetrofitServiceProvider.userRetrofitService,
                        storage
                    )
                )
            )[UsersViewModel::class.java]
        )


    }


}