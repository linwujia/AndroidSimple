package com.vega.android.ceiling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vega.android.R

class CeilingAdapter: BaseCeilingAdapter<CeilingAdapter.VH>() {
    private val dataList = arrayListOf<DataItem>()

    override fun isGroupHeader(position: Int): Boolean {
        if (position == 0) {
            return true
        }

        val currentGroup = getGroupName(position)
        val prePosition = position - 1
        if (prePosition in 0 until itemCount) {
            val preGroup = getGroupName(prePosition)
            return currentGroup != preGroup
        }

        return false
    }

    override fun getGroupName(position: Int): String? {
        return if (position in 0 .. dataList.size) dataList[position].groupName else null
    }

    override fun findNextGroupStartPosition(startPosition: Int, endPosition: Int): Int {
        val count = itemCount
        if (startPosition !in 0..count || endPosition !in 0 .. count) {
            return -1
        }

        if (startPosition > endPosition) {
            return -1
        }

        val currentGroup = getGroupName(startPosition)
        for (i in startPosition + 1 .. endPosition) {
            if (getGroupName(i) != currentGroup) {
                return i
            }
        }

        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.applyData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun notifyDataSetChanged(data : List<DataItem>) {
        dataList.clear()
        if (data.isNotEmpty()) {
            dataList.addAll(data)
        }

        notifyDataSetChanged()
    }

    class VH(itemView: View) : ViewHolder(itemView) {

        private val contentView by lazy {
            itemView.findViewById<TextView>(R.id.item_content)
        }

        fun applyData(data: DataItem) {
            contentView.text = data.content
        }
    }
}