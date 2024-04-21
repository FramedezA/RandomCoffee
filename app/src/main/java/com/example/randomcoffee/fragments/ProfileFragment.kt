package com.example.randomcoffee.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.randomcoffee.R
import com.example.randomcoffee.Utils
import com.example.randomcoffee.data_structures.UserForm
import com.example.randomcoffee.databinding.FragmentLoginBinding
import com.example.randomcoffee.databinding.FragmentProfileBinding
import com.example.randomcoffee.services.ServiceLocator
import com.example.randomcoffee.services.users.UsersViewModel


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: UsersViewModel
    private lateinit var userForm: UserForm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)

        viewModel = ServiceLocator.getService<UsersViewModel>("UserViewModel")!!
        userForm = viewModel.userForm
        loadUserForm()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    fun loadUserForm() {
        if (userForm != Utils.userForm) {
            binding.nameFormEditText.setText(userForm.name)
            binding.surnameEditText.setText(userForm.surname)
            binding.ageEditText.setText(userForm.age.toString())
            binding.aboutEditText.setText(userForm.about)
            binding.telegramEditText.setText(userForm.telegram)
        }
        binding.nameFormEditText.doOnTextChanged { text, _, _, _ ->
            userForm.name = text.toString().trim()
        }
        binding.surnameEditText.doOnTextChanged { text, _, _, _ ->
            userForm.surname = text.toString().trim()
        }
        binding.ageEditText.doOnTextChanged { text, _, _, _ ->
            if(text.toString().length!=0){
                userForm.age = text.toString().trim().toInt()
            }
            else{
                userForm.age = 0
            }
        }
        binding.aboutEditText.doOnTextChanged { text, _, _, _ ->
            userForm.about = text.toString().trim()
        }
        binding.telegramEditText.doOnTextChanged { text, _, _, _ ->
            userForm.telegram = text.toString().trim()
        }
        if( userForm.sex == "male"){
        binding.manRadioButton.isChecked = true
            binding.womanRadioButton.isChecked = false

        }
        else{
            binding.manRadioButton.isChecked = false
            binding.womanRadioButton.isChecked = true

        }

        binding.manRadioButton.setOnClickListener {
            userForm.sex = "male"
            binding.womanRadioButton.isChecked = false

        }
        binding.womanRadioButton.setOnClickListener {
            userForm.sex = "female"
            binding.manRadioButton.isChecked = false
        }
    }

    override fun onPause() {
        super.onPause()
        if (userForm.name != "" && userForm.surname != "" && userForm.age != 0 && userForm.about != "" && userForm.telegram != "") {
            viewModel.userForm = userForm

            viewModel.sendUserForm()
        }

    }
    fun saveForm(){
    }


}