package com.example.mvvm_map_demos.ui.home.adtr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_map_demos.R
import com.example.mvvm_map_demos.data.responce.Data
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.raw_user_list.view.*

class HomeUserListAdapter(
    var context: Context,
    var alRequestDataList: List<Data>,
    var btnlistener: BtnClickListener
) :
    RecyclerView.Adapter<HomeUserListAdapter.ViewHolder>() {


    companion object {
        var mClickListener: BtnClickListener? = null
    }

    open interface BtnClickListener {
        fun onHomeDetails(position: Int)
    }

    var lastSelectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.raw_user_list, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgVwUserPic = view.imgVwUserPic
        val tvEmailRUL = view.tvEmailRUL
        val tvFirstNameRUL = view.tvFirstNameRUL
        val tvSecondNameRUL = view.tvSecondNameRUL
        val imgVwEditProfile = view.imgVwEditProfile

    }


    override fun getItemCount(): Int {
        return alRequestDataList.size
    }


    override fun onBindViewHolder(viewholder: ViewHolder, pos: Int) {
        try {
            val itemList = alRequestDataList!!.get(pos)

            viewholder.tvFirstNameRUL.setText(itemList.first_name)
            viewholder.tvSecondNameRUL.setText(itemList.last_name)
            viewholder.tvEmailRUL.setText(itemList.email)

            val transformation = RoundedTransformationBuilder()
                .cornerRadiusDp(1f)
                .oval(true)
                .build()

            if (itemList.avatar != null) {
                Picasso.with(context)
                    .load(itemList.avatar)
                    .fit()
                    .transform(transformation)
                    .into(viewholder.imgVwUserPic)
            } else
                viewholder.imgVwUserPic.setImageDrawable(context.resources.getDrawable(R.mipmap.ic_launcher))


            mClickListener = btnlistener

            viewholder.imgVwEditProfile.setOnClickListener {
                if (mClickListener != null) {
                    mClickListener?.onHomeDetails(pos)
                    lastSelectedPosition = pos
                    notifyDataSetChanged()
                }
            }




        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}