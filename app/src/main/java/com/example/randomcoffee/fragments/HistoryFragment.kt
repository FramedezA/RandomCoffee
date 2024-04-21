package com.example.randomcoffee.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomcoffee.data_structures.UserForm
import com.example.randomcoffee.databinding.FragmentHistoryBinding
import com.example.randomcoffee.services.HistoryAdapter
import com.example.randomcoffee.services.ServiceLocator
import com.example.randomcoffee.services.users.UsersViewModel


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        viewModel = ServiceLocator.getService("UserViewModel")!!
        viewModel.history.observe(this){
            setUpRecycler(it)
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    fun setUpRecycler(history:List<UserForm>){
        if (history.isEmpty()){
            binding.emptyHistoryTextView.isVisible = true
        }else{
            binding.historyRecycler.adapter = HistoryAdapter(history)
            binding.historyRecycler.layoutManager = LinearLayoutManager(context)
        }
    }


}