package com.ti2.nguli.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ti2.nguli.MyData
import com.ti2.nguli.R
import com.ti2.nguli.buttonHome.ContentVoucherActivity

class CategoryDataAdapter (val listMyDatas: ArrayList<MyData>) :
    RecyclerView.Adapter<CategoryDataAdapter.GridViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_category, viewGroup, false)
        return GridViewHolder(view)
    }
    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listMyDatas[position].photo)
            .apply(RequestOptions().override(350, 550))
            .into(holder.imgPhoto)
    }
    override fun getItemCount(): Int {
        return listMyDatas.size
    }
    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }
}
