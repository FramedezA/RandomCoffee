package com.example.randomcoffee.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.randomcoffee.R
import com.example.randomcoffee.Utils
import com.example.randomcoffee.databinding.FragmentLoginBinding
import com.example.randomcoffee.databinding.FragmentMeetingConfirmBinding
import com.example.randomcoffee.services.ServiceLocator
import com.example.randomcoffee.services.users.UsersViewModel
import java.util.Calendar


class MeetingConfirmFragment : Fragment() {
    private lateinit var binding:FragmentMeetingConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ServiceLocator.getService<UsersViewModel>("UserViewModel")!!
        binding = FragmentMeetingConfirmBinding.inflate(layoutInflater)
        binding.dateTV.text = "Следующий кофе состоится ${Utils.getNextMondayDate()}"

        if(!viewModel.isCoffee){
            binding.yesButtonMeteenigConfirm.visibility =View.GONE
            binding.noButtonMeteenigConfirm.visibility =View.GONE
            binding.stateTV.text = "Вы отказались от участия"
        }
        binding.yesButtonMeteenigConfirm.setOnClickListener {
            viewModel.isCoffee = true
            viewModel.sendMeetingAgreement(15)
            binding.yesButtonMeteenigConfirm.visibility =View.GONE
            binding.noButtonMeteenigConfirm.visibility =View.GONE
            binding.stateTV.text = "Пара отобразится здесь,\n как только будет найдена!"


        }
        binding.noButtonMeteenigConfirm.setOnClickListener {
            viewModel.isCoffee = false
            binding.yesButtonMeteenigConfirm.visibility =View.GONE
            binding.noButtonMeteenigConfirm.visibility =View.GONE
            binding.stateTV.text = "Вы отказались от участия"

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