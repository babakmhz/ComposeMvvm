package com.babakmhz.composemvvm.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface BaseViewHelper {

    fun <T : ViewModel> getSharedViewModel(activity: BaseActivity, viewModel: Class<T>): T {
        return ViewModelProvider(activity)[viewModel]
    }

}