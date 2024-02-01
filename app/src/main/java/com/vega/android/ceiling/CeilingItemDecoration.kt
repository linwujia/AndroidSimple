package com.vega.android.ceiling

import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vega.android.R

class CeilingItemDecoration: BaseCeilingItemDecoration() {

    private var mGroupView: ViewGroup? = null
    private var mGroupTv: TextView? = null
    private var topGap = 0

    override fun getGroupItemOffsets(
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
        adapter: BaseCeilingAdapter<ViewHolder>,
        position: Int
    ): Int {
        if (mGroupView == null) {
            mGroupView = LayoutInflater.from(parent.context).inflate(R.layout.group_item_header, parent, false) as ViewGroup
            mGroupTv = mGroupView?.findViewById(R.id.txt_group_header)

            mGroupTv?.text = adapter.getGroupName(position)
            mGroupView?.let {
                it.measure(
                    View.MeasureSpec.makeMeasureSpec(parent.measuredWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(parent.measuredHeight, View.MeasureSpec.AT_MOST)
                )
                it.layout(0, 0, it.measuredWidth, it.measuredHeight)
                topGap = it.measuredHeight
            }
        }

        return topGap
    }

    override fun onGroupHeaderDraw(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State,
        adapter: BaseCeilingAdapter<ViewHolder>,
        view: View,
        position: Int
    ) {
        val groupName = adapter.getGroupName(position)
        mGroupTv?.text = groupName
        mGroupView?.let {
            val saveCount = c.save()
            c.translate(0f, view.top.toFloat() - topGap)
            it.draw(c)
            c.restoreToCount(saveCount)
        }
    }

    override fun onGroupCeilingDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State,
        adapter: BaseCeilingAdapter<ViewHolder>,
        position: Int,
        nextGroupPosition: Int
    ) {
        val groupName = adapter.getGroupName(position)
        if (groupName.isNullOrEmpty()) return

        if (nextGroupPosition != -1) {
            parent.findViewHolderForAdapterPosition(nextGroupPosition)?.itemView?.let {
                mGroupView?.let { viewGroup ->
                    val bottom = (it.top - topGap).coerceAtMost(topGap)
                    mGroupTv?.text = groupName
                    val saveCount = c.save()
                    c.translate(0f, (bottom - topGap).toFloat())
                    viewGroup.draw(c)
                    c.restoreToCount(saveCount)
                }
            }
        } else {
            mGroupTv?.text = groupName
            mGroupView?.also {
                it.draw(c)
            }
        }
    }
}