package com.absolute.template.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.absolute.template.R
import com.absolute.template.databinding.ActivityMainBinding
import com.absolute.template.viewmodel.MessageViewModel
import com.absolute.template.viewmodel.MessageViewModelFactory

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val viewModel: MessageViewModel by lazy {
        val viewModelFactory = MessageViewModelFactory(this@MainActivity.application)
        ViewModelProvider(this, viewModelFactory).get(MessageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this


    }


}