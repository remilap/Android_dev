package com.remilapointe.modernandroidapp

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.remilapointe.modernandroidapp.databinding.ActivityMainBinding
import com.remilapointe.modernandroidapp.uimodels.Repository

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var repository = Repository("Medium Android Repository Article",
            "Mladen Rakonjac", 1000, true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            repositoryName.text = "Medium Android Repository Article"
            repositoryOwner.text = "Mladen Rakonjac"
            numberOfStarts.text = "1000 stars"

        }
        binding.repository = repository
        binding.executePendingBindings()

    }

}
