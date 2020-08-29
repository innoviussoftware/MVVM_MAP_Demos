package com.example.mvvm_map_demos.ui.Main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mvvm_map_demos.R
import com.example.mvvm_map_demos.ui.home.HomeFragment
import com.example.mvvm_map_demos.ui.profile.ProfileListFragment
import com.example.mvvm_map_demos.utils.Applog
import com.example.mvvm_map_demos.utils.GlobalValues
import kotlinx.android.synthetic.main.activity_bottom_bar.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

   /* override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }*/

    lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext = MainActivity@ this

        try {
            setListners()

            showDashboard()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



    var fragment: Fragment? = null
    private var fragmentStack: Stack<Fragment>? = null


    private fun setListners() {
        llHomeABB.setOnClickListener(this)
        llMapABB.setOnClickListener(this)
        llProfileABB.setOnClickListener(this)
        fragmentStack = Stack()
    }

    override fun onClick(v: View?) {
        var i = v!!.id
        when (i) {
            R.id.llHomeABB -> {
                showDashboard()
            }

            R.id.llMapABB -> {
                showMapData()
            }
            R.id.llProfileABB -> {
                showUserData()
            }
        }
    }

    private fun showDashboard() {
        containerBody.visibility = View.VISIBLE
        GlobalValues.setHomeTitleName = "Dashboard"
        imgVwHome.setImageDrawable(resources.getDrawable(R.drawable.tab_home_active))
        imgMap.setImageDrawable(resources.getDrawable(R.drawable.ic_map_gray))
        imgVwProfileABB.setImageDrawable(resources.getDrawable(R.drawable.tab_user))

        fragment = HomeFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.containerBody,
            fragment!!,
            HomeFragment::class.java.getSimpleName()
        )
        if (fragmentStack!!.size >= 1) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentStack!!.lastElement().onPause()
            ft.remove(fragmentStack!!.pop())
            ft.commit()
        }
        fragmentStack!!.push(fragment)
        fragmentTransaction.commit()
    }

    private fun showMapData() {
        /*GlobalValues.setHomeTitleName = "request"
        imgVwHome.setImageDrawable(resources.getDrawable(R.drawable.tab_home))
        imgMap.setImageDrawable(resources.getDrawable(R.drawable.ic_map_app_bg))
        imgVwProfileABB.setImageDrawable(resources.getDrawable(R.drawable.tab_user))

        fragment = MapFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.containerBody,
            fragment!!,
            MapFragment::class.java.getSimpleName()
        )
        if (fragmentStack!!.size >= 1) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentStack!!.lastElement().onPause()
            ft.remove(fragmentStack!!.pop())
            ft.commit()
        }
        fragmentStack!!.push(fragment)
        fragmentTransaction.commit()*/
    }


    private fun showUserData() {
        GlobalValues.setHomeTitleName = "user"
        imgVwHome.setImageDrawable(resources.getDrawable(R.drawable.tab_home))
        imgMap.setImageDrawable(resources.getDrawable(R.drawable.ic_map_gray))
        imgVwProfileABB.setImageDrawable(resources.getDrawable(R.drawable.tab_user_active))

        fragment = ProfileListFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.containerBody,
            fragment!!,
            ProfileListFragment::class.java.getSimpleName()
        )
        if (fragmentStack!!.size >= 1) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentStack!!.lastElement().onPause()
            ft.remove(fragmentStack!!.pop())
            ft.commit()
        }
        fragmentStack!!.push(fragment)
        fragmentTransaction.commit()
    }


    override fun onBackPressed() {
        try {

            if (GlobalValues.setHomeTitleName.equals("Dashboard")) {
                super.onBackPressed()
                finish()
            } else {
                if (containerBody.visibility == View.VISIBLE) {
                    if (fragmentStack!!.size >= 2) {
                        val ft = supportFragmentManager.beginTransaction()
                        fragmentStack!!.lastElement().onPause()
                        ft.remove(fragmentStack!!.pop())
                        fragmentStack!!.lastElement().onResume()
                        ft.show(fragmentStack!!.lastElement())
                        ft.commit()
                        return
                    } else {
                        val ft = supportFragmentManager.beginTransaction()
                        fragmentStack!!.lastElement().onPause()
                        ft.remove(fragmentStack!!.pop())
                        ft.commit()
                        showDashboard()
                        return
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
