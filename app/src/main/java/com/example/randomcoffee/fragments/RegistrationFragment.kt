package com.example.randomcoffee.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.randomcoffee.services.FragmentFactory
import com.example.randomcoffee.services.Router
import com.example.randomcoffee.services.ServiceLocator
import com.example.randomcoffee.services.users.UsersViewModel
import com.example.randomcoffee.databinding.FragmentRegistrationBinding



class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: UsersViewModel
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegistrationBinding.inflate(layoutInflater)

        router = ServiceLocator.getService("Router")!!
        viewModel = ServiceLocator.getService("UserViewModel")!!


        binding.loginTextView.setOnClickListener {
            router.addFragmentWithBackStack(FragmentFactory.FRAGMENT_LOGIN)
        }

        viewModel.userId.observe(this) {
            when (it) {
                -1 -> makeToast("Этот логин уже используется")

                -2 -> makeToast("Пользователь с таким именем уже существует")

                else -> {
                    router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_MAP)
                }
            }

        }
        binding.registerButton.setOnClickListener {
            val passwordLength = binding.regPasswordEditText.text.toString().length
            val loginLength = binding.regLoginEditText.text.toString().length
            val nameLength = binding.nameEditText.text.toString().length


            if ( passwordLength in 8 until 40) {

                viewModel.regNewUser(
                    binding.nameEditText.text.toString(),
                    binding.regLoginEditText.text.toString(),
                    binding.regPasswordEditText.text.toString()
                )
            } else if (passwordLength >= 40) {
                makeToast("Слишком много символов в поле пароля")

            }
            else if (passwordLength < 8) {
                makeToast("Пароль должен содержать не менее 8-ми символов")
            }
            else if (loginLength >= 40) {
                makeToast("Слишком много символов в поле Логина")

            } else if (nameLength >= 40) {
                makeToast("Слишком много символов в имени Пользователя")

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    fun makeToast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }


}