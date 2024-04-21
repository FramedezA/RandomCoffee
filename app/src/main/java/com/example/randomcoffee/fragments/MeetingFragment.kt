package com.example.randomcoffee.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.randomcoffee.R
import com.example.randomcoffee.data_structures.UserForm
import com.example.randomcoffee.databinding.FragmentMeetingBinding
import com.example.randomcoffee.services.ServiceLocator
import com.example.randomcoffee.services.users.UsersViewModel

class MeetingFragment : Fragment() {
    private lateinit var binding: FragmentMeetingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMeetingBinding.inflate(layoutInflater)
        val viewModel = ServiceLocator.getService<UsersViewModel>("UserViewModel")!!
        viewModel.companionForm.observe(this){
            setUpCompanionForm(it)
        }
        binding.yesButton.setOnClickListener {
            viewModel.isCoffee =false
            binding.yesButton.visibility = View.GONE
            binding.noButton.visibility= View.GONE
            binding.meetIsCompleted.text = "Это круто!"

        }
        binding.noButton.setOnClickListener {
            viewModel.isCoffee =false
            binding.yesButton.visibility = View.GONE
            binding.noButton.visibility= View.GONE
            binding.meetIsCompleted.text = "Это печалька("
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    fun setUpCompanionForm(companionForm: UserForm){
        binding.nameTV.text = companionForm.name  +" "+companionForm.surname
        binding.ageTV.text = companionForm.age.toString()
        binding.telegramTV.text = companionForm.telegram
        binding.aboutTV.text = companionForm.about
        if (companionForm.sex == "male"){
            binding.imageView2.setImageResource(R.drawable.man)

        }
        else{
            binding.imageView2.setImageResource(R.drawable.woooooman)

        }


    }


}