package com.ti2.nguli.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ti2.nguli.R
import com.ti2.nguli.data.HistoryData
import com.ti2.nguli.databinding.ItemHistoryBinding
import com.ti2.nguli.ui.history.HistoryFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter(private val activity: HistoryFragment): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    var listQuotes = ArrayList<HistoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int = this.listQuotes.size
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listQuotes[position],position)
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryBinding.bind(itemView)
        fun bind(quote: HistoryData, position: Int) {
            binding.tvItemTitle.text = quote.title
            binding.tvItemCategory.text = quote.category
            val timestamp = quote.date as com.google.firebase.Timestamp
            val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
            val sdf = SimpleDateFormat("dd/MMM/yyyy, HH:mm")
            val netDate = Date(milliseconds)
            val date = sdf.format(netDate).toString()
            binding.tvItemDate.text = date
            binding.tvItemDescription.text = quote.description

            binding.cvItemQuote.setOnClickListener{
            //    val intent = Intent(activity, QuoteAddUpdateActivity::class.java)
            //    intent.putExtra(EXTRA_POSITION, position)
             //   intent.putExtra(EXTRA_QUOTE, quote)
             //   activity.startActivityForResult(intent, REQUEST_UPDATE)
            }
        }
    }




}