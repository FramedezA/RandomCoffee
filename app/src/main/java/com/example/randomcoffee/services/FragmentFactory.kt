package com.example.randomcoffee.services

import androidx.fragment.app.Fragment
import com.example.randomcoffee.fragments.LoginFragment
import com.example.randomcoffee.fragments.MeetingConfirmFragment
import com.example.randomcoffee.fragments.MeetingFragment
import com.example.randomcoffee.fragments.ProfileFragment
import com.example.randomcoffee.fragments.RegistrationFragment

class FragmentFactory {
    fun createFragment(fragmentType: Int): Fragment {
        return when (fragmentType) {
            FRAGMENT_PROFILE -> ProfileFragment()
            FRAGMENT_LOGIN -> LoginFragment()
            FRAGMENT_REGISTRATION -> RegistrationFragment()
            FRAGMENT_MEETING ->MeetingFragment()
            FRAGMENT_MEETING_CONFIRM ->MeetingConfirmFragment()
            else -> throw IllegalArgumentException("Invalid fragment type")
        }
    }
    companion object {
        const val FRAGMENT_PROFILE = 2
        const val FRAGMENT_LOGIN = 3
        const val FRAGMENT_REGISTRATION = 4
        const val FRAGMENT_MEETING = 5
        const val FRAGMENT_MEETING_CONFIRM = 6
    }
}