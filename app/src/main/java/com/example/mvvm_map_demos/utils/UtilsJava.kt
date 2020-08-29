package com.example.mvvm_map_demos.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.widget.Toast
import com.example.mvvm_map_demos.R

object UtilsJava {
    fun showToast(context: Context?, message: String) {
        val biggerText = SpannableStringBuilder(message)
        biggerText.setSpan(RelativeSizeSpan(1.35f), 0, message.length, 0)
        Toast.makeText(context, biggerText, Toast.LENGTH_LONG).show()
    }

    fun showInternetAlert(context: Context?) {
        AlertDialog.Builder(context).setIcon(0).setTitle("Error").setMessage(
            "Please Check your internet connection!!"
        )
            .setCancelable(true).setNeutralButton("OK", null).show()
    }

    fun CustomAlert(
        context: Context?,
        title: String?,
        message: String?,
        Positive_text: String?,
        Negative_text: String?,
        PositiveListener: DialogInterface.OnClickListener?,
        NegativeListener: DialogInterface.OnClickListener?
    ) {
        val builder =
            AlertDialog.Builder(context).setTitle(title).setMessage(message)
                .setPositiveButton(Positive_text, PositiveListener)
                .setNegativeButton(Negative_text, NegativeListener)
        val dialog = builder.create()
        dialog.show()
    }

    fun showConfirmAlert(
        context: Context,
        msg: String?,
        onYesClick: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context).setIcon(0)
            .setTitle(context.getString(R.string.app_name)).setMessage(msg).setCancelable(true)
            .setNegativeButton("NO", null)
            .setPositiveButton("YES", onYesClick).show()
    }

    fun showLogoutAlert(
        context: Context,
        msg: String?,
        onYesClick: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.app_name)).setMessage(msg)
            .setCancelable(true).setNegativeButton("Cancel", null)
            .setPositiveButton("Logout", onYesClick).show()
    }

    fun showSingleAlert(
        context: Context, strTitle: String?, strMessege: String?,
        onYesClick: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context).setIcon(0).setMessage(strMessege)
            .setTitle(strTitle)
            .setCancelable(false).setPositiveButton("OK", onYesClick)
            .show()
            .getButton(DialogInterface.BUTTON_POSITIVE)
            .setTextColor(context.resources.getColor(R.color.blue))
    }
}