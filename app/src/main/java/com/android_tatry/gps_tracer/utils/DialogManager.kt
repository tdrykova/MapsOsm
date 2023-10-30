package com.android_tatry.gps_tracer.utils

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

object DialogManager {
    fun showYesNoDialog(context: Context, titleId: Int, msgId: Int, OnYes: DialogInterface.OnClickListener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(titleId)
        dialog.setMessage(context.getString(msgId))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", OnYes)
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") {
                _,_ ->
            dialog.dismiss()
        }
        dialog.show()
}
}