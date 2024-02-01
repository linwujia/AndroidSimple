package com.vega.android

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class EntranceAdapter: Adapter<EntranceAdapter.VH>() {

    private val entranceList = arrayListOf<EntranceItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.layout_entrance_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.applyData(entranceList[position])
    }

    override fun getItemCount(): Int {
        return entranceList.size
    }

    fun notifyDataSetChanged(dataSet: List<EntranceItem>) {
        entranceList.clear()
        entranceList.addAll(dataSet)
        notifyDataSetChanged()
    }

    class EntranceItem(val display: String, val destine: Class<out Activity>)

    class VH(itemView: View): ViewHolder(itemView) {

        private val mEntranceView by lazy {
            itemView.findViewById<TextView>(R.id.entrance_item_tv)
        }

        fun applyData(data: EntranceItem) {
            mEntranceView.text = data.display
            mEntranceView.setOnClickListener {
                Intent(it.context, data.destine).apply {
                    it.context.startActivity(this)
                }
            }
        }
    }
}