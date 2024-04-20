package com.example.randomcoffee.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.randomcoffee.databinding.FragmentLoginBinding
import com.example.randomcoffee.services.FragmentFactory
import com.example.randomcoffee.services.Router
import com.example.randomcoffee.services.ServiceLocator
import com.example.randomcoffee.services.users.UsersViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UsersViewModel
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        router = ServiceLocator.getService<Router>("Router")!!
        viewModel = ServiceLocator.getService("UserViewModel")!!

        binding.registerTextView.setOnClickListener {
            router.addFragmentWithBackStack(FragmentFactory.FRAGMENT_REGISTRATION)
        }

        viewModel.loginCode.observe(this) {
            if (it.loginStatusCode !=-5){
            when (it.loginStatusCode) {
                0 -> {
                    router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_MAP)
                }
                else -> Toast.makeText(
                    requireContext(),
                    "Неверные логин или пароль",
                    Toast.LENGTH_LONG
                ).show()
            }}
        }

        binding.loginButton.setOnClickListener {
            binding.loginButton.isClickable = false
            viewModel.loginUser(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }




}