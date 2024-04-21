package com.example.randomcoffee.services

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class Router(private val containerId: Int, private val fragmentManager: FragmentManager, private val fragmentFactory: FragmentFactory,private val bottomNavigationView: BottomNavigationView) {

    fun addFragmentWithBackStack(fragmentType:Int) {
        checkFragment(fragmentType)
        val fragment = fragmentFactory.createFragment(fragmentType)
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun addFragmentWithoutBackStack(fragmentType:Int) {
        checkFragment(fragmentType)

        val fragment = fragmentFactory.createFragment(fragmentType)
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun popFragment() {
        fragmentManager.popBackStack()

    }

    fun checkFragment(fragmentType: Int){
        var state = View.GONE
         when(fragmentType){
            FragmentFactory.FRAGMENT_MEETING-> state = View.VISIBLE
        FragmentFactory.FRAGMENT_PROFILE->state = View.VISIBLE
            FragmentFactory.FRAGMENT_LOGIN->state = View.GONE
            FragmentFactory.FRAGMENT_REGISTRATION->state = View.GONE
            FragmentFactory.FRAGMENT_MEETING_CONFIRM->state = View.VISIBLE
        }
        bottomNavigationView.visibility = state
    }



}