package com.example.mvvm_map_demos.ui.login

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvm_map_demos.R
import com.example.mvvm_map_demos.ui.Main.MainActivity
import com.example.mvvm_map_demos.utils.ConnectivityDetector
import com.example.mvvm_map_demos.utils.GlobalMethods
import com.example.mvvm_map_demos.utils.KeyboardUtility
import com.example.mvvm_map_demos.utils.SessionManager.SessionManager
import com.example.mvvm_map_demos.utils.UtilsJava
import com.google.firebase.iid.FirebaseInstanceId

import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext = LoginActivity@ this
        setListners()
    }

    private fun setListners() {
        try {
            tvSignInALWE.setOnClickListener(this)
            imgPassWrdShHdALWE.setOnClickListener(this)
            imgPassWrdShHdALWE.setImageDrawable(resources.getDrawable(R.drawable.ic_pw_dont_show_blk))

        } catch (e: Exception) {
        }
    }

    var isCheckPassword: Boolean = false

    override fun onClick(v: View?) {
        var i = v!!.id
        when (i) {

            R.id.imgPassWrdShHdALWE -> {
                if (isCheckPassword == false) {
                    isCheckPassword = true
                    imgPassWrdShHdALWE.setImageDrawable(resources.getDrawable(R.drawable.ic_pw_show_blk))
                    edtTvPassWrdALWE.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isCheckPassword = false
                    imgPassWrdShHdALWE.setImageDrawable(resources.getDrawable(R.drawable.ic_pw_dont_show_blk))
                    edtTvPassWrdALWE.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                }
            }

            R.id.tvSignInALWE -> {
                if (isValidation()) {
                    if (ConnectivityDetector.isConnectingToInternet(mContext)) {
                        SessionManager.setIsUserLoggedin(mContext,true)
                        var email=edtTvPassWrdALWE.text.toString()
                        SessionManager.setUserEmail(mContext,email)
                        var intent=Intent(mContext,MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        UtilsJava.showInternetAlert(mContext)
                    }

                }
            }

        }
    }


    var email: String? = ""
    var password: String? = ""

    private fun isValidation(): Boolean {

        email = edtTvEmailALWE.text.toString()
        password = edtTvPassWrdALWE.text.toString()

        if (email.equals("", ignoreCase = true)) {
            GlobalMethods.showToast(mContext, resources.getString(R.string.email_validation))
            edtTvEmailALWE.requestFocus()
            KeyboardUtility.hideKeyboard(mContext, edtTvEmailALWE)
            return false
        } else if (!GlobalMethods.isEmailValid(email!!)) {
            GlobalMethods.showToast(mContext, resources.getString(R.string.valid_email_validation))
            edtTvEmailALWE.requestFocus();
            KeyboardUtility.hideKeyboard(mContext, edtTvEmailALWE)
            return false
        } else if (password.equals("", ignoreCase = true)) {
            GlobalMethods.showToast(mContext, resources.getString(R.string.password_validation))
            edtTvPassWrdALWE.requestFocus();
            KeyboardUtility.hideKeyboard(mContext, edtTvPassWrdALWE)
            return false
        } else if (password!!.length < 6 || password!!.length > 16) {
            GlobalMethods.showToast(
                mContext,
                resources.getString(R.string.password_length_validation)
            )
            edtTvPassWrdALWE.requestFocus();
            KeyboardUtility.hideKeyboard(mContext, edtTvPassWrdALWE)
            return false
        } else if (!email.equals("hello@gmail.com") && !password.equals(
                "Password@123",
                ignoreCase = true
            )
        ) {
            GlobalMethods.showToast(mContext, resources.getString(R.string.valid_email_password))
            edtTvEmailALWE.requestFocus();
            KeyboardUtility.hideKeyboard(mContext, edtTvEmailALWE)
            return false
        }
        return true

    }
}
