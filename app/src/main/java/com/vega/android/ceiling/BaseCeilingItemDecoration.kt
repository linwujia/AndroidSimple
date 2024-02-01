package com.vega.android.ceiling

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vega.android.R

/**
 * 显示日期的修饰器  包括悬停的日期
 */
abstract class BaseCeilingItemDecoration: ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.adapter !is BaseCeilingAdapter) {
            return
        }

        val adapter = parent.adapter as BaseCeilingAdapter
        val position = parent.getChildAdapterPosition(view)
        if(adapter.isGroupHeader(position)) {
            outRect.top = getGroupItemOffsets(view, parent, state, adapter, position)
        } else {
            outRect.top = parent.resources.getDimensionPixelOffset(R.dimen.item_split_line)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter !is BaseCeilingAdapter) {
            return
        }

        val adapter = parent.adapter as BaseCeilingAdapter

        val count = parent.childCount
        for (i in 0 until count) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            if(adapter.isGroupHeader(position)) {
                onGroupHeaderDraw(c, parent, state, adapter, view, position)
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter !is BaseCeilingAdapter) {
            return
        }

        if (parent.layoutManager !is LinearLayoutManager) {
            return
        }

        val adapter = parent.adapter as BaseCeilingAdapter
        val firstPosition = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val lastPosition = (parent.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        val nextGroupPosition = adapter.findNextGroupStartPosition(firstPosition, lastPosition)
        onGroupCeilingDrawOver(c, parent, state, adapter, firstPosition, nextGroupPosition)
    }

    abstract fun getGroupItemOffsets(view: View, parent: RecyclerView, state: RecyclerView.State, adapter: BaseCeilingAdapter<ViewHolder>, position: Int): Int

    abstract fun onGroupHeaderDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State, adapter: BaseCeilingAdapter<ViewHolder>, view: View, position: Int)

    abstract fun onGroupCeilingDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State, adapter: BaseCeilingAdapter<ViewHolder>, position: Int, nextGroupPosition: Int)
}