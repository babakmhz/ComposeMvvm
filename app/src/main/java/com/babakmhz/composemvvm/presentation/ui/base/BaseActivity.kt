package com.babakmhz.composemvvm.presentation.ui.base

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity(), BaseViewHelper {


    override fun onStart() {
        super.onStart()
        initializeUi()
    }

    abstract fun initializeUi()

}