package com.example.randomcoffee.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initServises()
        val viewModel = ServiceLocator.getService<UsersViewModel>("UserViewModel")!!

        val router = ServiceLocator.getService<Router>("Router")!!





        if (viewModel.isUserLogged()) {
          //  router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_MAP)
        } else {
            router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_LOGIN)
        }

    }


    fun initServises(){
        val storage = StorageImpl(this)

        ServiceLocator.registerService(
            "Router",
            Router(R.id.place_holder, supportFragmentManager, FragmentFactory())
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