package com.babakmhz.composemvvm.presentation.ui.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings
import android.view.View
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.babakmhz.composemvvm.utils.validString
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

interface BaseViewHelper {

    fun <T : ViewModel> getSharedViewModel(activity: BaseActivity, viewModel: Class<T>): T {
        return ViewModelProvider(activity)[viewModel]
    }


    fun buildAlertMessageNoGps(activity: Activity) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                activity.startActivity(
                    Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    )
                )
            }
            .setNegativeButton(
                "No"
            ) { dialog, _ -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }


    fun showErrorSnackBar(view: View) {
        Snackbar.make(view, "Error occurred!", Snackbar.LENGTH_LONG).setAction("OK") {
        }.show()
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("OK") {
        }.show()
    }

}