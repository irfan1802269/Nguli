package com.ti2.nguli.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ti2.nguli.MyData
import com.ti2.nguli.R
import com.ti2.nguli.buttonHome.ContentBundleActivity
import com.ti2.nguli.buttonHome.ContentCategoryActivity
import com.ti2.nguli.buttonHome.ContentVoucherActivity
import com.ti2.nguli.data.BundleData

class BundleDataAdapter (val listMyDatas: ArrayList<BundleData>, val context: Context) :
    RecyclerView.Adapter<BundleDataAdapter.GridViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_bundle, viewGroup, false)
        return GridViewHolder(view)
    }
    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val myData = listMyDatas[position]
        Glide.with(holder.itemView.context)
            .load(listMyDatas[position].photo)
            .apply(RequestOptions().override(350, 550))
            .into(holder.imgPhoto)
        holder.tvName.text = myData.name
        holder.tvDetail.text = myData.description
        holder.itemView.setOnClickListener {
            val moveWithObjectIntent = Intent(context, ContentBundleActivity::class.java)
            moveWithObjectIntent.putExtra(ContentBundleActivity.EXTRA_MYDATA, myData)
            context.startActivity(moveWithObjectIntent)
        }
    }
    override fun getItemCount(): Int {
        return listMyDatas.size
    }
    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_item_detail)
    }
}
