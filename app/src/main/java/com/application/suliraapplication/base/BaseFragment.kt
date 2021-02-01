package com.application.suliraapplication.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.fragment.app.Fragment
import com.application.suliraapplication.R
import com.google.android.material.snackbar.Snackbar

open class BaseFragment :Fragment() {

    private val mDialog: Dialog by lazy {
        Dialog(activity!!).apply {
            window?.requestFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.layout_progress_wheel)
            window?.setBackgroundDrawable(ColorDrawable(0))

        }

    }

    fun showDialog() {
        if (!mDialog.isShowing) {
            mDialog.show()
        }
    }

    fun dismissDialog() {
        if (mDialog !=null && mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

    fun setError(string: String) {

        val snackBar: Snackbar =
            Snackbar.make(activity!!.findViewById(android.R.id.content), string, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.parseColor("#C2272D"))
        snackBar.show()


  /*      Snackbar.make(
            findViewById(android.R.id.content),
            string,
            Snackbar.LENGTH_SHORT
        ).show()
    }*/
}
}