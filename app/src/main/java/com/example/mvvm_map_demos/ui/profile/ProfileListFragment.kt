package com.example.mvvm_map_demos.ui.profile

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvm_map_demos.R
import com.example.mvvm_map_demos.ui.login.LoginActivity
import com.example.mvvm_map_demos.utils.SessionManager.SessionManager
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileListFragment : Fragment()/*, View.OnClickListener, KodeinAware*/ {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


   /* override val kodein by kodein()
    private lateinit var viewModel: UserDetailsViewModel
    private val factory: UserDetailsViewModelFactory by instance()
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var mContext: Context

    var fragment: Fragment? = null
    private var fragmentStack: Stack<Fragment>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_profile_list, container, false)
        mContext = requireActivity()
      //  viewModel = ViewModelProviders.of(this, factory).get(UserDetailsViewModel::class.java)


        getIDs(view)
        //setListners()

        /*if (ConnectivityDetector.isConnectingToInternet(mContext)) {
            callGetUserDataAPI()
        } else {
            UtilsJava.showInternetAlert(mContext)
        }*/
        return view
    }

    lateinit var tvEmailIDFPL:TextView
    lateinit var tvLogoutFPL:TextView
    private fun getIDs(view: View) {
        tvEmailIDFPL=view.findViewById(R.id.tvEmailIDFPL)
        tvLogoutFPL=view.findViewById(R.id.tvLogoutFPL)
        tvEmailIDFPL.setText(SessionManager.getUserEMail(mContext))

        tvLogoutFPL.setOnClickListener {
            showLogoutDialog(mContext!!.resources.getString(R.string.logout_message))
        }
    }


    private fun showLogoutDialog(message: String) {
        try {
            val builder = AlertDialog.Builder(mContext!!)
            builder.setCancelable(false)
            builder.setTitle(resources.getString(R.string.app_name))
            builder.setMessage(message)
            builder.setPositiveButton(
                getString(R.string.logout)
            ) { dialog, which ->


                SessionManager.clearAppSession(mContext!!)

                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

                dialog.dismiss()


            }
            builder.setNegativeButton(
                getString(R.string.cancel),
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        dialog.dismiss()
                    }
                })
            val dialog = builder.create()
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
