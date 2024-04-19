package com.example.randomcoffee.services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Router(private val containerId: Int, private val fragmentManager: FragmentManager, private val fragmentFactory: FragmentFactory) {

    fun addFragmentWithBackStack(fragmentType:Int) {
        val fragment = fragmentFactory.createFragment(fragmentType)
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun addFragmentWithoutBackStack(fragmentType:Int) {
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



}