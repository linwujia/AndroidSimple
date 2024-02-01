package com.vega.android.ceiling

import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseCeilingAdapter<VH: ViewHolder> : Adapter<VH>() {

    abstract fun isGroupHeader(position: Int): Boolean

    abstract fun getGroupName(position: Int): String?

    abstract fun findNextGroupStartPosition(startPosition: Int, endPosition: Int): Int
}