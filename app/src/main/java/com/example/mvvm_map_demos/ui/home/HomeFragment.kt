package com.example.mvvm_map_demos.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mvvm_map_demos.R
import com.example.mvvm_map_demos.data.responce.Data
import com.example.mvvm_map_demos.data.responce.HomeResponse
import com.example.mvvm_map_demos.ui.editprofile.EditProfileActivity
import com.example.mvvm_map_demos.ui.home.adtr.HomeUserListAdapter
import com.example.mvvm_map_demos.utils.*
import com.maeiapp.ui.home.HomeViewModel
import com.maeiapp.ui.home.HomeViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(), KodeinAware,
    SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override val kodein by kodein()
    private lateinit var viewModel: HomeViewModel
    private val factory: HomeViewModelFactory by instance()

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
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        mContext = requireActivity()
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        getIDs(view)
        setListners()

        alHomeData = ArrayList()
        showHomeData()

        swpLytHomeFRL!!.setOnRefreshListener(this)
        swpLytHomeFRL!!.post(Runnable() {
            fun run() {
                firstPage = 1
                alHomeData = ArrayList()
                swpLytHomeFRL!!.setRefreshing(true)
                showHomeData()
            }
        })

        return view
    }

    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    lateinit var homeAdapter: HomeUserListAdapter

    private var firstPage: Int = 1
    private var mTotalPage: Int = 0

    private fun showHomeData() {
        if (ConnectivityDetector.isConnectingToInternet(mContext)) {
            callGetUserDataAPI()
        } else {
            UtilsJava.showInternetAlert(context)
        }
    }

    private fun callGetUserDataAPI() {
        try {

            MyProgressDialog.showProgressDialog(mContext)
            viewModel.callHomeDetailsAPI(
                mContext!!,
                firstPage
            )
                .observe(this, Observer {

                    try {
                        //Todo: After Api call Success get data as per Return Values means Model class or any other
                        if (it != null) {
                            MyProgressDialog.hideProgressDialog()
                            swpLytHomeFRL!!.setRefreshing(false)
                            mTotalPage = it.total_pages
                            firstPage = firstPage + 1
                            alHomeData.addAll(it.data)
                            alFilterExpertLst.addAll(it.data)

                            setHomeListData(alHomeData)
                            homeAdapter.notifyDataSetChanged()
                        } else {
                            MyProgressDialog.hideProgressDialog()
                        }
                    } catch (e: Exception) {
                        MyProgressDialog.hideProgressDialog()
                    }

                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setHomeListData(alHomeData: ArrayList<Data>) {
        if (alHomeData.size > 0) {
            rcVwHomeDataFM.visibility = View.VISIBLE

            homeAdapter = HomeUserListAdapter(mContext, alHomeData,
                object : HomeUserListAdapter.BtnClickListener {
                    override fun onHomeDetails(position: Int) {
                        var intent = Intent(mContext, EditProfileActivity::class.java)
                        intent.putExtra("position", position)
                        intent.putExtra("id", alHomeData.get(position).id)
                        intent.putExtra("first_name", alHomeData.get(position).first_name)
                        intent.putExtra("last_name", alHomeData.get(position).last_name)
                        intent.putExtra("email", alHomeData.get(position).email)
                        intent.putExtra("avatar", alHomeData.get(position).avatar)
                        startActivityForResult(intent, 154)
                    }


                })

            var linearLayoutManager = LinearLayoutManager(mContext)
            linearLayoutManager = GridLayoutManager(mContext, 1)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            rcVwHomeDataFM!!.layoutManager = linearLayoutManager

            rcVwHomeDataFM.setItemAnimator(DefaultItemAnimator())


            rcVwHomeDataFM!!.isNestedScrollingEnabled = true
            rcVwHomeDataFM!!.setHasFixedSize(true)
            rcVwHomeDataFM!!.setAdapter(homeAdapter)
            //Todo:Related Pagination
            scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(
                    page: Int,
                    totalItemsCount: Int,
                    view: RecyclerView?
                ) {
                    if (mTotalPage > 0 && firstPage <= mTotalPage) {
                        showHomeData()
                    }
                }
            }
            rcVwHomeDataFM.addOnScrollListener(scrollListener!!)
        } else {
            rcVwHomeDataFM.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Applog.E("requestCode-==>" + requestCode)
        Applog.E("resultCode-==>" + resultCode)

        try {
            if (requestCode == 154) {
                var first_name = data!!.getStringExtra("first_name")
                var last_name = data!!.getStringExtra("last_name")
                var email = data!!.getStringExtra("email")
                var avatar = data!!.getStringExtra("avatar")
                var profilemageUri = data!!.getStringExtra("profilemageUri")
                var position = data!!.getIntExtra("position", 0)
                var id = data!!.getIntExtra("id", 0)
                if (profilemageUri!=null) {
                    var changedValues = Data(id, first_name!!, last_name!!, profilemageUri, email!!)
                    alHomeData.add(position, changedValues)
                    homeAdapter.notifyItemChanged(position)
                }else{
                    var changedValues = Data(id, first_name!!, last_name!!, avatar, email!!)
                    alHomeData.add(position, changedValues)
                    homeAdapter.notifyItemChanged(position)
                }


            }
        } catch (e: Exception) {
        }
    }

    override fun onRefresh() {
        firstPage = 1
        alHomeData = ArrayList()
        swpLytHomeFRL!!.setRefreshing(true);
        showHomeData()
    }


    var alHomeData: ArrayList<Data> = ArrayList()
    var alFilterExpertLst: ArrayList<Data> = ArrayList()


    private fun setListners() {
    }


    lateinit var rcVwHomeDataFM: RecyclerView
    lateinit var swpLytHomeFRL: SwipeRefreshLayout
    lateinit var edtSearchExpertASD: EditText

    private fun getIDs(view: View) {
        rcVwHomeDataFM = view.findViewById(R.id.rcVwHomeDataFM)
        swpLytHomeFRL = view.findViewById(R.id.swpLytHomeFRL)
        edtSearchExpertASD = view.findViewById(R.id.edtSearchExpertASD)
        fragmentStack = Stack()


        edtSearchExpertASD.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(arg0: CharSequence, start: Int, count: Int, after: Int) {

                filterExpert(arg0.toString())
            }

            override fun onTextChanged(arg0: CharSequence, start: Int, before: Int, count: Int) {
                filterExpert(arg0.toString())

            }

            override fun afterTextChanged(s: Editable) {

                val text = edtSearchExpertASD.getText().toString().toLowerCase(Locale.getDefault())
                filterExpert(text.toString())

            }
        })
    }

    fun filterExpert(charText: String) {
        // MyProgressDialog.showProgressDialog(mContext)


        var charText = charText
        try {
            charText = charText.toLowerCase(Locale.getDefault())
            alHomeData.clear()
            setHomeListData(alHomeData)
            if (charText.isEmpty()) {
                alHomeData.addAll(alFilterExpertLst)
                setHomeListData(alHomeData)
            } else {
                for (wp in alFilterExpertLst) {

                    if (wp.first_name!!.toLowerCase(Locale.getDefault()).contains(charText)) {
                        alHomeData.add(wp)
                    } else if (wp.last_name!!.toLowerCase(Locale.getDefault()).contains(charText)) {
                        alHomeData.add(wp)
                    }
                }

                setHomeListData(alHomeData)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}
